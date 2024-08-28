package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 查手机
 * @author nannanness
 */
@Component
public class GetPhoneCommand implements Command {

    @Autowired
    IykManager iykManager;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        if(params != null && params.length > 1){
            String phoneName = params[0];
            List<Map> list = iykManager.getPhone(phoneName);
            if(CollectionUtils.isNotEmpty(list)){
                for (Map map : list){
                    StringBuilder msg = new StringBuilder();
                    Object name = map.get("手机品牌");
                    msg.append("手机品牌：");
                    msg.append(name);
                    msg.append("\n");
                    map.remove("手机品牌");
                    for(Object key : map.keySet()){
                        msg.append(key.toString());
                        msg.append("：");
                        msg.append(map.get(key).toString());
                        msg.append("\n");
                    }
                    event.getGroup().sendMessage(msg.toString());
                }
                event.getGroup().sendMessage("输入的名称型号越完整越精准哦");
            }else{
                event.getGroup().sendMessage("未找到该手机");
            }
        }
    }
}
