package org.cherrygirl.command;

import com.baomidou.mybatisplus.extension.api.R;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.chinesechess.ChessAdaptor;
import org.cherrygirl.chinesechess.RequestInfo;
import org.cherrygirl.chinesechess.ResponseInfo;
import org.cherrygirl.chinesechess.ResponseType;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ChessCommand implements Command {

    private static ChessAdaptor chessAdaptor = new ChessAdaptor();

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String text = null;
        if(params != null && params.length > 0){
            text = params[0];
        }
//        System.out.println("象棋:" + text);
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.cmd = text;
        requestInfo.name = event.getSenderName();
        requestInfo.id = String.valueOf(event.getSender().getId());
        requestInfo.group = String.valueOf(event.getGroup());
        ResponseInfo responseInfo = chessAdaptor.cmd(requestInfo);
//        System.out.println("象棋结果：" + responseInfo.toString());
        if(responseInfo.type != ResponseType.none){
            MessageChainBuilder msg = new MessageChainBuilder();
            switch (responseInfo.type){
                case info_text :
                    String msg1 = responseInfo.msg;
                    msg.append(msg1);
                    break;
                case image:
                    File file = responseInfo.image;
                    if(file.exists()){
                        System.out.println("chess : " + file.getName());
                        ExternalResource resource = ExternalResource.create(file);
                        Image image = event.getGroup().uploadImage(resource);
                        resource.close();
                        msg.append(image);
                    }
                    break;
                case info_and_image :
                    String msg2 = responseInfo.msg;
                    msg.append(msg2);
                    File file2 = responseInfo.image;
                    if(file2.exists()){
                        System.out.println("chess : " + file2.getName());
                        ExternalResource resource2 = ExternalResource.create(file2);
                        Image image2 = event.getGroup().uploadImage(resource2);
                        resource2.close();
                        msg.append(image2);
                    }
                    break;
                case image_and_info :
                    File file3 = responseInfo.image;
                    if(file3.exists()){
                        System.out.println("chess : " + file3.getName());
                        ExternalResource resource3 = ExternalResource.create(file3);
                        Image image3 = event.getGroup().uploadImage(resource3);
                        resource3.close();
                        msg.append(image3);
                    }
                    String msg3 = responseInfo.msg;
                    msg.append(msg3);
                    break;
            }

            event.getGroup().sendMessage(msg.build());
        }
    }
}
