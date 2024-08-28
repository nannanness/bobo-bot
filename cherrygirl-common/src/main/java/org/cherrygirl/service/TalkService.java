package org.cherrygirl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.TalkDO;

import java.util.List;

/**
 * @author nannanness
 */
public interface TalkService extends IService<TalkDO> {

    List<TalkDO> selectYearTop(Long group, String year);
}
