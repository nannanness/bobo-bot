package org.cherrygirl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.cherrygirl.constant.UrlConstant;
import org.cherrygirl.domain.CombineDO;
import org.cherrygirl.domain.CookiesDO;
import org.cherrygirl.domain.LiveItemDO;
import org.cherrygirl.service.CookiesService;
import org.cherrygirl.service.impl.CookiesServiceImpl;

import java.net.HttpCookie;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
public class LoginUtil {

    /**
     * 人机认证 (网页进行)
     * @return
     */
    public static CombineDO combine(){
        String res = HttpUtil.get(UrlConstant.COMBINE);
        System.out.println("res: " + res);
        Map<String, String> json = JSONObject.parseObject(res, Map.class);
        Map<String, String> data = JSONObject.parseObject(JSON.toJSONString(json.get("data")), Map.class);
        Map result = JSONObject.parseObject(JSON.toJSONString(data.get("result")), Map.class);
        int success = (int)result.get("success");
        String gt = result.get("gt").toString();
        String challenge = result.get("challenge").toString();
        String key = result.get("key").toString();
        CombineDO combineDo = new CombineDO(success, gt, challenge, key);
        System.out.println(JSON.toJSONString(combineDo));
        return combineDo;
    }

    public static List<HttpCookie> getCookies(CookiesDO cookiesDo){
        String cookies = cookiesDo.getCookies();
        List<Map> mapList = JSONArray.parseArray(cookies, Map.class);
        List<HttpCookie> httpCookieList = new ArrayList<>();
        for (Map map : mapList){
            httpCookieList.add(new HttpCookie(map.get("name").toString(), map.get("value").toString()));
        }
        return httpCookieList;
    }


    /**
     * step1 发送短信验证码
     * @param combineDo
     */
    private static void send(CombineDO combineDo){
        Map paramMap = new HashMap();
        paramMap.put("cid","86");
        paramMap.put("tel","13168971510");
        paramMap.put("source","main_mini");
        paramMap.put("token",combineDo.getKeykey());
        paramMap.put("challenge",combineDo.getChallenge());
        paramMap.put("validate",combineDo.getValidate());
        paramMap.put("seccode",combineDo.getSeccode());
        HttpResponse response = HttpRequest.post("https://passport.bilibili.com/x/passport-login/web/sms/send")
                .form(paramMap)
                .execute();
        System.out.println("-----body-----");
        System.out.println(JSON.toJSONString(response.body()));
    }

    /**
     * step2 短信验证码登录
     * @param code
     * @return
     */
    private static List<HttpCookie> login(int code, String captchaKey){
        Map paramMap = new HashMap();
        paramMap.put("cid","86");
        paramMap.put("tel","13168971510");
        paramMap.put("source","main_mini");
        paramMap.put("code",code);
        paramMap.put("captcha_key",captchaKey);
        paramMap.put("go_url", "https://www.bilibili.com/");
        paramMap.put("keep", "0");
        HttpResponse response = HttpRequest.post("https://passport.bilibili.com/x/passport-login/web/login/sms")
                .form(paramMap)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        System.out.println("-----cookies-----");
        cookies.forEach(System.out::println);
        System.out.println("-----body-----");
        System.out.println(JSON.toJSONString(response.body()));
        return cookies;
    }

    public static void loginStep1(String key, String challenge, String validate, String seccode) {
        CombineDO combineDo = new CombineDO();
        combineDo.setKeykey(key);
        combineDo.setChallenge(challenge);
        combineDo.setValidate(validate);
        combineDo.setSeccode(seccode);
        send(combineDo);
    }

    public static CookiesDO loginStep2(int code, String captchaKey) {
//        List<HttpCookie> cookies = login(code, captchaKey);
//        CookiesDO cookiesDo = new CookiesDO(1, "bilibili", JSON.toJSONString(cookies), new Timestamp(System.currentTimeMillis()));
        return null;
    }
}
