package org.cherrygirl.events;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.cherrygirl.command.handler.GroupCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author nannanness
 */
@Component
public class IykEvent {


    @Autowired
    GroupCommandHandler groupCommandHandler;

    public void handlerIyk(GroupMessageEvent event) throws IOException {
        String message = event.getMessage().contentToString();
        groupCommandHandler.handler(message, event);
    }


}
