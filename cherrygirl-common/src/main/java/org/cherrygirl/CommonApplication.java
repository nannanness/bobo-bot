package org.cherrygirl;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.manager.*;
import org.cherrygirl.service.CloudMusicService;
import org.cherrygirl.service.impl.CloudMusicServiceImpl;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.ImageMergeUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
@SpringBootApplication
public class CommonApplication implements ApplicationRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(CommonApplication.class)
                // 指定非 web 模式
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        System.out.println("====== bot启动 =====");
//        GenshinManager genshinManager = applicationContext.getBean(GenshinManager.class);
//        List<String> tenCompaniesGif = genshinManager.getTenCompaniesGif("12312", "123123123");
//        tenCompaniesGif.forEach(System.out::println);
//        talkManager.addOneTalk(123456L, 156452L);
//        CoinManager coinManager = applicationContext.getBean(CoinManager.class);
//        coinManager.add100Coins(123456L, 156452L);
//        TalkDO talkDO = talkManager.viewTalks(123456L, 156452L);
//        CoinDO coinDO = coinManager.viewCoins(123456L, 156452L);
//        System.out.println("talk: " + JSON.toJSONString(talkDO) + " loadDate: " + talkDO.getLoadDate());
//        System.out.println("coin: " + JSON.toJSONString(coinDO));
//        SignInImgManager signInImgManager = applicationContext.getBean(SignInImgManager.class);
//        String path = "E:\\IdeaProjects\\cherrygirl\\resouces\\image\\signin\\source\\.png";
//        signInImgManager.getSignImg(path, "");

    }
}
