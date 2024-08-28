package org.cherrygirl.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.pojo.BilibiliInterfaceResponseVO;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * bilibili接口统一访问方法
 */
public class BilibiliApiRequestUtil {

    /**
     * GET方法
     * @param apiUrl url
     * @param cookies cookie
     * @param paramMap url参数
     * @param headerMap 请求头
     * @param needWbi 是否需要wbi鉴权
     * @return
     */
    public static String get(String apiUrl, String cookies, Map<String, Object> paramMap, Map<String, String> headerMap, boolean needWbi){
        String param = null;
        if(needWbi){
            WbiUtil.getWbiImg(cookies);
            TreeMap<String, Object> map = new TreeMap<>(paramMap);
            param = WbiUtil.getSignParam(map);
        }else {
            param = paramMap.entrySet().stream()
                    .map(it -> String.format("%s=%s", it.getKey(), it.getValue()))
                    .collect(Collectors.joining("&"));
        }
        //同步请求
        OkHttpClient httpClient = new OkHttpClient();
        String url = apiUrl + "?" + param;
        System.out.println("bilibiliRequestGet api: " + url);
        Request.Builder builder = new Request.Builder();
        headerMap.forEach(builder::header);
        Request getRequest = builder.url(url)
                .get()
                .build();

        //准备好请求的Call对象
        Call call = httpClient.newCall(getRequest);
        String jsonBody = null;
        try {
            Response response = call.execute();
            ResponseBody body = response.body();
            jsonBody = body.string();
            System.out.println("bilibiliRequestGet response: " + jsonBody);
            return jsonBody;
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
