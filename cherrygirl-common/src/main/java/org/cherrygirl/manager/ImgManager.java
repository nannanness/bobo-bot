package org.cherrygirl.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ImgManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/DDD/";
    }

    public String uploadImg(String url){
        if(StringUtils.isNotEmpty(url)){
            String filePath = UUID.randomUUID().toString() + ".jpg";
            long download = HttpUtil.downloadFile(url, FileUtil.file(getRoot() + filePath));
            if(download > 0){
                return filePath;
            }
        }
        return null;
    }

    public String getImg(String name){
        return getRoot() + name;
    }
}
