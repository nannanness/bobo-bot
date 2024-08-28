package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.CoinMapper;
import org.cherrygirl.dao.ImgMapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.ImgDO;
import org.cherrygirl.service.CoinService;
import org.cherrygirl.service.ImgService;
import org.springframework.stereotype.Service;

/**
 * @author nannanness
 */
@Service
public class CoinServiceImpl extends ServiceImpl<CoinMapper, CoinDO> implements CoinService {
}
