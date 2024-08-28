package org.cherrygirl.service.impl;

import org.cherrygirl.utils.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.FansMapper;
import org.cherrygirl.domain.FansDO;
import org.cherrygirl.service.FansService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nannanness
 */
@Service
public class FansServiceImpl  extends ServiceImpl<FansMapper, FansDO> implements FansService {

    @Override
    public boolean updateFans(FansDO fansDo) {
        fansDo.setStatTime(TimeUtil.todayTostr());
        int insert = baseMapper.insert(fansDo);
        if(insert > 0){
            return true;
        }
        return false;
    }

    @Override
    public Map fansPlus(long uid, long fans, long nums) {
        Map map = new HashMap();
        int hour = LocalTime.now().getHour();
        int minute = LocalTime.now().getMinute();
        int second = LocalTime.now().getSecond();
        System.out.println("hour : " + hour);
        String time = null;
        if(hour + minute + second > 3){
            time = TimeUtil.todayTostr();
        }else {
            time = TimeUtil.yesterdayToStr();
        }
        QueryWrapper<FansDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FansDO::getUid, uid);
        queryWrapper.lambda().eq(FansDO::getStatTime, time);
        FansDO fansDo = baseMapper.selectOne(queryWrapper);
        if(fansDo != null){
            map.put("fansP", fans - fansDo.getFans());
            map.put("numsP", nums - fansDo.getNums());
        }
        return map;
    }

}
