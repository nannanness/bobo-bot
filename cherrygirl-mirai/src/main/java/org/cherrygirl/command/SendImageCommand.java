package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 搜图
 * @author nannanness
 */
@Component
public class SendImageCommand implements Command{

    @Autowired(required = false)
    private PictureService pictureService;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String text = null;
        if(params != null && params.length > 0){
            text = params[0];
        }
        String fileName = pictureService.getChuzhanPicture(text);
        File file = new File(fileName);
        if(file.exists()){
            System.out.println("image name : " + file.getName());
            ExternalResource resource = ExternalResource.create(file);
            Image image = event.getGroup().uploadImage(resource);
            resource.close();
            event.getGroup().sendMessage(Image.fromId(image.getImageId()));
        }else{
            event.getGroup().sendMessage("图片不存在");
        }
    }
}
