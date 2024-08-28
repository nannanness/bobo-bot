package org.cherrygirl;


import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import org.cherrygirl.domain.CookiesDO;
import org.cherrygirl.domain.FansDO;
import org.cherrygirl.domain.LiveItemDO;
import org.cherrygirl.domain.UpDO;
import org.cherrygirl.manager.ScancodeLoginManager;
import org.cherrygirl.manager.SpaceManager;
import org.cherrygirl.manager.VideoManager;
import org.cherrygirl.pojo.BilibiliInterfaceResponseVO;
import org.cherrygirl.pojo.BilibiliWbiSearchResponseDataListVListVO;
import org.cherrygirl.service.CookiesService;
import org.cherrygirl.service.FansService;
import org.cherrygirl.service.UpService;
import org.cherrygirl.service.impl.CookiesServiceImpl;
import org.cherrygirl.service.impl.FansServiceImpl;
import org.cherrygirl.service.impl.UpServiceImpl;
import org.cherrygirl.utils.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.List;

/**
 * @author nannanness
 */
@SpringBootApplication
public class BilibiliApplication implements ApplicationRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(BilibiliApplication.class)
                // 指定非 web 模式
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        System.out.println("====== bili启动 =====");
//        CookiesService cookiesService = applicationContext.getBean(CookiesServiceImpl.class);
//        FansService fansService = applicationContext.getBean(FansServiceImpl.class);
//        UpService upService = applicationContext.getBean(UpServiceImpl.class);
//
//        ScancodeLoginManager scancodeLoginManager = applicationContext.getBean(ScancodeLoginManager.class);
//        scancodeLoginManager.login();
//        VideoManager videoManager = applicationContext.getBean(VideoManager.class);
//        SpaceManager spaceManager = applicationContext.getBean(SpaceManager.class);
//        List<BilibiliWbiSearchResponseDataListVListVO> allWorks = spaceManager.getAllWorks(365188473L);
//        System.out.println("allWorks: " + JSON.toJSONString(allWorks));
//        BilibiliInterfaceResponseVO resultVO = videoManager.getVideoInformation(null, "BV1qZ421N7jd");
//        System.out.println(JSON.toJSONString(resultVO));
//        List<String> playerVideoUrlList = videoManager.getPlayerVideoUrl(null, resultVO.getData().getBvid(), resultVO.getData().getCid());
//        System.out.println(JSON.toJSONString(playerVideoUrlList));
//        File folderFile = new File(videoManager.getDownlownFile(), resultVO.getData().getOwner().getName() + "_" + resultVO.getData().getTitle() + ".mp4");
//        videoManager.downloadVideo(playerVideoUrlList, folderFile.getAbsolutePath());

    }

    /**
     * 获取as数据
     * @param upService
     * @param fansService
     */
    public static void getAsData(UpService upService, FansService fansService) {
        List<UpDO> upDOs = upService.list();
        for(UpDO upDo : upDOs){
            long uid = upDo.getUid();
            long roomId = upDo.getRoomId();
            String name = upDo.getName();
            long fans = UpInfoUtil.getFans(uid);
            long nums = UpInfoUtil.getCaptain(roomId, uid);
            FansDO fansDo = new FansDO(uid, fans, nums);
            System.out.println("name: " + name + " 粉丝：" + fans + " 舰长数：" + nums);
            fansService.updateFans(fansDo);
        }
    }

    /**
     * 获取短信验证码，参数通过人机认证取得
     * @param cookiesService
     */
    private void loginStep1(CookiesService cookiesService){
        LoginUtil.loginStep1("56ad5ef96a7d401b9c8af80e3b6fdac8", "d9a79f68a312578cafed346da5793da2",
                "7045d7268eea952aae12c07c62579de7", "7045d7268eea952aae12c07c62579de7");
    }

    /**
     * 输入短信验证码登录
     * @param cookiesService
     */
    private void loginStep2(CookiesService cookiesService){
        CookiesDO cookiesDo = LoginUtil.loginStep2(256868, "13862c4ac2cf0e9610509c29168dfb41");
        cookiesService.updateById(cookiesDo);
    }

    public static void test(CookiesService cookiesService) {
        CookiesDO cookiesDo = cookiesService.getById(1);
        System.out.println("cookiesDo: " + JSON.toJSONString(cookiesDo));
        List<LiveItemDO> list = DynamicUtil.liveUsers(LoginUtil.getCookies(cookiesDo));
    }

    public static List<LiveItemDO> test2(CookiesService cookiesService) {
        CookiesDO cookiesDo = cookiesService.getById(1);
        System.out.println("cookiesDo: " + JSON.toJSONString(cookiesDo));
        return DynamicUtil.uplist(LoginUtil.getCookies(cookiesDo));
    }

    public static void test3(CookiesService cookiesService) {
        CookiesDO cookiesDo = cookiesService.getById(1);
        DynamicUtil.call(LoginUtil.getCookies(cookiesDo), 401742377);
    }
}
