package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.cherrygirl.events.GameSpyEvent;
import org.cherrygirl.command.handler.GameHandler;
import org.cherrygirl.enumeration.GameStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 来一局谁是卧底
 * @author nannanness
 */
@Component
public class SpyCommand implements Command{

    @Autowired
    private GameHandler gameHandler;

    @Autowired
    private GameSpyEvent gameSpyEvent;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        GameStatusEnum enumValue = GameStatusEnum.getEnumByValue(gameHandler.status());
        if (enumValue == GameStatusEnum.nonStart){
            gameSpyEvent.triggerStart();
            gameSpyEvent.setId(id);
        }
    }
}
