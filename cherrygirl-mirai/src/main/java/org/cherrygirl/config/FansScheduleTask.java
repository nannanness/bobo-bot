package org.cherrygirl.config;

import org.cherrygirl.domain.FansDO;
import org.cherrygirl.domain.UpDO;
import org.cherrygirl.service.FansService;
import org.cherrygirl.service.UpService;
import org.cherrygirl.utils.UpInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;


/**
 * @author nannanness
 */
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class FansScheduleTask {

    @Autowired
    FansService fansService;
    @Autowired
    UpService upService;

    //3.添加定时任务
//    @Scheduled(cron = "0 0 3 * * ?")
    private void configureTasks() {
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        List<UpDO> upDOs = upService.list();
        for(UpDO upDo : upDOs){
            long uid = upDo.getUid();
            long roomId = upDo.getRoomId();
            String name = upDo.getName();
            long fans = UpInfoUtil.getFans(uid);
            long nums = UpInfoUtil.getCaptain(roomId, uid);
            FansDO fansDo = new FansDO(uid, fans, nums);
            System.out.println("name: " + name + " 粉丝：" + fans + " 舰长数：" + nums);
            fansService.updateFans(fansDo);
        }
    }




}