package org.cherrygirl.manager;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

@Service
public class TextManager {

    @Autowired
    private TextService textService;

    public boolean uploadText(String text, Integer type){
        if(StringUtils.isNotEmpty(text)){
            return textService.addText(text, type);
        }
        return false;
    }

    public boolean uploadText(String text){
        if(StringUtils.isNotEmpty(text)){
            return textService.addText(text);
        }
        return false;
    }

    public String getText(Integer type){
        byte[] text = textService.getRandomText(type).getText();
        return new String(text);
    }

    public String getText(){
        return textService.getTextStr();
    }
}
