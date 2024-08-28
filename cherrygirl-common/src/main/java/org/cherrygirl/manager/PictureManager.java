package org.cherrygirl.manager;

import com.alibaba.fastjson.JSON;
import org.cherrygirl.config.ResourcesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nannanness
 */
@Service
public class PictureManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    public String getRootCz(){
        return resourcesConfig.getRoot() + "/image/CZ/";
    }

    public String getRootBd(){
        return resourcesConfig.getRoot() + "/image/BD/";
    }

    public String[] Keywords = {"美少女"};

    public Map<String, Object> getCzParams(Integer index, String word){
        Map<String, Object> map = new HashMap<>();
        map.put("word", word);
        map.put("index", 1);
        return map;
    }

    public String getBdParams(long ts, String title){
        return "?tn=resultjson_com&logid=2566848612575262032&ipn=rj&ct=201326592&is=&fp=result&queryWord=" + title
            + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=&hd=&latest=&copyright=&word=" + title
            + "&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&expermode=&nojc=&pn=60&rn=30&gsm=3c&" + ts
            + "=";
    }
}
