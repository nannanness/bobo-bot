package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * /搜漫画
 * @author nannanness
 */
@Component
public class GetTxmhCommand implements Command {

    @Autowired
    IykManager iykManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(params != null && params.length > 1){
            String name = params[0];
            String url = iykManager.getTxmh(name, 0);
            if(StringUtils.isNotEmpty(url)){
                event.getGroup().sendMessage("漫画《" + name + "》\n" + url);
            }else{
                event.getGroup().sendMessage("未找到该漫画");
            }
        }
    }
}
