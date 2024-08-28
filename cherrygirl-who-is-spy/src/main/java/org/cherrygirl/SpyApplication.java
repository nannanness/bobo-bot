package org.cherrygirl;


import com.alibaba.fastjson.JSON;
import org.cherrygirl.pojo.Vote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpyApplication {
    public static void main(String[] args) {
        List<Vote> list = new ArrayList<>();
        list.add(new Vote(21, 9));
        list.add(new Vote(25, 0));
        list.add(new Vote(22, 8));
        list.add(new Vote(19, 6));
        list.add(new Vote(32, 6));
        list.add(new Vote(29, 9));
        list.add(new Vote(21, 6));

        // 对象根据年龄属性升序排序
        list.sort(Comparator.comparingInt(Vote::getVote));
        Collections.reverse(list);
        list.forEach(e -> System.out.println(JSON.toJSONString(e)));
    }
}
