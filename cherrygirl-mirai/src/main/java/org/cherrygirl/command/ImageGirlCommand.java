package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.cherrygirl.service.ImageGirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @author nannanness
 */
@Component
public class ImageGirlCommand implements Command{

    @Autowired(required = false)
    private ImageGirlService girlService;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String fileName = girlService.queryImageGirl();
        File file = new File(fileName);
        if(file.exists()){
            System.out.println("image name : " + file.getName());
            ExternalResource resource = ExternalResource.create(file);
            Image image = event.getGroup().uploadImage(resource);
            resource.close();
            FlashImage flashImage = FlashImage.from(image);
            event.getGroup().sendMessage(flashImage);
        }else{
            event.getGroup().sendMessage("图片未找到哦");
        }
    }
}
