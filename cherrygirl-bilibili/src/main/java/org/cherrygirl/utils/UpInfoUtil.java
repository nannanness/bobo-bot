package org.cherrygirl.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.net.HttpCookie;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
public class UpInfoUtil {

    /**
     * 获取粉丝数量
     *
     * @param uid
     * @return
     */
    public static long getFans(long uid){
        String url = "https://api.bilibili.com/x/relation/stat?vmid=" + uid + "&jsonp=jsonp";
        HttpResponse response = HttpRequest.get(url)
                .execute();
        Map map = JSON.parseObject(response.body(), Map.class);
        int code = (int) map.get("code");
        if(code == 0){
            Map data = JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            long fans = (int) data.get("follower");
            return fans;
        }
        return -1;
    }

    /**
     * 获取舰长数量
     *
     * @param uid
     * @return
     */
    public static long getCaptain(long roomId, long uid){
        String url = "https://api.live.bilibili.com/xlive/app-room/v2/guardTab/topList?page_size=1&page=1&roomid=" + roomId + "&ruid=" + uid;
        HttpResponse response = HttpRequest.get(url)
                .execute();
        Map map = JSON.parseObject(response.body(), Map.class);
        int code = (int) map.get("code");
        if(code == 0){
            Map data = JSON.parseObject(JSON.toJSONString(map.get("data")), Map.class);
            Map info = JSON.parseObject(JSON.toJSONString(data.get("info")), Map.class);
            long num = (int) info.get("num");
            return num;
        }
        return -1;
    }


}
