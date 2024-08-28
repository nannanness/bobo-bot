package org.cherrygirl.command.iyk;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.Command;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.cherrygirl.manager.IykManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 挑战古诗词
 * @author nannanness
 */
@Component
public class GetTzgscCommand implements Command {

    @Autowired
    IykManager iykManager;

    private boolean next = false;

    private int tzgscNumber = 1000;

    private boolean flag;

    private long time;

    private long uploadGroup;

    public void doing(long time, long uploadGroup){
        this.flag = true;
        this.time = time;
        this.next = false;
        this.uploadGroup = uploadGroup;
    }

    public void finish(int id){
        this.flag = false;
        this.time = 0;
        this.next = false;
        this.uploadGroup = 0;
        GroupCommandHandler.clearCache(id);
    }

    public void nextFinish(){
        this.flag = false;
        this.time = System.currentTimeMillis() + 1000 * 8;
        this.next = true;
    }

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String message = event.getMessage().contentToString();
        if(!flag){
            if(next && "下一题".equals(message)){
                String s = iykManager.getTzgsc(tzgscNumber);
                event.getGroup().sendMessage(s + "\n答题过程中可输入”结束答题“来结束答题");
                doing(System.currentTimeMillis() + 1000 * 8, event.getGroup().getId());
            } else {
                doing(System.currentTimeMillis() + 1000 * 8, event.getGroup().getId());
                String s = iykManager.getTzgsc(tzgscNumber);
                event.getGroup().sendMessage(s);
            }
        } else if(time < System.currentTimeMillis()){
            finish(id);
        } else if(flag && uploadGroup == event.getGroup().getId()) {
            if(StringUtils.isNotEmpty(message)){
                if("结束答题".equals(message)){
                    finish(id);
                }else if(answerTzgsc(message) || "提示".equals(message)){
                    String s = iykManager.getTzgsc(message, tzgscNumber);
                    if(s.startsWith("恭喜你，回答正确")){
                        nextFinish();
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus(s));
                    }else {
                        event.getGroup().sendMessage(s);
                    }
                }
            }
        }
    }

    private boolean answerTzgsc(String message){
        List<String> list = Arrays.asList("1", "2", "3", "4");
        return list.contains(message);
    }
}
