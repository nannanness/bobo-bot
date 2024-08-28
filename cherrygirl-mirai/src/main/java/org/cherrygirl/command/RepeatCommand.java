package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * @author nannanness
 */
@Component
public class RepeatCommand implements Command{


    private String text;
    private String history;

    private String repeat(String msg) {
        if(StringUtils.isEmpty(text)){
            text = msg;
            return null;
        }
        if(!text.equals(msg)){
            text = msg;
            return null;
        }
        if(!msg.equals(history)){
            int random = new Random().nextInt(99999);
            System.out.println("repeat random: " + random);
            if(random % 2 == 1){
                history = msg;
                return msg;
            }
        }
        return null;
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        MessageChain eventMessage = event.getMessage();
        String message = eventMessage.contentToString();
        PlainText plainText = (PlainText) eventMessage.stream().filter(PlainText.class::isInstance).findFirst().orElse(null);
        if(plainText == null){
            System.out.println("not text");
            return;
        }
        String msg = repeat(message);
        if(StringUtils.isNotEmpty(msg)){
            event.getGroup().sendMessage(msg);
        }
    }
}
