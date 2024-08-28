package org.cherrygirl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.LiveItemDO;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
public class DynamicUtil {

    /**
     * 获取直播up
     * @return
     */
    public static List<LiveItemDO> liveUsers(List<HttpCookie> cookies){
        HttpResponse response = HttpRequest.get("https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/w_live_users?size=10")
                .cookie(cookies)
                .execute();
//        System.out.println("-----body-----");
//        System.out.println(JSON.toJSONString(response.body()));
        Map map = JSON.parseObject(response.body(), Map.class);
        int code = (int) map.get("code");
        if(code == 0){
            Map data = JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            if(data.containsKey("items")){
                List<LiveItemDO> list = JSONArray.parseArray(JSON.toJSONString(data.get("items")), LiveItemDO.class);
//                System.out.println("LiveItemDo list: " + JSON.toJSONString(list));
                return list;
            }
        }
        return null;
    }

    /**
     * 获取发动态的up
     * @return
     */
    public static List<LiveItemDO> uplist(List<HttpCookie> cookies){
        HttpResponse response = HttpRequest.get("https://api.bilibili.com/x/polymer/web-dynamic/v1/portal")
                .cookie(cookies)
                .execute();
//        System.out.println("-----body-----");
//        System.out.println(JSON.toJSONString(response.body()));
        Map map = JSON.parseObject(response.body(), Map.class);
        int code = (int) map.get("code");
        List<LiveItemDO> upList = new ArrayList<>();
        if(code == 0){
            Map data = JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            List<Map> itemList = JSONArray.parseArray(JSON.toJSONString(data.get("up_list")), Map.class);
            for (Map item : itemList){
                boolean hasUpdate = (boolean) item.get("has_update");
                if(hasUpdate){
                    LiveItemDO liveItemDo = new LiveItemDO(Long.parseLong(item.get("mid").toString()), item.get("uname").toString(), item.get("face").toString(), null, null);
                    upList.add(liveItemDo);
                }
            }
//            System.out.println("up update upList: " + JSON.toJSONString(upList));
            return upList;
        }
        return null;
    }

    /**
     * 访问动态
     * @param cookies
     * @param uid
     * @return
     */
    public static String[] call(List<HttpCookie> cookies, long uid){
        String url = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/w_dyn_personal?host_uid=" + uid + "&offset=";
        HttpResponse response = HttpRequest.get(url)
                .cookie(cookies)
                .execute();
        return parseDynamicPage(response.body());
    }

    /**
     * 获取动态页面
     * @param body
     * @return
     */
    public static String[] parseDynamicPage(String body){
        String[] strings = new String[2];
        Map map = JSON.parseObject(body, Map.class);
        int code = (int) map.get("code");
        if(code == 0){
            Map data = JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            if(data.containsKey("cards")){
                List<Map> cards = JSONArray.parseArray(JSON.toJSONString(data.get("cards")), Map.class);
                for (Map item : cards){
                    Map desc = JSON.parseObject(JSON.toJSONString(item.get("desc")), Map.class);
                    String s = desc.get("dynamic_id_str").toString();
                    Map userProfile = JSON.parseObject(JSON.toJSONString(desc.get("user_profile")), Map.class);
                    Map info = JSON.parseObject(JSON.toJSONString(userProfile.get("info")), Map.class);

                    if(StringUtils.isNotEmpty(s)){
                        strings[0] = "https://t.bilibili.com/" + s + "?tab=2";
                        strings[1] = info.get("face").toString();
                        return strings;
                    }
                }
            }
        }
        return null;
    }
}
