package org.cherrygirl.command.handler;

import net.mamoe.mirai.contact.MemberPermission;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.command.BilibiliVideoParseCommand;
import org.cherrygirl.command.*;
import org.cherrygirl.command.iyk.*;
import org.cherrygirl.pojo.CommandAction;
import org.cherrygirl.events.GameSpyEvent;
import org.cherrygirl.events.IykEvent;
import org.cherrygirl.utils.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nannanness
 */
@Configuration
public class GroupCommandHandler {

    @Autowired
    GetKtcCommand getKtcCommand;
    @Autowired
    GetLliCommand getLliCommand;
    @Autowired
    GetMrytCommand getMrytCommand;
    @Autowired
    GetPhoneCommand getPhoneCommand;
    @Autowired
    GetQqgjCommand getQqgjCommand;
    @Autowired
    GetTxmhCommand getTxmhCommand;
    @Autowired
    GetTzgscCommand getTzgscCommand;
    @Autowired
    GetXjjCommand getXjjCommand;
    @Autowired
    CloudMusicCommand cloudMusicCommand;
    @Autowired
    SendImageCommand sendImageCommand;
    @Autowired
    ImageGirlCommand imageGirlCommand;
    @Autowired
    SpyOperateCommand spyOperateCommand;
    @Autowired
    RepeatCommand repeatCommand;
    @Autowired
    FansCommand fansCommand;
    @Autowired
    GameSpyEvent gameSpyEvent;
    @Autowired
    IykEvent iykEvent;
    @Autowired
    MmGifCommand mmGifCommand;
    @Autowired
    TiGifCommand tiGifCommand;
    @Autowired
    FistGifCommand fistGifCommand;
    @Autowired
    BetBulletCommand betBulletCommand;
    @Autowired
    PotteryExileCommand potteryExileCommand;
    @Autowired
    TalkCommand talkCommand;
    @Autowired
    ChatWxCommand chatWxCommand;
    @Autowired
    ChatImgWxCommand chatImgWxCommand;
    @Autowired
    DddCommand dddCommand;
    @Autowired
    UploadImgCommand uploadImgCommand;
    @Autowired
    TextCommand textCommand;
    @Autowired
    SpyCommand spyCommand;
    @Autowired
    BoCoinCommand boCoinCommand;
    @Autowired
    BetBullet2Command betBullet2Command;
    @Autowired
    ViewBoCoinCommand viewBoCoinCommand;
    @Autowired
    ViewTalkTopCoinCommand viewTalkTopCoinCommand;
    @Autowired
    SignInTodayCommand signInTodayCommand;

    @Autowired
    DiceCommand diceCommand;
    @Autowired
    UploadTextCommand uploadTextCommand;
    @Autowired
    ChessCommand chessCommand;
    @Autowired
    GenshinCommand genshinCommand;
    @Autowired
    ViewTalkYearCommand viewTalkYearCommand;
    @Autowired
    CallVideoCommand callVideoCommand;
    @Autowired
    WowCommand wowCommand;
    @Autowired
    BilibiliVideoParseCommand bilibiliVideoParseCommand;

    /**
     * 展示型命令
     */
    private static final List<CommandAction> SHOW_COMMAND_LIST = new ArrayList<>();

    /**
     * 非展示命令
     */
    private static final List<CommandAction> NON_SHOW_COMMAND_LIST = new ArrayList<>();

    /**
     * 非命令型通用功能
     */
    private static final List<CommandAction> NON_COMMAND_LIST = new ArrayList<>();


    private static final List<CommandAction> ALL_COMMAND_LIST = new ArrayList<>();

    /**
     * 缓存池
     */
    private static final List<Integer> CACHE_COMMAND_LIST = new ArrayList<>();

