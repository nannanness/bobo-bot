package org.cherrygirl.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.service.CloudMusicService;
import org.cherrygirl.utils.WyyUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nannaness
 */
@Service
public class CloudMusicServiceImpl implements CloudMusicService {
    @Override
    public Map search(String name , String singer) {
        // 搜索
        Map form = WyyUtil.getSearchParamFormData(name);
        String result = HttpUtil.post(WyyUtil.SEARCH_URL, form);
        List<Map> songList = parseSong(result);
        if(songList == null){
            return null;
        }
        if(StringUtils.isNotBlank(singer)){
            List<Map> tmpList = songList.stream().filter(map -> {
                List<String> singers = (List<String>) map.get("singers");
                return singers.contains(singer);
            }).collect(Collectors.toList());
            if(tmpList.size() != 0){
                songList = tmpList;
            }
        }
        if(songList.size() == 0){
            return null;
        }
        Map<String, Object> res = selectedSong(songList);
        if(res == null || res.isEmpty()){
            return null;
        }
        String src = res.get("src").toString();
        String pageUrl = res.get("pageUrl").toString();
        Map song = (Map) res.get("song");
        Map data = new HashMap(5);
        data.put("title", song.get("name"));
        data.put("summary", String.join("/", (List)song.get("singers")));
        data.put("jumpUrl", pageUrl);
        data.put("pictureUrl", song.get("picUrl"));
        data.put("musicUrl", src);
        data.put("brief", "[音乐]" + name + "/" + StringUtils.join(song.get("singers"), "/"));
        return data;
    }
    private Map<String, Object> selectedSong(List<Map> songList) {
        Map<String, Object> res = new HashMap<>();
        for(Map song : songList){
            if(song == null || song.isEmpty()){
                continue;
            }
            String id = song.get("id").toString();
            // page
            String pageUrl = WyyUtil.PAGE_URL + id;
            // get src
            Map srcForm = WyyUtil.getSrcParamFormData(id);
            String srcResult = HttpUtil.post(WyyUtil.GET_SRC_URL, srcForm);
            String src = parseSrc(srcResult);
            if(StringUtils.isNotEmpty(src)){
                res.put("src", src);
                res.put("pageUrl", pageUrl);
                res.put("song", song);
                break;
            }
        }
        System.out.println("random song : " + JSON.toJSONString(res));
        return res;
    }


    private String parseSrc(String result) {
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        List<Map> data = JSONArray.parseArray(JSON.toJSONString(res.get("data")), Map.class);
        Map srcMap = data.get(0);
        return srcMap.get("url").toString();
    }

    private List<Map> parseSong(String result){
        Map<String, String> res = JSONObject.parseObject(result, Map.class);
        Map<String, String> resultMap = JSONObject.parseObject(JSON.toJSONString(res.get("result")), Map.class);
        List<Map> songs = JSONArray.parseArray(JSON.toJSONString(resultMap.get("songs")), Map.class);
        if(songs == null || songs.size() == 0){
            return null;
        }
        List<Map> songList = new ArrayList<>();
        songs.forEach(map -> {
            // name
            String name = map.get("name").toString();
            // id
            int id = (int) map.get("id");
            // singers
            List<Map> arList = JSONArray.parseArray(JSON.toJSONString(map.get("ar")), Map.class);
            List<String> singers = arList.stream().map(ar -> ar.get("name").toString()).collect(Collectors.toList());
            // picUrl
            Map alMap = JSONObject.parseObject(JSON.toJSONString(map.get("al")), Map.class);
            String picUrl = alMap.get("picUrl").toString();
            Map tmp = new HashMap(4);
            tmp.put("name", name);
            tmp.put("id", id);
            tmp.put("singers", singers);
            tmp.put("picUrl", picUrl);
            songList.add(tmp);
        });
        System.out.println("--------------song list---------------");
        System.out.println(JSON.toJSONString(songList));
        return songList;
    }

    @Override
    public Map search(String text) {
        return search(text, null);
    }
}
