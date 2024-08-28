package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.collections4.MapUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * qq估价
 * @author nannanness
 */
@Component
public class GetQqgjCommand implements Command {

    @Autowired
    IykManager iykManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        long qq = event.getSender().getId();
        Map map = iykManager.getQqgj(qq);
        if(MapUtils.isNotEmpty(map)){
            StringBuilder msg = new StringBuilder();
            for(Object key : map.keySet()){
                msg.append(key.toString()).append("：").append(map.get(key));
                msg.append("\n");
            }
            event.getGroup().sendMessage(msg.toString());
        }
    }
}
