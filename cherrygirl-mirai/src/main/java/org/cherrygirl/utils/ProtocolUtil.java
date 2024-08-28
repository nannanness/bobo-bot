//package org.cherrygirl.utils;
//
//import net.mamoe.mirai.utils.BotConfiguration;
//import xyz.cssxsh.mirai.tool.FixProtocolVersion;
//import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol;
//
//import java.io.FileNotFoundException;
//import java.util.Map;
//public class ProtocolUtil {
//    // 获取指定协议版本
//    public static void fetch() {
//        // 获取最新版本协议
//        FixProtocolVersion.fetch(BotConfiguration.MiraiProtocol.ANDROID_PAD, "8.9.88");
//        // 获取 8.9.63 版本协议
////        FixProtocolVersion.fetch(BotConfiguration.MiraiProtocol.ANDROID_PHONE, "8.9.63");
//    }
//
//    // 从本地文件加载协议版本
//    public static void load() {
//        FixProtocolVersion.load(MiraiProtocol.ANDROID_PAD);
//    }
//
//    // 获取协议版本信息
//    public static Map<BotConfiguration.MiraiProtocol, String> info() {
//        return FixProtocolVersion.info();
//    }
//}
