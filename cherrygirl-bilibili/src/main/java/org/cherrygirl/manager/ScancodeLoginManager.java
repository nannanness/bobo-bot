package org.cherrygirl.manager;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.domain.CookiesDO;
import org.cherrygirl.pojo.BilibiliApplyQRCodeResponseVO;
import org.cherrygirl.pojo.NameValueVO;
import org.cherrygirl.pojo.BilibiliScanQRCodeLoginResponseVO;
import org.cherrygirl.service.CookiesService;
import org.cherrygirl.utils.CalenderUtil;
import org.cherrygirl.utils.CodeUtil;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.HttpCookie;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 扫码登录
 */
@Service
public class ScancodeLoginManager {


    @Autowired
    private CookiesService cookiesService;


    @Autowired
    private ResourcesConfig resourcesConfig;

    private String getQRFile(){
        return resourcesConfig.getRoot() + "/bilibiliQRCode/";
    }

    /**
     * step1 申请二维码URL及扫码密钥（web端）
     */
    private BilibiliApplyQRCodeResponseVO applyForQRCode(){
        HttpResponse response = HttpRequest.get("https://passport.bilibili.com/x/passport-login/web/qrcode/generate")
                .execute();
        String body = response.body();
        Map<String, Object> mapBody = JSON.parseObject(body, Map.class);
        if(mapBody.containsKey("code") && mapBody.containsKey("data")){
            if((Integer) mapBody.get("code") == 0){
                String jsonData = JSON.toJSONString(mapBody.get("data"));
                Map<String, String> mapData = JSON.parseObject(jsonData, Map.class);
//                mapData.forEach((k,v) -> System.out.println(k + ":" + v));
                return new BilibiliApplyQRCodeResponseVO(mapData.get("qrcode_key"), mapData.get("url"));
            }
        }
        return null;
    }

    /**
     * step2 使用扫码登录（web端）
     */
    private boolean useQRCodeLogin(String code){
        HttpResponse response = HttpRequest.get("https://passport.bilibili.com/x/passport-login/web/qrcode/poll?qrcode_key=" + code)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        String body = response.body();
//        System.out.println("-----cookies-----");
//        cookies.forEach(System.out::println);
        BilibiliScanQRCodeLoginResponseVO bilibiliScanQRCodeLoginResponseVO = JSONObject.parseObject(body, BilibiliScanQRCodeLoginResponseVO.class);
        if(bilibiliScanQRCodeLoginResponseVO.getCode() == 0 && bilibiliScanQRCodeLoginResponseVO.getData().getCode() == 0){
            CookiesDO cookiesDo = new CookiesDO();
            cookiesDo.setLoginTime(new Timestamp(System.currentTimeMillis()));
            cookiesDo.setCookies(turnJsonCookies(cookies));
            cookiesDo.setIsv("bilibili");
            cookiesDo.setId(2);
            cookiesService.updateById(cookiesDo);
            return false;
        }else {
            System.out.println(bilibiliScanQRCodeLoginResponseVO.getData().getMessage());
            return true;
        }
    }

    /**
     * cookie转换json
     * @param cookies
     * @return
     */
    private String turnJsonCookies(List<HttpCookie> cookies){
        List<NameValueVO> list = new ArrayList<>();
        cookies.forEach(httpCookie -> {
            String name = httpCookie.getName();
            String value = httpCookie.getValue();
            list.add(new NameValueVO(name, value));
        });
        return JSON.toJSONString(list);
    }

    public void login() throws ParseException {
        CookiesDO cookiesDO = cookiesService.getById(1);
        //需要重新登录获取cookie
        boolean needLoinReCookie = true;
        if(cookiesDO != null){
            needLoinReCookie = CalenderUtil.oneMonthDifference(cookiesDO.getLoginTime().getTime(), System.currentTimeMillis());
        }
        if(needLoinReCookie){
            BilibiliApplyQRCodeResponseVO bilibiliApplyQRCodeResponseVO = applyForQRCode();
            CodeUtil.createCodeToFile(bilibiliApplyQRCodeResponseVO.getUrl(), new File(getQRFile()), "qr.jpg");
            boolean flag = true;
            while (flag){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                boolean b = useQRCodeLogin(bilibiliApplyQRCodeResponseVO.getQrcodeKey());
                System.out.println(b);
                flag = b;
            }
            FileUtil.deleteDir(getQRFile());
        }
    }

    public List<HttpCookie> getCookies(){
        CookiesDO cookiesDO = cookiesService.getById(1);
        String cookies = cookiesDO.getCookies();
        List<Map> mapList = JSONArray.parseArray(cookies, Map.class);
        List<HttpCookie> httpCookieList = new ArrayList<>();
        for (Map map : mapList){
//            System.out.println(map.get("name").toString() + ":" + map.get("value").toString());
            httpCookieList.add(new HttpCookie(map.get("name").toString(), map.get("value").toString()));
        }
        return httpCookieList;
    }

    public String getStringCookies(){
        CookiesDO cookiesDO = cookiesService.getById(1);
        String cookies = cookiesDO.getCookies();
        List<Map> mapList = JSONArray.parseArray(cookies, Map.class);
        StringBuilder builder = new StringBuilder();
        for (Map map : mapList){
//            System.out.println(map.get("name").toString() + ":" + map.get("value").toString());
            builder.append(map.get("name").toString());
            builder.append("=");
            builder.append(map.get("value").toString());
            builder.append(";");
        }
        String strCookie = builder.toString();
//        System.out.println(strCookie);
        return strCookie.substring(0, strCookie.length());
    }

    public String getStringCookies(List<NameValueVO> list){
        CookiesDO cookiesDO = cookiesService.getById(1);
        String cookies = cookiesDO.getCookies();
        List<Map> mapList = JSONArray.parseArray(cookies, Map.class);
        List<Map> collect = list.stream().map(nameValueVO -> {
            Map map = new HashMap();
            map.put("name", nameValueVO.getName());
            map.put("value", nameValueVO.getValue());
            return map;
        }).toList();
        mapList.addAll(collect);
        StringBuilder builder = new StringBuilder();
        for (Map map : mapList){
//            System.out.println(map.get("name").toString() + ":" + map.get("value").toString());
            builder.append(map.get("name").toString());
            builder.append("=");
            builder.append(map.get("value").toString());
            builder.append(";");
        }
        String strCookie = builder.toString();
        System.out.println("string-cookie:" + strCookie);
        return strCookie.substring(0, strCookie.length());
    }

}
