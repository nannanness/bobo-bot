package org.cherrygirl.command;

import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.io.IOException;

/**
 * @author nannanness
 */
public interface FriendCommand {


    /**
     * 命令处理
     *
     * @param event 事件
     * @param message 参数
     */
    void runFriend(FriendMessageEvent event, long qq, String message) throws IOException;

}
