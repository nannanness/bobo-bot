package org.cherrygirl.command;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.cherrygirl.domain.UpDO;
import org.cherrygirl.service.FansService;
import org.cherrygirl.service.UpService;
import org.cherrygirl.utils.UpInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 数据
 * @author nannanness
 */
@Component
public class FansCommand implements Command {

    @Autowired
    private FansService fansService;

    @Autowired
    UpService upService;

    @Override
    public void run(GroupMessageEvent event, int id, String... params) throws IOException {
        String builder = "";
        List<UpDO> upDOs = upService.list();
        for(int i = 0; i < upDOs.size(); i++){
            UpDO upDo = upDOs.get(i);
            long uid = upDo.getUid();
            long roomId = upDo.getRoomId();
            String name = upDo.getName();
            long fans = UpInfoUtil.getFans(uid);
            long nums = UpInfoUtil.getCaptain(roomId, uid);
            Map map = fansService.fansPlus(uid, fans, nums);
            if(MapUtils.isNotEmpty(map)){
                long fansP = (long) map.get("fansP");
                long numsP = (long) map.get("numsP");
                builder += name + "粉丝：" + fans;
                if(fansP != 0){
                    builder += "(" + fansP + ")";
                }
                builder += "\n";
                //
                builder += name + "舰长数：" + nums;
                if(numsP != 0){
                    builder += "(" + numsP + ")";
                }
                if(i < upDOs.size() -1){
                    builder += "\n";
                    builder += "\n";
                }
            }
        }
        System.out.println(builder);
        if(StringUtils.isNotEmpty(builder)){
            event.getGroup().sendMessage(builder);
        }
    }
}
