package org.cherrygirl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.ImgDO;

/**
 * @author nannanness
 */
@Mapper
public interface CoinMapper extends BaseMapper<CoinDO> {
}
