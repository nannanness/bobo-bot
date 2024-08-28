package org.cherrygirl.command;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.NudgeEvent;
import net.mamoe.mirai.message.data.OfflineAudio;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.config.ResourcesConfig;
import org.cherrygirl.utils.FileUtil;
import org.cherrygirl.utils.TalkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 语音回复
 */
@Component
public class CallVideoCommand implements Command{
    private long beginTime;

    private void playing(long beginTime){
        this.beginTime = beginTime;
    }
    @Autowired
    ResourcesConfig resourcesConfig;
    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(beginTime == 0){
            playing(System.currentTimeMillis() + 1000 * 45);
            doin(event, id, params);
        }else if(beginTime < System.currentTimeMillis()){
            playing(System.currentTimeMillis() + 1000 * 45);
            doin(event, id, params);
        }
    }

    public void run(Group group) throws IOException {
        if(beginTime == 0){
            playing(System.currentTimeMillis() + 1000 * 45);
            doinNudgeEvent(group);
        }else if(beginTime < System.currentTimeMillis()){
            playing(System.currentTimeMillis() + 1000 * 45);
            doinNudgeEvent(group);
        }
    }
    private void doinNudgeEvent(Group group) throws IOException {
        String videoPath = resourcesConfig.getRoot() + "/video/";
        File videoFile = FileUtil.findVideoFile(videoPath);
        ExternalResource resource = ExternalResource.create(videoFile);
        OfflineAudio offlineAudio = group.uploadAudio(resource);
        resource.close();
        group.sendMessage(offlineAudio);
    }

    private void doin(GroupMessageEvent event, int id, String... params) throws IOException {
        Group group = event.getGroup();
        String videoPath = resourcesConfig.getRoot() + "/video/";
        File videoFile = FileUtil.findVideoFile(videoPath);
        ExternalResource resource = ExternalResource.create(videoFile);
        OfflineAudio offlineAudio = group.uploadAudio(resource);
        resource.close();
        group.sendMessage(offlineAudio);
    }

}
