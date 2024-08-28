package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cherrygirl.dao.CoinMapper;
import org.cherrygirl.dao.TalkMapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;
import org.cherrygirl.service.CoinService;
import org.cherrygirl.service.TalkService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author nannanness
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, TalkDO> implements TalkService {


    @Override
    public List<TalkDO> selectYearTop(Long group, String year) {
        String sql = "SELECT t1.max1 talk_count, t1.load_date, t2.qq\n" +
                "from (\n" +
                "\tSELECT max(talk_count) max1, load_date \n" +
                "\tFROM `cherrygirl_talk` \n" +
                "\twhere load_date \n" +
                "\tlike '" + year +"%'\n" +
                "\tand group_id = \"" + group + "\"\n" +
                "\tGROUP BY load_date\n" +
                ") t1 LEFT JOIN `cherrygirl_talk` t2\n" +
                "on t1.load_date = t2.load_date\n" +
                "and t1.max1 = t2.talk_count";
        List<TalkDO> talkDOS = baseMapper.selectSql(sql);
        return talkDOS;
    }
}
