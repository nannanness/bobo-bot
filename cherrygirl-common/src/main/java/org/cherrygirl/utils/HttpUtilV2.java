package org.cherrygirl.utils;

import com.alibaba.fastjson.JSON;
import org.cherrygirl.constant.HttpCode;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
public class HttpUtilV2 {


    /**
     * GET请求
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static String getJsonString(String requestUrl) {
        System.out.println("========requestUrl========");
        System.out.println(requestUrl);
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(requestUrl);
            // 通过远程url对象打开一个连接，强制转换为HttpUrlConnection类型
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：GET
            connection.setRequestMethod("GET");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(HttpCode.CONN_TIME_OUT);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(HttpCode.READ_TIME_OUT);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();
            System.out.println(JSON.toJSONString(connection));
            // 获取所有相应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            if (connection.getResponseCode() == HttpCode.RESP_SUCCESS_CODE) {
                // 通过connection连接，获取输入流
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sbf.append(line);
                }
                result = sbf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 关闭远程连接
            // 断开连接，最好写上，disconnect是在底层tcp socket链接空闲时才切断。如果正在被其他线程使用就不切断。
            // 固定多线程的话，如果不disconnect，链接会增多，直到收发不出信息。写上disconnect后正常一些
            connection.disconnect();

        }
        System.out.println("--------->>> GET request end <<<----------");
        System.out.println("--------->>> result:" + result);
        return result;
    }

}
