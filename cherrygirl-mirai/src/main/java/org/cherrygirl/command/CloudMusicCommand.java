package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
import org.cherrygirl.enumeration.CloudMusicModeEnum;
import org.cherrygirl.service.CloudMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 云点歌
 * @author nannaness
 */
@Component
public class CloudMusicCommand implements Command {

    @Autowired(required = false)
    private CloudMusicService cloudMusicService;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(params != null) {
            String name = null;
            String singer = null;
            String mode = null;
            if(params.length == 1){
                name = params[0];
                mode = CloudMusicModeEnum.NAME.getMode();
            }else if(params.length == 2){
                name = params[0];
                singer = params[1];
                mode = CloudMusicModeEnum.SINGER.getMode();
            }
            Map res = null;
            if(CloudMusicModeEnum.NAME.getMode().equals(mode)){
                res = cloudMusicService.search(name);
            }else if(CloudMusicModeEnum.SINGER.getMode().equals(mode)){
                res = cloudMusicService.search(name, singer);
            }
            if(res == null){
                event.getGroup().sendMessage("未找到该歌曲");
            }else{
                MusicShare musicShare = new MusicShare(
                        MusicKind.NeteaseCloudMusic,
                        res.get("title").toString(),
                        res.get("summary").toString(),
                        res.get("jumpUrl").toString(),
                        res.get("pictureUrl").toString(),
                        res.get("musicUrl").toString()
                );
                event.getGroup().sendMessage(musicShare);
            }
        }

    }
}
