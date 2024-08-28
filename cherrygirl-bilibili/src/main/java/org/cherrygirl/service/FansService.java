package org.cherrygirl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.cherrygirl.domain.FansDO;

import java.util.Map;

/**
 * @author nannanness
 */
public interface FansService extends IService<FansDO> {

    boolean updateFans(FansDO fansDo);

    Map fansPlus(long uid, long fans, long nums);
}
