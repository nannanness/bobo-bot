//package org.cherrygirl.command;
//
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author nannanness
// */
//@Component
//public class HelpCommand extends BaseCommand{
//
//    @Override
//    public int getParamSize() {
//        return 0;
//    }
//
//    @Override
//    public List<String> getCommands() {
//        List<String> commands = new ArrayList<>();
//        commands.add("/help");
//        return commands;
//    }
//
//    public String help() {
//        return "你好，以下功能可供使用：\n" +
//                "/数据\n" +
//                "/搜图 图片类型\n" +
//                "/云点歌 歌名 歌手名（可选）\n" +
//                "/来一局谁是卧底\n" +
//                "/查天气 城市名称 明天|后天|周几\n" +
//                "/看图猜成语\n" +
//                "/二次元图片\n" +
//                "/每日一图\n" +
//                "/查手机 手机名称\n" +
//                "/qq估价\n" +
//                "/搜漫画 漫画名称\n" +
//                "/挑战古诗词\n" +
////                "/萝莉图（测试功能，可能失败）\n" +
//                "/摸摸 @对方\n" +
//                "@我|波波（聊天内容）\n";
//
//    }
//
//    @Override
//    public Map<String, Object> parseCommand(String text) {
//        return null;
//    }
//}
