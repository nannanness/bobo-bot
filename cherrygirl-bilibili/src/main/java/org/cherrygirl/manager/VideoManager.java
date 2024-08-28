package org.cherrygirl.manager;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.pojo.*;
import org.cherrygirl.service.CookiesService;
import org.cherrygirl.utils.BilibiliApiRequestUtil;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.ThreadPoolUtil;
import org.cherrygirl.utils.WbiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 视频解析manager
 */
@Service
public class VideoManager {

    @Autowired
    private CookiesService cookiesService;


    @Autowired
    private ResourcesConfig resourcesConfig;

    @Autowired
    private ScancodeLoginManager scancodeLoginManager;

    public String getDownlownFile(){
        return resourcesConfig.getRoot() + "/bilibiliDownloadVideo/";
    }

    /**
     * 访问视频信息
     */
    public BilibiliInterfaceResponseVO getVideoInformation(Long aid, String bvid){
        String api = "https://api.bilibili.com/x/web-interface/view";
        String cookies = scancodeLoginManager.getStringCookies();
        Map<String, Object> paramMap = new HashMap<>();
        if(aid != null) {
            paramMap.put("aid", aid);
        }
        if(StringUtils.isNotEmpty(bvid)) {
            paramMap.put("bvid", bvid);
        }
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookies);
        String jsonBody = BilibiliApiRequestUtil.get(api, cookies, paramMap, headerMap, false);
        return JSON.parseObject(jsonBody, BilibiliInterfaceResponseVO.class);
    }

    /**
     * 获取下载视频流地址
     */
    public List<String> getPlayerVideoUrl(Long aid, String bvid, Long cid) {
        String api = "https://api.bilibili.com/x/player/wbi/playurl";
        String cookies = scancodeLoginManager.getStringCookies();
        Map<String, Object> paramMap = new HashMap<>();
        if(aid != null) {
            paramMap.put("aid", aid);
        }
        if(StringUtils.isNotEmpty(bvid)) {
            paramMap.put("bvid", bvid);
        }
        paramMap.put("cid", cid);
        paramMap.put("qn", 64);
        paramMap.put("fnval", 1);
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookies);
        headerMap.put("Host", "api.bilibili.com");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
        String jsonBody = BilibiliApiRequestUtil.get(api, cookies, paramMap, headerMap, true);
        BilibiliVideoStreamResponseVO bilibiliVideoStreamResponseVO = JSON.parseObject(jsonBody, BilibiliVideoStreamResponseVO.class);
        BilibiliVideoStreamResponseDataVO data = bilibiliVideoStreamResponseVO.getData();
        List<BilibiliVideoStreamResponseDataUrlVO> durl = data.getDurl();
        List<String> urlList = new ArrayList<>();
        durl.forEach(durl1 -> {
            urlList.add(durl1.getUrl());
            urlList.addAll(durl1.getBackup_url());
        });
        return urlList;
    }

    /**
     * 获取ai视频摘要
     */
    public String getAiConclusion(Long aid, String bvid, Long cid, Long mid){
        String api = "https://api.bilibili.com/x/web-interface/view/conclusion/get";
        String cookies = scancodeLoginManager.getStringCookies();
        Map<String, Object> paramMap = new HashMap<>();
        if(aid != null) {
            paramMap.put("aid", aid);
        }
        if(StringUtils.isNotEmpty(bvid)) {
            paramMap.put("bvid", bvid);
        }
        paramMap.put("cid", cid);
        paramMap.put("up_mid", mid);
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookies);
        headerMap.put("Host", "api.bilibili.com");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
        String jsonBody = BilibiliApiRequestUtil.get(api, cookies, paramMap, headerMap, true);
        BilibiliVideoConclusionResponseVO videoConclusionVO = JSON.parseObject(jsonBody, BilibiliVideoConclusionResponseVO.class);
        String summary = null;
        if(videoConclusionVO.getCode() == 0 && videoConclusionVO.getData().getCode() == 0 && videoConclusionVO.getData().getModel_result().getResult_type() != 0){
            summary = videoConclusionVO.getData().getModel_result().getSummary();
        }
        return summary;
    }

    public Long downloadVideo(List<String> urlList, String filename) {
        Callable<Long> callable = () -> {
            File file = new File(filename);
            String cookies = scancodeLoginManager.getStringCookies();
            for(String url : urlList) {
                OkHttpClient httpClient = new OkHttpClient();
                Request getRequest = new Request.Builder()
                        .header("Cookie", cookies)
                        .header("Referer", "https://www.bilibili.com")
                        .header("Host", "api.bilibili.com")
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36")
                        .url(url)
                        .get()
                        .build();
                //准备好请求的Call对象
                Call call = httpClient.newCall(getRequest);
                String strBody = "";
                try {
                    Response response = call.execute();
                    ResponseBody body = response.body();
                    InputStream inputStream = body.byteStream();
                    boolean b = FileUtil.saveToFile(inputStream, filename);
                    if(b){
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            System.out.println("file length: " + file.length());
            return file.length();
        };
        return (Long)ThreadPoolUtil.running(callable);
    }

}
