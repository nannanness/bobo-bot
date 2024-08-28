package org.cherrygirl.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.domain.TextDO;

/**
 * @author nannanness
 */
@Mapper
public interface TextMapper extends BaseMapper<TextDO> {
}
