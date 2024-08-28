package org.cherrygirl.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.manager.PictureManager;
import org.cherrygirl.constant.PictureUrl;
import org.cherrygirl.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.*;

/**
 * @author nannanness
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureManager pictureManager;

    @Override
    public String getChuzhanPicture(String keyword) throws IOException {
        keyword = StringUtils.defaultIfBlank(keyword, pictureManager.Keywords[0]);
        int i = new Random().nextInt(10);
//        if(i%2 == 0){
//        }
        Map<String, Object> map = pictureManager.getCzParams(1, keyword);
        String result = HttpRequest.post(PictureUrl.CHUZHAN)
                .header(Header.CONTENT_TYPE, "multipart/form-data")
                .form(map)
                .execute().body();
        List<String> pathList = parseCzImg(result, keyword);
        if(pathList.size() > 0){
            pathList.removeIf(StringUtils::isBlank);
            String path = pathList.get(new Random().nextInt(pathList.size()));
            System.out.println("触站图片：" + PictureUrl.CHUZHAN_IMG + path);
            long download = HttpUtil.downloadFile(PictureUrl.CHUZHAN_IMG + path, FileUtil.file(pictureManager.getRootCz()));
            return pictureManager.getRootCz() + getFileName(path);
        } else {
            return getBaiduPicture(keyword);
        }
    }

    public String getBaiduPicture(String keyword){
        String filePath = UUID.randomUUID().toString() + ".jpg";
        String params = pictureManager.getBdParams(System.currentTimeMillis(), keyword);
        String result = HttpUtil.get(PictureUrl.BAIDU_IMG + params);
        List<String> pathList = parseBdImg(result);
        if(pathList.size() > 0){
            pathList.removeIf(StringUtils::isBlank);
            String path = pathList.get(new Random().nextInt(pathList.size()));
            long download = HttpUtil.downloadFile(path, new File(pictureManager.getRootBd() + filePath));
            return pictureManager.getRootBd() + filePath;
        }
        return filePath;
    }

    /**
     * 解析百度返回数据
     *
     * @param result
     * @return
     */
    private List<String> parseBdImg(String result){
        List<String> pathList = new ArrayList<>();
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        List<Map> data = JSONArray.parseArray(JSON.toJSONString(res.get("data")), Map.class);
        for(Map map : data) {
            if(map.containsKey("hoverURL")){
                String path = map.get("hoverURL").toString();
                pathList.add(path);
            }
        };
        System.out.println("BD PATH LIST:");
        System.out.println(JSON.toJSONString(pathList));
        return pathList;
    }

    /**
     * 解析触站返回数据
     *
     * @param result
     * @return
     */
    private List<String> parseCzImg(String result, String keyword){
        List<String> pathList = new ArrayList<>();
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        Map<String, Object> data = JSONObject.parseObject(JSON.toJSONString(res.get("data")), Map.class);
        if(data.containsKey("pageCount")){
            int pageCount = Integer.parseInt(data.get("pageCount").toString());
            int randomPageCount = new Random().nextInt(pageCount);
            System.out.println("randomPageCount: " + randomPageCount);
            Map<String, Object> params = pictureManager.getCzParams(randomPageCount, keyword);
            String randomResult = HttpRequest.post(PictureUrl.CHUZHAN)
                    .header(Header.CONTENT_TYPE, "multipart/form-data")
                    .form(params)
                    .execute().body();
            res = JSONObject.parseObject(randomResult, Map.class);
            data = JSONObject.parseObject(JSON.toJSONString(res.get("data")), Map.class);
            List<Map> works = JSONArray.parseArray(JSON.toJSONString(data.get("works")), Map.class);
            works.forEach(map -> {
                Map<String, String> coverImage =  JSONObject.parseObject(JSON.toJSONString(map.get("coverImage")), Map.class);
                String path = coverImage.get("path");
                pathList.add(path);
            });
            System.out.println("PATH LIST:");
            System.out.println(JSON.toJSONString(pathList));
        }
        return pathList;
    }

    private String getFileName(String path){
        String[] arr = path.split("/");
        if(arr.length > 0){
            return arr[arr.length - 1];
        }
        return null;
    }
}