    private static final List<String> HELP_COMMAND =  Arrays.asList("波波菜单", "波波帮助");




    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        // 展示命令
        SHOW_COMMAND_LIST.add(new CommandAction(170, Arrays.asList("签到"), signInTodayCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(75, Arrays.asList("我的波币"), viewBoCoinCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(300, Arrays.asList("AI模式"), chatWxCommand, true, false, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(350, Arrays.asList("生成图片"), chatImgWxCommand, false));

        SHOW_COMMAND_LIST.add(new CommandAction(180, Arrays.asList("聊天王"), viewTalkTopCoinCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(10, Arrays.asList("典中典"), dddCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(220, Arrays.asList("发病"), textCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(20, Arrays.asList("搜图"), sendImageCommand, false, false, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(30, Arrays.asList("云点歌"), cloudMusicCommand, false));

        SHOW_COMMAND_LIST.add(new CommandAction(40, Arrays.asList("摸摸"), mmGifCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(42, Arrays.asList("飞踢"), tiGifCommand, false, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(45, Arrays.asList("给你"), fistGifCommand, false, false, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(50, Arrays.asList("对决"), diceCommand, true));

        SHOW_COMMAND_LIST.add(new CommandAction(60, Arrays.asList("下注子弹"), betBulletCommand, true, true, true));

        SHOW_COMMAND_LIST.add(new CommandAction(70, Arrays.asList("子弹试炼"), betBullet2Command, true,  true, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(230, Arrays.asList("疯狂十连"), genshinCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(70, Arrays.asList("陶片制裁法"), potteryExileCommand, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(160, Arrays.asList("来一局谁是卧底"), spyCommand, true));

        SHOW_COMMAND_LIST.add(new CommandAction(130, Arrays.asList("上传典中典"), uploadImgCommand, true, false, true));

        SHOW_COMMAND_LIST.add(new CommandAction(210, Arrays.asList("上传小作文"), uploadTextCommand, true, false, true));
        SHOW_COMMAND_LIST.add(new CommandAction(450, Arrays.asList("wow"), wowCommand, false, false, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(250, Arrays.asList("象棋启动"), chessCommand, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(240, Arrays.asList("年度聊天报告"), viewTalkYearCommand, false));


        // 不展示命令
//        NON_SHOW_COMMAND_LIST.add(new CommandAction(120, Arrays.asList("上传词条谁是卧底"), spyOperateCommand, false));

        NON_SHOW_COMMAND_LIST.add(new CommandAction(140, Arrays.asList("波波，", "波波,", "波波 ", "波波", "@1"), talkCommand, false, false, true));
        NON_SHOW_COMMAND_LIST.add(new CommandAction(400, Arrays.asList("波波") , callVideoCommand, false, false, false));
//        NON_SHOW_COMMAND_LIST.add(new CommandAction(140, Arrays.asList("小野，", "小野,", "小野 ", "小野"), talkCommand, false));
        // 通用功能
        NON_COMMAND_LIST.add(new CommandAction(150, repeatCommand, false, false, false));

        NON_COMMAND_LIST.add(new CommandAction(200, boCoinCommand, false, false, false));
        NON_COMMAND_LIST.add(new CommandAction(500, bilibiliVideoParseCommand, false, false, false));



//        SHOW_COMMAND_LIST.add(new CommandAction(45, Arrays.asList("数据"), fansCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(50, Arrays.asList("看图猜成语"), getKtcCommand, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(60, Arrays.asList("二次元"), getLliCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(60, Arrays.asList("每日一图"), getMrytCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(70, Arrays.asList("查手机"), getPhoneCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(80, Arrays.asList("qq估价"), getQqgjCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(90, Arrays.asList("搜漫画"), getTxmhCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(100, Arrays.asList("挑战古诗词"), getTzgscCommand, true));

//        SHOW_COMMAND_LIST.add(new CommandAction(110, Arrays.asList("写真"), imageGirlCommand, false));

//        SHOW_COMMAND_LIST.add(new CommandAction(115, Arrays.asList("萝莉图"), getXjjCommand, false));


        ALL_COMMAND_LIST.addAll(SHOW_COMMAND_LIST);
        ALL_COMMAND_LIST.addAll(NON_SHOW_COMMAND_LIST);
    }

    public static void joinCache(Integer id){
        CACHE_COMMAND_LIST.add(id);
    }

    public static void clearCache(Integer id){
        CACHE_COMMAND_LIST.remove(id);
    }

    public void handler(String text, GroupMessageEvent event) throws IOException {
        if (StringUtils.isEmpty(text)) {
            return;
        }
        if (HELP_COMMAND.contains(text)) {
            isHelpCommand(event);
            return;
        }
        if(!CACHE_COMMAND_LIST.isEmpty()){
            List<CommandAction> commandList = ALL_COMMAND_LIST.stream().filter(commandAction -> CACHE_COMMAND_LIST.contains(commandAction.getId())).collect(Collectors.toList());
            runCommandList(text, event, commandList);
        }
        int triggerCommandId = isTriggerCommand(text, ALL_COMMAND_LIST);
        if (triggerCommandId != CommandAction.INVALID) {
            Optional<CommandAction> one = ALL_COMMAND_LIST.stream().filter(commandAction -> commandAction.getId() == triggerCommandId).findFirst();
            if(one.isPresent()){
                CommandAction commandAction = one.get();
                if(commandAction.isJoinCache()){
                    joinCache(triggerCommandId);
                }
                String[] paramArr = CommandParamParser.parseCommand(text, triggerCommandId, commandAction.getCommandWords());
                handler(event, commandAction, paramArr);
            }
        }

        // 通用命令
        runCommandList(text, event, NON_COMMAND_LIST);
    }

    private void runCommandList(String text, GroupMessageEvent event, List<CommandAction> commandList) {
        commandList.forEach(commandAction -> {
            try {
                String[] paramArr = CommandParamParser.parseCommand(text, commandAction.getId(), commandAction.getCommandWords());
                handler(event, commandAction, paramArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void isHelpCommand(GroupMessageEvent event) {
        MemberPermission permission = event.getGroup().getBotPermission();
        boolean botIsAdmin = permission == MemberPermission.MEMBER ? false : true;
        StringBuilder helpWordBuilder = new StringBuilder("你好，\n为防止波波被关小黑屋，部分功能暂时禁用，目前可用功能：\n");
        List<String> commandList = new ArrayList<>();

        SHOW_COMMAND_LIST.stream().filter(commandAction -> {
            boolean needAdministratorRights = commandAction.isNeedAdministratorRights();
            if (needAdministratorRights){
                return botIsAdmin;
            } else {
                return true;
            }
        }).filter(CommandAction::isDisplay).forEach(commandAction -> commandList.addAll(commandAction.getCommandWords()));
        commandList.forEach(word -> helpWordBuilder.append(word).append("\n"));
        String help = helpWordBuilder.toString();
        event.getGroup().sendMessage(new At(event.getSender().getId()).plus(help));
    }

    private void handler(GroupMessageEvent event, CommandAction commandAction, String[] paramArr) throws IOException {
        Command command = commandAction.getCommand();
        command.run(event, commandAction.getId() ,paramArr);
    }

    private int isTriggerCommand(String text, List<CommandAction> commandList) {
        for (CommandAction commandAction : commandList) {
            List<String> commandWords = commandAction.getCommandWords();
            if (CollectionUtils.isNotEmpty(commandWords)) {
                for (String word : commandWords) {
                    if(commandAction.getId() == 130 || commandAction.getId() == 210){
                        if(text.startsWith(word) || text.endsWith(word)){
                            return commandAction.getId();
                        }
                    }else if(commandAction.getId() == 140){
                        if(text.startsWith(word)){
                            String replace = text.replace(word, "");
                            boolean notEmpty = StringUtils.isNotEmpty(replace);
                            if(notEmpty){
                                return commandAction.getId();
                            }
                        }
                    }else {
                        if (text.startsWith(word)) {
                            return commandAction.getId();
                        }
                    }
                }
            }
        }
        return CommandAction.INVALID;
    }
}
