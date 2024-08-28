package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.collections4.MapUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 每日一图
 * @author nannanness
 */
@Component
public class GetMrytCommand implements Command {

    @Autowired
    IykManager iykManager;

    @Override
    public void run(GroupMessageEvent event, int id, String...params) throws IOException {
        Map mryt = iykManager.getMryt();
        if(MapUtils.isNotEmpty(mryt)){
            String filePath = mryt.get("filePath").toString();
            File file = new File(filePath);
            if(file.exists()){
                System.out.println("image name : " + file.getName());
                ExternalResource resource = ExternalResource.create(file);
                Image image = event.getGroup().uploadImage(resource);
                resource.close();
                event.getGroup().sendMessage(new PlainText(mryt.get("title").toString() + "\n").plus(Image.fromId(image.getImageId())));
            }
        }
    }
}
