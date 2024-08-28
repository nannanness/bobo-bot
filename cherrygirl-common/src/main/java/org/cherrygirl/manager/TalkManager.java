package org.cherrygirl.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.service.TalkService;
import org.cherrygirl.utils.CalenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author nannanness
 */
@Service
public class TalkManager {

    @Autowired
    private TalkService talkService;

    public boolean addOneTalk(Long group, Long qq) throws ParseException {
        QueryWrapper<TalkDO> qw = new QueryWrapper<>();
        qw.lambda().eq(TalkDO::getQq, qq);
        qw.lambda().eq(TalkDO::getGroupId, group);
        qw.lambda().eq(TalkDO::getLoadDate, CalenderUtil.getTodaySqlTime());
        List<TalkDO> talkDOS = talkService.getBaseMapper().selectList(qw);
        TalkDO talkDO = new TalkDO();
        if(talkDOS.isEmpty()){
            talkDO.setQq(qq);
            talkDO.setGroupId(group);
            talkDO.setTalkCount(1L);
            talkDO.setLoadDate(CalenderUtil.getTodaySqlTime());
        }else {
            talkDO = talkDOS.stream().findFirst().get();
            talkDO.setTalkCount(talkDO.getTalkCount() == null ? 1 : talkDO.getTalkCount() + 1);
        }
        return talkService.saveOrUpdate(talkDO);
    }

    public boolean signInToday(Long group, Long qq) throws ParseException {
        QueryWrapper<TalkDO> qw = new QueryWrapper<>();
        qw.lambda().eq(TalkDO::getQq, qq);
        qw.lambda().eq(TalkDO::getGroupId, group);
        qw.lambda().eq(TalkDO::getLoadDate, CalenderUtil.getTodaySqlTime());
        List<TalkDO> talkDOS = talkService.getBaseMapper().selectList(qw);
        TalkDO talkDO = new TalkDO();
        if(talkDOS.isEmpty()){
            talkDO.setQq(qq);
            talkDO.setGroupId(group);
            talkDO.setTalkCount(1L);
            talkDO.setSignInToday(1);
            talkDO.setLoadDate(CalenderUtil.getTodaySqlTime());
        }else {
            talkDO = talkDOS.stream().findFirst().get();
            talkDO.setTalkCount(talkDO.getTalkCount() == null ? 1 : talkDO.getTalkCount() + 1);
            if(talkDO.getSignInToday() == 0){
                talkDO.setSignInToday(1);
            }
        }
        return talkService.saveOrUpdate(talkDO);
    }

    public List<TalkDO> viewGroupTopTalks(Long group) throws ParseException {
        QueryWrapper<TalkDO> qw = new QueryWrapper<>();
        qw.lambda().eq(TalkDO::getGroupId, group);
        qw.lambda().eq(TalkDO::getLoadDate, CalenderUtil.getTodaySqlTime());
        qw.lambda().orderByDesc(TalkDO::getTalkCount);
        List<TalkDO> talkDOS = talkService.getBaseMapper().selectList(qw);
        return talkDOS;
    }

    public List<TalkDO> viewGroupQqYearTalks(Long group, Long qq, String year) throws ParseException {
        QueryWrapper<TalkDO> qw = new QueryWrapper<>();
        qw.lambda().eq(TalkDO::getGroupId, group);
        qw.lambda().eq(TalkDO::getQq, qq);
        qw.like("load_date", year + "%");
        qw.lambda().orderByDesc(TalkDO::getTalkCount);
        List<TalkDO> talkDOS = talkService.getBaseMapper().selectList(qw);
        return talkDOS;
    }


    public List<TalkDO> viewGroupYearTalks(Long group, Long qq, String year) throws ParseException {
        List<TalkDO> talkDOS = talkService.selectYearTop(group, year);
        talkDOS = talkDOS.stream().filter(talkDO -> talkDO.getQq().equals(qq)).collect(Collectors.toList());
        return talkDOS;
    }

    public TalkDO viewTodayTalks(Long group, Long qq) throws ParseException {
        QueryWrapper<TalkDO> qw = new QueryWrapper<>();
        qw.lambda().eq(TalkDO::getQq, qq);
        qw.lambda().eq(TalkDO::getGroupId, group);
        qw.lambda().eq(TalkDO::getLoadDate, CalenderUtil.getTodaySqlTime());
        List<TalkDO> talkDOS = talkService.getBaseMapper().selectList(qw);
        return talkDOS.stream().findFirst().orElse(null);
    }


}
