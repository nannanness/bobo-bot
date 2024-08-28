package org.cherrygirl.utils;
import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.symmetric.AES;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nannanness
 */
public class WyyUtil {

    public static final String SEARCH_URL = "https://music.163.com/weapi/cloudsearch/get/web?csrf_token=";
    public static final String PAGE_URL = "https://music.163.com/#/song?id=";
    public static final String GET_SRC_URL = "https://music.163.com/weapi/song/enhance/player/url/v1?csrf_token=";


    private static final String PUB_KEY = "010001";
    private static final String NONCE = "0CoJUm6Qyw8W8jud";
    private static final String MODULUS = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
    private static final String SECRET_KEY = "4YxDy7YCJJh2biXy";
    private static final String ENC_SEC_KEY = "5c33af8274cf5fdd11b879f0eacf2b038a2a2ba6c34d164be03ce0c69820427cb09e2139e26bff0e4ac2a14e2995bca06df50bc446970d6a4e53fcb4d1ab501f4ce602abe7fe0fb0da5ade838f58e23f01d8ee4f89e1f93ba14bfeadec03657dc32e92f5f2bf864fae35dd6facfbc1dc3fbde70fcfde262cdde195d44a06af09";


    private static String aesEncrypt(String text, String secKey) {
        AES aes = new AES("CBC", "PKCS7Padding", secKey.getBytes(), "0102030405060708".getBytes());
        byte[] encrypt = aes.encrypt(text);
        return Base64.encode(encrypt);
    }

    private static Map getSearchParam(String ids, String offset, String limit) {
        offset = StringUtils.defaultString(offset, "0");
        limit = StringUtils.defaultString(limit, "30");
        Map map = new HashMap<>(8);
        map.put("csrf_token", "");
        map.put("hlposttag", "</span>");
        map.put("hlpretag", "<span class=\"s-fc7\">");
        map.put("limit", limit);
        map.put("offset", offset);
        map.put("s", ids);
        map.put("total", "true");
        map.put("type", "1");
        return map;
    }
    private static Map getSrcParam(String ids) {
        Map map = new HashMap<>(4);
        map.put("ids", ids);
        map.put("level", "standard");
        map.put("encodeType", "aac");
        map.put("csrf_token", "");
        return map;
    }

    public static Map getSearchParamFormData(String name){
        Map param = getSearchParam(name, null, null);
        System.out.println("searchParam: " + JSON.toJSONString(param));
        String encText = aesEncrypt(aesEncrypt(JSON.toJSONString(param), NONCE), SECRET_KEY);
        System.out.println("search encText: " + encText);
        Map data = new HashMap(2);
        data.put("params",encText);
        data.put("encSecKey",ENC_SEC_KEY);
        return data;
    }

    public static Map getSrcParamFormData(String ids){
        Map srcParam = getSrcParam("[" + ids + "]");
        System.out.println("srcParam: " + JSON.toJSONString(srcParam));
        String encText = aesEncrypt(aesEncrypt(JSON.toJSONString(srcParam), NONCE), SECRET_KEY);
        System.out.println("src encText: " + encText);
        Map data = new HashMap(2);
        data.put("params",encText);
        data.put("encSecKey",ENC_SEC_KEY);
        return data;
    }
}
