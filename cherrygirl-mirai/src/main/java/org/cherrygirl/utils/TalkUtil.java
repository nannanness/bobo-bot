package org.cherrygirl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.pojo.WxMessage;
import org.cherrygirl.pojo.WxResult;

import java.util.*;

/**
 * @author nannanness
 */
public class TalkUtil {

    private static String WX_TOKEN;
    private static Map<Long, List<WxMessage>> MESSAGES = new HashMap<>();

    private static Map<Long, Integer> USER_TOKENS = new HashMap<>();

    public static String talk(String uid, String msg){
        String url = "https://api.sizhi.com/bot?appid=d4523933a7fb4c49b6676b87404f7cc3&userid=" + uid + "&spoken=" + msg;
        String result = HttpUtil.get(url);
        Map map = JSONObject.parseObject(result, Map.class);
        String message = map.get("message").toString();
        String s = null;
        if("success".equals(message) || "请求成功".equals(message)){
            Map data = JSONObject.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            Map info = JSONObject.parseObject(JSON.toJSONString(data.get("info")), Map.class);
            s = info.get("text").toString();
            if(s.contains("小思")){
                s = s.replace("小思", "波波");
//                s = s.replace("小思", "小野");
            }
            if(s.contains("驾车大约")){
                s = "";
            }
        }
        return s;
    }
    public static String chatWxToken(){
        String result = HttpRequest.post("https://aip.baidubce.com/oauth/2.0/token?client_id=4QdODkNGCqn4GrKEvHjnGy3f&client_secret=PFGxcFkYddl4jZE45oimIidCYGBtYfPu&grant_type=client_credentials")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .execute().body();
        Map map = JSONObject.parseObject(result, Map.class);
        String token = map.get("access_token").toString();
        return token;
    }

    public static WxResult chatWx(Long qq, String message){
        WxMessage wxMessage = new WxMessage(WxMessage.ROLE_USER, message);
        if(MESSAGES.containsKey(qq)){
            List<WxMessage> wxMessages = MESSAGES.get(qq);
            wxMessages.add(wxMessage);
        } else {
            MESSAGES.put(qq, new ArrayList<>(List.of(wxMessage)));
        }
        Map map = new HashMap();
        map.put("messages", MESSAGES.get(qq));
        String jsonMessage = JSON.toJSONString(map);
        if(StringUtils.isEmpty(WX_TOKEN)){
            WX_TOKEN = chatWxToken();
        }
        String result = HttpRequest.post("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=" + WX_TOKEN)
                .header("Content-Type", "application/json")
                .body(jsonMessage)
                .execute().body();
        Map response = JSONObject.parseObject(result, Map.class);
        // 对话内容
        String respText = response.get("result").toString();
        // 是否关闭
        boolean clear = (boolean) response.get("need_clear_history");
        Map usage =  JSON.parseObject(response.get("usage").toString(), Map.class);
        // 总tokens
        int count = (int) usage.get("total_tokens");
        if(USER_TOKENS.containsKey(qq)){
            Integer tokenCount = USER_TOKENS.get(qq);
            USER_TOKENS.put(qq, tokenCount + count);
        } else {
            USER_TOKENS.put(qq, count);
        }
        if(clear){
            MESSAGES.remove(qq);
        } else {
            WxMessage wxMessage2 = new WxMessage(WxMessage.ROLE_ASSISTANT, respText);
            List<WxMessage> wxMessages = MESSAGES.get(qq);
            wxMessages.add(wxMessage2);
        }
        return new WxResult(respText, clear, count, WxResult.TYPE_TEXT, null);
    }

    public static WxResult imgWx(String path, String message){
        List<String> imgList = new ArrayList<>();
        Map map = new HashMap();
        map.put("prompt", message);
        map.put("negative_prompt", "lowres, bad anatomy, bad hands, text, error, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality, normal quality, jpeg artifacts, signature, watermark, username, blurry, bad feet");
        map.put("n", 4);
        map.put("sampler_index", "LMS");
        String jsonMessage = JSON.toJSONString(map);
        if(StringUtils.isEmpty(WX_TOKEN)){
            WX_TOKEN = chatWxToken();
        }
        try {
            String result = HttpRequest.post("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl?access_token=" + WX_TOKEN)
                    .header("Content-Type", "application/json")
                    .body(jsonMessage)
                    .timeout(1000 * 30)
                    .execute().body();
            Map response = JSONObject.parseObject(result, Map.class);
            if(response.containsKey("error_code")){
                int errCode = (int) response.get("error_code");
                switch (errCode){
                    case 336302:
                        return new WxResult(WxResult.TYPE_IMG_ILLEGAL, false, 0, WxResult.TYPE_IMG_FAIL, null);
                }
            }
            // 图片集合
            List<Map> data = JSONArray.parseArray(response.get("data").toString(), Map.class);
            data.forEach(map1 -> {
                String b64Image = map1.get("b64_image").toString();
                int index = (int) map1.get("index");
                String img = path + "/" + System.currentTimeMillis() + "_" + index + ".png";
                ImageMergeUtil.convertBase64StrToImage(b64Image, img);
                imgList.add(img);
            });

            Map usage =  JSON.parseObject(response.get("usage").toString(), Map.class);
            // 总tokens
            int count = (int) usage.get("total_tokens");

            return new WxResult(null, false, count, WxResult.TYPE_IMG, imgList);
        } catch (cn.hutool.http.HttpException httpException) {
            return new WxResult(WxResult.TYPE_IMG_TIME_OUT_TITLE, false, 0, WxResult.TYPE_IMG_FAIL, null);
        }

    }

    public static int clear(Long qq){
        int count = 0;
        if(MESSAGES.containsKey(qq)){
            count = USER_TOKENS.get(qq);
            USER_TOKENS.remove(qq);
            MESSAGES.remove(qq);
        }
        return count;
    }

    public static void main(String[] args) {
//        WxResult wxResult = chatWx(, "你是？");
//        System.out.println(JSON.toJSONString(wxResult));
//        WxResult wxResult2 = chatWx(, "用小红书的风格帮我写一篇肥皂安利文案");
//        System.out.println(JSON.toJSONString(wxResult2));
//        System.out.println(JSON.toJSONString(MESSAGES.get()));
//        WxResult wxResult = imgWx("E:\\IdeaProjects\\cherrygirl\\resouces\\image\\WXIMG", "猫咪;大象");
//        System.out.println(JSON.toJSONString(wxResult));
//        try {
//            WX_TOKEN = chatWxToken();
//            Map map = new HashMap();
//            map.put("prompt", "a burning house,digital painting,hyperrealistic,artstation,cinematic lighting");
//            map.put("n", 2);
//            map.put("sampler_index", "LMS");
//            String jsonMessage = JSON.toJSONString(map);
//            String result = HttpRequest.post("https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/text2image/sd_xl?access_token=" + WX_TOKEN)
//                    .header("Content-Type", "application/json")
//                    .body(jsonMessage)
//                    .timeout(5000)
//                    .execute().body();
//        }catch (cn.hutool.http.HttpException httpException){
//            WxResult wxResult = new WxResult(WxResult.TYPE_IMG_TIME_OUT_TITLE, false, 0, WxResult.TYPE_IMG_FAIL, null);
//            System.out.println(JSON.toJSONString(wxResult));
//        }
        int a = 1/2;
        int i = a + 1 > 2 - 1 ? 5 * 5 : 5 + 4;
        System.out.println(i);
    }
}
