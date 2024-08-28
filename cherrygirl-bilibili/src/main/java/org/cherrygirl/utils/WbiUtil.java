package org.cherrygirl.utils;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.pojo.BilibiliFingerResponseDataVO;
import org.cherrygirl.pojo.BilibiliFingerResponseVO;
import org.cherrygirl.pojo.BilibiliNavResponseVO;
import org.cherrygirl.pojo.BilibiliNavResponseDataWbiImgVO;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WbiUtil {

    private static String imgKey;
    private static String subKey;
    private static final int[] mixinKeyEncTab = new int[]{
            46, 47, 18, 2, 53, 8, 23, 32, 15, 50, 10, 31, 58, 3, 45, 35, 27, 43, 5, 49,
            33, 9, 42, 19, 29, 28, 14, 39, 12, 38, 41, 13, 37, 48, 7, 16, 24, 55, 40,
            61, 26, 17, 0, 1, 60, 51, 30, 4, 22, 25, 54, 21, 56, 59, 6, 63, 57, 62, 11,
            36, 20, 34, 44, 52
    };

    private static final char[] hexDigits = "0123456789abcdef".toCharArray();

    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            char[] result = new char[messageDigest.length * 2];
            for (int i = 0; i < messageDigest.length; i++) {
                result[i * 2] = hexDigits[(messageDigest[i] >> 4) & 0xF];
                result[i * 2 + 1] = hexDigits[messageDigest[i] & 0xF];
            }
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String getMixinKey(String imgKey, String subKey) {
        String s = imgKey + subKey;
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 32; i++)
            key.append(s.charAt(mixinKeyEncTab[i]));
        return key.toString();
    }

    private static String encodeURIComponent(Object o) {
        return URLEncoder.encode(o.toString(), StandardCharsets.UTF_8).replace("+", "%20");
    }

    public static String getSignParam(TreeMap<String, Object> map) {
        String mixinKey = getMixinKey(imgKey, subKey);
//        System.out.println(mixinKey); // 72136226c6a73669787ee4fd02a74c27
        // 用TreeMap自动排序
        map.put("wts", System.currentTimeMillis() / 1000);
        String param = map.entrySet().stream()
                .map(it -> String.format("%s=%s", it.getKey(), encodeURIComponent(it.getValue())))
                .collect(Collectors.joining("&"));
//        System.out.println(param);
        String s = param + mixinKey;
        String wbiSign = md5(s);
//        System.out.println(wbiSign);
        String finalParam = param + "&w_rid=" + wbiSign;
//        System.out.println(finalParam);
        return finalParam;
    }

    public static void getWbiImg(String cookies){
        String api = "https://api.bilibili.com/x/web-interface/nav";
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Cookie", cookies);
        String jsonBody = BilibiliApiRequestUtil.get(api, cookies, new HashMap<>(1), headerMap, false);
        BilibiliNavResponseVO bilibiliNavResponseVO = JSON.parseObject(jsonBody, BilibiliNavResponseVO.class);
        BilibiliNavResponseDataWbiImgVO wbiImg = bilibiliNavResponseVO.getData().getWbi_img();
        String imgUrl = wbiImg.getImg_url();
        String subUrl = wbiImg.getSub_url();
        BilibiliNavResponseDataWbiImgVO bilibiliNavResponseDataWbiImgVO = matcherUrl(imgUrl, subUrl);
        setImgKey(bilibiliNavResponseDataWbiImgVO.getImg_url());
        setSubKey(bilibiliNavResponseDataWbiImgVO.getSub_url());
        System.out.println("wbi: " + JSON.toJSONString(bilibiliNavResponseDataWbiImgVO));
    }

    public static BilibiliFingerResponseDataVO getFinger(){
        String api = "https://api.bilibili.com/x/frontend/finger/spi";
        //header
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "api.bilibili.com");
        headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36");
        String jsonBody = BilibiliApiRequestUtil.get(api, null, new HashMap<>(1), headerMap, false);
        BilibiliFingerResponseVO bilibiliFingerResponseVO = JSON.parseObject(jsonBody, BilibiliFingerResponseVO.class);

        System.out.println("finger: " + JSON.toJSONString(bilibiliFingerResponseVO.getData()));
        return bilibiliFingerResponseVO.getData();
    }

    private static BilibiliNavResponseDataWbiImgVO matcherUrl(String imgUrl, String subUrl){
        BilibiliNavResponseDataWbiImgVO wbiImg = new BilibiliNavResponseDataWbiImgVO();
        String pattern = "[A-Za-z0-9]+.png$";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 imgUrl matcher 对象
        Matcher m = r.matcher(imgUrl);
        if (m.find( )) {
            String group = m.group(0);
            String replace = StringUtils.replace(group, ".png", "");
            wbiImg.setImg_url(replace);
        }
        // 现在创建 subUrl matcher 对象
        Matcher m2 = r.matcher(subUrl);
        if (m2.find( )) {
            String group = m2.group(0);
            String replace = StringUtils.replace(group, ".png", "");
            wbiImg.setSub_url(replace);
        }
        return wbiImg;
    }

    private static void setImgKey(String imgKey) {
        WbiUtil.imgKey = imgKey;
    }

    private static void setSubKey(String subKey) {
        WbiUtil.subKey = subKey;
    }
}
