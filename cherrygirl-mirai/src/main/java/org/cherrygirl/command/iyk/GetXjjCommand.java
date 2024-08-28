package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 萝莉图
 * @author nannanness
 */
@Component
public class GetXjjCommand implements Command {

    @Autowired
    IykManager iykManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String filePath = iykManager.getXjj();
        if(StringUtils.isNotEmpty(filePath)){
            File file = new File(filePath);
            if(file.exists()){
                System.out.println("image name : " + file.getName());
                ExternalResource resource = ExternalResource.create(file);
                Image image = event.getGroup().uploadImage(resource);
                resource.close();
                event.getGroup().sendMessage(Image.fromId(image.getImageId()));
            }
        }
    }
}
