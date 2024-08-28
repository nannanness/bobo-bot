package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.CoinMapper;
import org.cherrygirl.dao.WorksInfoMapper;
import org.cherrygirl.domain.CoinDO;
import org.cherrygirl.domain.WorksInfoDO;
import org.cherrygirl.service.CoinService;
import org.cherrygirl.service.WorksInfoService;
import org.springframework.stereotype.Service;

@Service
public class WorksInfoServiceImpl extends ServiceImpl<WorksInfoMapper, WorksInfoDO> implements WorksInfoService {
}
