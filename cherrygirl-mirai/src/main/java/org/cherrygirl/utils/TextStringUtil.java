package org.cherrygirl.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextStringUtil {

    public static List<String> matchHanZi(String str){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            list.add(group);
            System.out.print("matchHanZi: " + group);
        }
        return list;
    }

    public static List<String> matchQQ(String str){
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("qq=[\\d]+");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            String qq = StringUtils.replace(group, "qq=", "");
            list.add(qq);
            System.out.println("matchQQ: " + group);
            System.out.println("qq: " + qq);
        }
        return list;
    }
}
