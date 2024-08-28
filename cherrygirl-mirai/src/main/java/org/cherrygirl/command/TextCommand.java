package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.manager.ImgManager;
import org.cherrygirl.manager.TextManager;
import org.cherrygirl.service.ImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * 小作文
 * @author nannanness
 */
@Component
public class TextCommand implements Command {

    @Autowired
    private TextManager textManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String text = textManager.getText();
        if(StringUtils.isNotEmpty(text)){
            event.getGroup().sendMessage(text);
        }
    }
}
