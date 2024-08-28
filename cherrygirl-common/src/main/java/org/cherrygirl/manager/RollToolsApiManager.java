package org.cherrygirl.manager;

import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.constant.RollToolsApi;
import org.cherrygirl.manager.PictureManager;
import org.cherrygirl.pojo.RollToolsApiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nannanness
 */
@Service
public class RollToolsApiManager {

    @Autowired
    private ResourcesConfig resourcesConfig;

    public String getRoot(){
        return resourcesConfig.getRoot() + "/image/roll/";
    }

    public static String getRollParams(String apiType){
        RollToolsApiVO rollToolsApiVO = RollToolsApi.apiMap.get(apiType);
        if(rollToolsApiVO.getParamList() == null){
            return rollToolsApiVO.getApi() + "?app_id=" + RollToolsApi.APP_ID + "&app_secret=" + RollToolsApi.APP_SECRET;
        }
        return null;
    }
}
