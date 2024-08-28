package org.cherrygirl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.cherrygirl.command.Command;

import java.util.List;

/**
 * @author nannanness
 */
@Data
public class CommandAction {

    public static final int INVALID = 0;

    private int id;

    private List<String> commandWords;

    private int paramSize;

    private Command command;

    private boolean joinCache;

    private boolean needAdministratorRights;

    private boolean isDisplay;

    public CommandAction(int id, Command command, boolean joinCache, boolean needAdministratorRights, boolean isDisplay) {
        this.command = command;
        this.id = id;
        this.joinCache = joinCache;
        this.needAdministratorRights = needAdministratorRights;
        this.isDisplay = isDisplay;
    }

    public CommandAction(int id, List<String> commandWords, Command command, boolean joinCache, boolean needAdministratorRights, boolean isDisplay) {
        this.commandWords = commandWords;
        this.command = command;
        this.joinCache = joinCache;
        if (commandWords != null) {
            this.paramSize = commandWords.size();
        }
        this.id = id;
        this.needAdministratorRights = needAdministratorRights;
        this.isDisplay = isDisplay;
    }
}
