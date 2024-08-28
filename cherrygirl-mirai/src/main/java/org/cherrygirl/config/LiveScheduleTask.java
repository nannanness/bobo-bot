package org.cherrygirl.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.constant.PictureUrl;
import org.cherrygirl.domain.LiveItemDO;
import org.cherrygirl.utils.DynamicUtil;
import org.cherrygirl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.net.HttpCookie;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author nannanness
 */
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class LiveScheduleTask {

    private Bot bot;
    private ResourcesConfig resourcesConfig;
    private final Map<Long, String> liveRecord = new HashMap<>();
    private List<HttpCookie> cookies;
    private long masterGroup = 723474785;
    private String getRoot(){
        return resourcesConfig.getRoot() + "/image/live/";
    }

    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：10秒
//    @Scheduled(fixedRate=10000)
    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        if(cookies == null){
            System.out.println("cookies null");
        }else{
            List<LiveItemDO> liveList = DynamicUtil.liveUsers(cookies);
            if(liveList != null && liveList.size() > 0){
                for (LiveItemDO itemDo : liveList){
//                    if(itemDo.getUid() == 50329118){
                        // 英雄联盟赛事
                        if(liveRecord.containsKey(itemDo.getUid())){
                            String liveTitle = liveRecord.get(itemDo.getUid());
                            if(liveTitle.equals(itemDo.getTitle())){
                                continue;
                            }
                        }
                        MessageChainBuilder msg = new MessageChainBuilder();
                        msg.append(itemDo.getUname());
                        msg.append("正在直播!\n");
                        msg.append("“");
                        msg.append(itemDo.getTitle());
                        msg.append("”\n");
                        // 封面
                        String filePath = getRoot() + UUID.randomUUID().toString() + ".jpg";
                        HttpUtil.downloadFile(itemDo.getFace(), FileUtil.file(filePath));
                        File file = new File(filePath);
                        if(file.exists()){
                            ExternalResource resource = ExternalResource.create(file);
                            Image image = bot.getGroup(masterGroup).uploadImage(resource);
                            msg.append(image);
                        }
                        msg.append("直播地址：");
                        msg.append(itemDo.getLink());

                        bot.getGroup(masterGroup).sendMessage(msg.build());

//                    }
                    liveRecord.put(itemDo.getUid(), itemDo.getTitle());
                }
                liveList.clear();
            }
            List<LiveItemDO> uplist = DynamicUtil.uplist(cookies);
            if(uplist != null && uplist.size() > 0){
                for (LiveItemDO itemDo : uplist){
                    if(itemDo.getUid() == 390008456){
                        continue;
                    }
                    String[] call = DynamicUtil.call(cookies, itemDo.getUid());
                    MessageChainBuilder msg = new MessageChainBuilder();
                    msg.append(itemDo.getUname());
                    msg.append("有新动态\n");
                    String filePath = getRoot() + UUID.randomUUID().toString() + ".jpg";
                    HttpUtil.downloadFile(call[1], FileUtil.file(filePath));
                    File file = new File(filePath);
                    if(file.exists()){
                        ExternalResource resource = ExternalResource.create(file);
                        Image image = bot.getGroup(masterGroup).uploadImage(resource);
                        msg.append(image);
                    }
                    msg.append("动态地址：" + call[0]);
                    HttpRequest.get(call[0]).cookie(cookies).execute();
                    bot.getGroup(masterGroup).sendMessage(msg.build());
                }
                uplist.clear();
            }
        }


    }


    public void setResourcesConfig(ResourcesConfig resourcesConfig) {
        this.resourcesConfig = resourcesConfig;
    }

    public void setCookies(List<HttpCookie> cookies) {
        this.cookies = cookies;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }
}