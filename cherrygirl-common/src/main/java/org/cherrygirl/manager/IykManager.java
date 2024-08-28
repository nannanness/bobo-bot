package org.cherrygirl.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.cherrygirl.config.ResourcesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author nannanness
 */
@Service
public class IykManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    /**
     * 获取手机咨询
     * @param msg
     * @return
     */
    public List<Map> getPhone(String msg){
        String url = "https://api.iyk0.com/sjzx/?msg=" + msg;
        HttpResponse response = HttpRequest.get(url)
                .execute();
        String body = response.body();
        List<Map> mapList = new ArrayList<>();
        Map map = JSONObject.parseObject(body, Map.class);
        int code = (int) map.get("code");
        if(code == 200){
            List<Map> list = JSONArray.parseArray(JSON.toJSONString(map.get("data")), Map.class);
            int limit = Math.min(list.size(), 2);
            List<Map> collect = list.stream().limit(limit).collect(Collectors.toList());
            mapList.addAll(collect);
        }
        return mapList;
    }

    /**
     * 二次元图片
     * @return
     */
    public String getLli(){
        String url = "https://api.iyk0.com/ecy/api.php";
        String path = resourcesConfig.getRoot() + "/image/iyk/lli/";
        String file = UUID.randomUUID().toString() + ".png";
        String filePath = path + file;
        HttpUtil.downloadFile(url, FileUtil.file(filePath));
        return filePath;
    }

    /**
     * 获取小姐姐
     * @return
     */
    public String getXjj(){
        String url = "https://api.iyk0.com/luoli";
        String path = resourcesConfig.getRoot() + "/image/iyk/xjj/";
        String file = UUID.randomUUID().toString() + ".jpg";
        String filePath = path + file;
        HttpUtil.downloadFile(url, FileUtil.file(filePath));
        return filePath;
    }

    /**
     * qq估价
     * @return
     */
    public Map getQqgj(long qq){
        String url = "https://api.iyk0.com/qqgj/?qq=" + qq;
        String s = HttpUtil.get(url);
        Map map = JSONObject.parseObject(s, Map.class);
        int code = (int) map.get("code");
        if(code == 200){
            map.remove("code");
            return map;
        }
        return null;
    }

    /**
     * 挑战古诗词 - 拿题
     * @return
     */
    public String getTzgsc(long uuid){
        String url = "https://api.iyk0.com/tzgsc/?msg=&id=" + uuid;
        return HttpUtil.get(url);
    }

    /**
     * 挑战古诗词 - 答题
     * @return
     */
    public String getTzgsc(String msg, long uuid){
        String url = "https://api.iyk0.com/tzgsc/?msg=" + msg +"&id=" + uuid;
        return HttpUtil.get(url);
    }

    /**
     * 搜动漫
     * @return
     */
    public String getTxmh(String msg, long id){
        String url = "https://api.iyk0.com/txmh/?msg=" + msg + "&n=1";
        String s = HttpUtil.get(url);
        Map map = JSONObject.parseObject(s, Map.class);
        int code = (int) map.get("code");
        if(code == 200){
            return map.get("url").toString();
        }
        return null;
    }

    /**
     * 每日一图
     * @return
     */
    public Map getMryt(){
        String url = "https://api.iyk0.com/mryt/";
        String path = resourcesConfig.getRoot() + "/image/iyk/mryt/";
        String file = UUID.randomUUID().toString() + ".jpg";
        String filePath = path + file;
        String s = HttpUtil.get(url);
        System.out.println("getMryt : " + s);
        Map map = JSONObject.parseObject(s, Map.class);
        int code = (int) map.get("code");
        if(code == 1){
            List<Map> list = JSONArray.parseArray(JSON.toJSONString(map.get("data")), Map.class);
            Map map1 = list.get(new Random().nextInt(list.size()));
            HttpUtil.downloadFile(map1.get("imgurl").toString(), FileUtil.file(filePath));
            map1.put("filePath", filePath);
            return map1;
        }
        return null;
    }

    /**
     * 看图猜成语
     * @return
     */
    public Map getKtc(){
        String url = "https://api.iyk0.com/ktc/";
        String path = resourcesConfig.getRoot() + "/image/iyk/ktc/";
        String file = UUID.randomUUID().toString() + ".jpg";
        String filePath = path + file;
        String s = HttpUtil.get(url);
        Map map = JSONObject.parseObject(s, Map.class);
        int code = (int) map.get("code");
        if(code == 200){
            HttpUtil.downloadFile(map.get("img").toString(), FileUtil.file(filePath));
            map.put("filePath", filePath);
            return map;
        }
        return null;
    }


}
