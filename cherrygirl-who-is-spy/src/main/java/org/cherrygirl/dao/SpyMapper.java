package org.cherrygirl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cherrygirl.domain.SpyDo;

import java.util.List;

/**
 * @author nannanness
 */
@Mapper
public interface SpyMapper extends BaseMapper<SpyDo> {
}
