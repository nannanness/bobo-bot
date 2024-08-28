package org.cherrygirl.command.handler;

import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.utils.TextStringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nannanness
 */
public class CommandParamParser {

    /**
     * 解析命令参数
     *
     * @param text msg
     * @return 参数
     */
    public static String[] parseCommand(String text, int id, List<String> filters) {
        List<String> paramList = new ArrayList<>();
        if(id == 40 || id == 42 || id == 45 || id == 50){
            // 摸摸头
            if(text.contains("CQ:at")){
                List<String> matchQQ = TextStringUtil.matchQQ(text);
                if(id == 50){
                    List<String> matchHanZi = TextStringUtil.matchHanZi(text);
                    paramList.add(matchHanZi.get(0));
                    paramList.add(matchQQ.get(0));
                }else {
                    paramList.add(matchQQ.get(0));
                }
            }
            if(text.contains("@")){
                if(id == 50){
                    String s1 = text.trim().split("@")[0];
                    String s2 = text.trim().split("@")[1];
                    paramList.add(s1);
                    paramList.add(s2);
                }else {
                    String s = text.trim().split("@")[1];
                    paramList.add(s);
                }
            }
        } else if(id == 140 || id == 350){
            // 聊天
            for(String s : filters){
                if(text.startsWith(s)){
                    String replace = text.replace(s, "");
                    paramList.add(replace);
                }
            }
        } else {
            String[] words = text.split("\\s+");
            paramList.addAll(Arrays.asList(words));
            if(paramList.size() > 0){
                // 需要命令作为参数的， 签到，子弹试炼，象棋
                if(id != 70 && id != 170 && id != 250 && id != 300){
                    paramList.remove(0);
                }
            }
        }
        return paramList.toArray(new String[paramList.size()]);
    }


}
