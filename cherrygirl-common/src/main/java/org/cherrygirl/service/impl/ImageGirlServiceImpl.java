package org.cherrygirl.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.constant.RollToolsApi;
import org.cherrygirl.pojo.ImageGirlVO;
import org.cherrygirl.service.ImageGirlService;
import org.cherrygirl.manager.RollToolsApiManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nannanness
 */
@Service
public class ImageGirlServiceImpl implements ImageGirlService {

    @Autowired
    private RollToolsApiManager rollToolsApiManager;

    @Override
    public String queryImageGirl() {
        String filePath = UUID.randomUUID().toString() + ".jpg";
        String result = HttpUtil.get(RollToolsApi.API_BASE_URL + RollToolsApiManager.getRollParams("fl_img"));
        List<String> pathList = parseImg(result);
        if(pathList.size() > 0){
            pathList.removeIf(StringUtils::isBlank);
            String path = pathList.get(new Random().nextInt(pathList.size()));
            long download = HttpUtil.downloadFile(path, FileUtil.file(rollToolsApiManager.getRoot() + filePath));
            return rollToolsApiManager.getRoot() + filePath;
        }
        return null;
    }

    @Override
    public String getJokes() {
        String result = HttpUtil.get(RollToolsApi.API_BASE_URL + RollToolsApiManager.getRollParams("jokes"));
        List<String> textList = parseJokes(result);
        String text = textList.get(new Random().nextInt(textList.size()));
        return text;
    }

    private List<String> parseJokes(String result){
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        List<Map> data = JSONArray.parseArray(JSON.toJSONString(res.get("data")), Map.class);
        List<String> textList = new ArrayList<>();
        data.forEach(map -> {
            String text = map.get("content").toString();
            textList.add(text);
        });
        System.out.println("TEXT LIST:");
        System.out.println(JSON.toJSONString(textList));
        return textList;
    }

    private List<String> parseImg(String result){
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        List<ImageGirlVO> data = JSONArray.parseArray(JSON.toJSONString(res.get("data")), ImageGirlVO.class);
        List<String> pathList = new ArrayList<>();
        data.forEach(girlDo -> {
            String path = girlDo.getImageUrl();
            pathList.add(path);
        });
        System.out.println("PATH LIST:");
        System.out.println(JSON.toJSONString(pathList));
        return pathList;
    }
}
