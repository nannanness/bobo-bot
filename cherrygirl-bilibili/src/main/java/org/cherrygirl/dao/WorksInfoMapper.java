package org.cherrygirl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.WorksInfoDO;

/**
 * @author nannanness
 */
@Mapper
public interface WorksInfoMapper extends BaseMapper<WorksInfoDO> {
}
