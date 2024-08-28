package org.cherrygirl.command.iyk;


import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * 看图猜成语
 * @author nannanness
 */
@Component
public class GetKtcCommand implements Command {

    @Autowired
    IykManager iykManager;

    private String ktcWord;
    private boolean flag;
    private long time;
    private long uploadGroup;

    public void doing(long time, long uploadGroup){
        this.flag = true;
        this.time = time;
        this.uploadGroup = uploadGroup;
    }

    public void finish(int id){
        this.flag = false;
        this.time = 0;
        this.uploadGroup = 0;
        this.ktcWord = null;
        GroupCommandHandler.clearCache(id);
    }


    @Override
    public void run(GroupMessageEvent event, int id, String...params) throws IOException {
        if(!flag){
            showQuestion(event);
        } else if(time < System.currentTimeMillis()){
            finish(id);
        } else if(flag && uploadGroup == event.getGroup().getId()){
            String message = event.getMessage().contentToString();
            if("结束答题".equals(message)){
                finish(id);
            } else {
                System.out.println("成语为： " + ktcWord);
                System.out.println("回答为： " + message);
                if(StringUtils.isNotEmpty(ktcWord) && ktcWord.equals(message)){
                    event.getGroup().sendMessage(new At(event.getSender().getId()).plus("猜对了！\n答案是：" + ktcWord));
                    finish(id);
                }
            }
        }
    }

    private void showQuestion(GroupMessageEvent event) throws IOException {
        doing(System.currentTimeMillis() + 1000 * 30, event.getGroup().getId());
        Map ktc = iykManager.getKtc();
        if(MapUtils.isNotEmpty(ktc)){
            String filePath = ktc.get("filePath").toString();
            File file = new File(filePath);
            if(file.exists()){
                System.out.println("image name : " + file.getName());
                ExternalResource resource = ExternalResource.create(file);
                Image image = event.getGroup().uploadImage(resource);
                resource.close();
                event.getGroup().sendMessage(new PlainText("猜猜下图是什么成语呢？\n").plus(Image.fromId(image.getImageId())));
                ktcWord = ktc.get("key").toString();
            }
        }
    }
}
