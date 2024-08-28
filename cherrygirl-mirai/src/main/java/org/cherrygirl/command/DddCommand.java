package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.service.ImgService;
import org.cherrygirl.manager.ImgManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 典中典
 * @author nannanness
 */
@Component
public class DddCommand implements Command {

    @Autowired
    private ImgService imgService;

    @Autowired
    private ImgManager imgManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        ImgDO ddd = imgService.getDdd();
        if(ddd != null && StringUtils.isNotEmpty(ddd.getName())){
            String img = imgManager.getImg(ddd.getName());
            File file = new File(img);
            if(file.exists()){
                System.out.println("ddd name : " + file.getName());
                ExternalResource resource = ExternalResource.create(file);
                Image image = event.getGroup().uploadImage(resource);
                resource.close();
                event.getGroup().sendMessage(Image.fromId(image.getImageId()));
            }
        }
    }
}
