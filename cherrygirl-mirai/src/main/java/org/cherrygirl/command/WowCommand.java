package org.cherrygirl.command;

import cn.hutool.core.util.RandomUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.OfflineAudio;
import net.mamoe.mirai.message.data.ShortVideo;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 短视频回复
 */
@Component
public class WowCommand implements Command{

    @Autowired
    ResourcesConfig resourcesConfig;

    private long beginTime;

    private void playing(long beginTime){
        this.beginTime = beginTime;
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(beginTime == 0){
            playing(System.currentTimeMillis() + 1000 * 15);
            doin(event, id, params);
        }else if(beginTime < System.currentTimeMillis()){
            playing(System.currentTimeMillis() + 1000 * 15);
            doin(event, id, params);
        }
    }

    private void doin(GroupMessageEvent event, int id, String... params) throws IOException {
        Group group = event.getGroup();
        String path;
        int c = RandomUtil.randomInt(0, 100);
        if(c % 2 == 0){
            path = resourcesConfig.getDyRoot() + "/";
        } else {
            path = resourcesConfig.getBiliRoot() + "/";
        }
        File videoFile = FileUtil.findVideoFile(path);
        ExternalResource resource = ExternalResource.create(videoFile);
        ShortVideo shortVideo = group.uploadShortVideo(resource, resource, videoFile.getName());
        resource.close();
        group.sendMessage(shortVideo);
    }

}