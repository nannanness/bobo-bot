package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author nannanness
 */
public interface Command {


    /**
     * 命令处理
     *
     * @param event 事件
     * @param params 参数
     */
    void run(GroupMessageEvent event, int id, String...params) throws IOException;

}
