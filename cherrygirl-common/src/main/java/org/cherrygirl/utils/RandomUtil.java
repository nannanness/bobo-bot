package org.cherrygirl.utils;

import java.util.Random;

/**
 * @author nannanness
 */
public class RandomUtil {

    private static final String NUMBER = "0123456789";

    public static Integer nextNumber(Integer length, Integer start, Integer end) {
        Random random = new Random();
        Integer num = null;
        boolean flag = true;
        while (flag){
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < length; i++){
                int number = random.nextInt(NUMBER.length());
                sb.append(NUMBER.charAt(number));
            }
            int i = Integer.parseInt(sb.toString());
            if(i >= start && i<= end){
                num = i;
                flag = false;
            }
        }
        return num;
    }
}
