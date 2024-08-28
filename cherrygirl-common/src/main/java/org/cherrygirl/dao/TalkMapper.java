package org.cherrygirl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;

import java.util.List;

/**
 * @author nannanness
 */
@Mapper
public interface TalkMapper extends BaseMapper<TalkDO> {

    @Select("${sqlStr}")
    List<TalkDO> selectSql(@Param("sqlStr") String sql);
}
