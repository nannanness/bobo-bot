package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.manager.FistGifManager;
import org.cherrygirl.manager.TiGifManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 踢
 * @author nannanness
 */
@Component
public class FistGifCommand implements Command{

    @Autowired
    private FistGifManager fistGifManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String qq = null;
        String ti = null;
        String qqUrl = null;
        String tiUrl = null;
        if(params.length == 1) {
            qq = params[0];
        }
        if(StringUtils.isNotEmpty(qq)){
            qqUrl = event.getGroup().getMembers().get(Long.parseLong(qq)).getAvatarUrl();
            ti = String.valueOf(event.getSender().getId());
            tiUrl = event.getSender().getAvatarUrl();
        }else{
            qq = String.valueOf(event.getBot().getId());
            qqUrl = event.getBot().getAvatarUrl();
            ti = String.valueOf(event.getSender().getId());
            tiUrl = event.getSender().getAvatarUrl();
        }
        String qqSource = fistGifManager.genSource(qqUrl, qq);
        String tiSource = fistGifManager.genSource(tiUrl, ti);
        String tGif = fistGifManager.getTiGif(tiSource, ti, qqSource, qq);
        File file = new File(tGif);
        if(file.exists()){
            System.out.println("gif name : " + file.getName());
            ExternalResource resource = ExternalResource.create(file);
            Image image = event.getGroup().uploadImage(resource);
            resource.close();
            event.getGroup().sendMessage(Image.fromId(image.getImageId()));
        }

    }
}
