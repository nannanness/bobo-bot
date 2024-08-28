package org.cherrygirl;

import net.mamoe.mirai.Bot;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.events.GroupEvent;
import org.cherrygirl.manager.ScancodeLoginManager;
import org.cherrygirl.utils.SpringUtil;
import org.cherrygirl.core.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import top.mrxiaom.overflow.BotBuilder;

/**
 * @author nannanness
 */
@SpringBootApplication
public class Application implements ApplicationRunner {

    public static void main(String[] args) {
        try{
        new SpringApplicationBuilder()
                .sources(Application.class)
                // 指定非 web 模式
                .web(WebApplicationType.NONE)
                .run(args);
        } catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        GroupEvent groupEvent = applicationContext.getBean(GroupEvent.class);
        ResourcesConfig resourcesConfig = applicationContext.getBean(ResourcesConfig.class);
        Tool.initPath(resourcesConfig.getRoot() + "/chessImage");
//        CoreUsage.setup(resourcesConfig.getRoot() + "/txlib/8.9.88");
//        LocalFileService.register();
//        Bot bot = BotFactory.INSTANCE.newBot(1, "pw", new BotConfiguration() {{
//            setProtocol(MiraiProtocol.ANDROID_PAD );
//            fileBasedDeviceInfo();
//            setDeviceInfo(bot1 -> DeviceInfo.from(new File(resourcesConfig.getRoot() + "/json/device.json")));
//        }});
//        Bot bot = BotFactory.INSTANCE.newBot(1, BotAuthorization.byQRCode(), configuration -> {
//            // 本地设备信息
////            configuration.fileBasedDeviceInfo();
//            // 服务器端
//            configuration.setDeviceInfo(bot1 -> DeviceInfo.from(new File(resourcesConfig.getRoot() + "/json/device.json")));
////            configuration.redirectBotLogToFile(new File(resourcesConfig.getLogRoot() + "/mirai.log"));
////            configuration.redirectNetworkLogToFile(new File(resourcesConfig.getLogRoot() + "/mirai-net.log"));
//            configuration.setProtocol(BotConfiguration.MiraiProtocol.ANDROID_WATCH);
//        });
        Bot bot = BotBuilder.positive("ws://127.0.0.1:3001").connect();
        bot.login();
        bot.getEventChannel().registerListenerHost(groupEvent);

        System.out.println("====== bot启动 =====");
        ScancodeLoginManager scancodeLoginManager = applicationContext.getBean(ScancodeLoginManager.class);
        scancodeLoginManager.login();


//        定时任务
//        TimedCache<String, Integer> timedCache = applicationContext.getBean(TimedCache.class);
//        TalkScheduleTask talkScheduleTask = applicationContext.getBean(TalkScheduleTask.class);
//        talkScheduleTask.setTimedCache(timedCache);
//        LiveScheduleTask liveScheduleTask = applicationContext.getBean(LiveScheduleTask.class);
//        liveScheduleTask.setBot(bot);
//        liveScheduleTask.setCookies(LoginUtil.getCookies(cookiesDO));
//        liveScheduleTask.setResourcesConfig(resourcesConfig);
    }
}
