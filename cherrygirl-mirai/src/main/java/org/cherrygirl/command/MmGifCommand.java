package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.manager.GifManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 摸头
 * @author nannanness
 */
@Component
public class MmGifCommand implements Command{

    @Autowired
    private GifManager gifManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String qq = null;
        String url = null;
        if(params.length == 1) {
            qq = params[0];
        }
        if(StringUtils.isNotEmpty(qq)){
            url = event.getGroup().getMembers().get(Long.parseLong(qq)).getAvatarUrl();
        }else{
            qq = String.valueOf(event.getSender().getId());
            url = event.getSender().getAvatarUrl();
        }
        String source = gifManager.genSource(url, qq);
        String mmGif = gifManager.getMmGif(source, qq);
        File file = new File(mmGif);
        if(file.exists()){
            System.out.println("gif name : " + file.getName());
            ExternalResource resource = ExternalResource.create(file);
            Image image = event.getGroup().uploadImage(resource);
            resource.close();
            event.getGroup().sendMessage(Image.fromId(image.getImageId()));
        }

    }
}
