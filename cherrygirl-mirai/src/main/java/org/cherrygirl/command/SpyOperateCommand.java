package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.SpyDo;
import org.cherrygirl.events.GameSpyEvent;
import org.cherrygirl.service.SpyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传词条 谁是卧底
 * @author nannanness
 */
@Component
public class SpyOperateCommand implements Command{

    @Autowired
    private SpyService spyService;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(params != null && params.length > 2) {
            String pos = params[0];
            String neg = params[1];
            if (StringUtils.isNotEmpty(pos) && StringUtils.isNotEmpty(neg)){
                spyService.save(new SpyDo(pos, neg));
                event.getGroup().sendMessage("上传词条成功！");
            }else{
                event.getGroup().sendMessage("上传词条失败！请不要传空值哦");
            }
        }

    }
}
