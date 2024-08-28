package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.SpyMapper;
import org.cherrygirl.domain.SpyDo;
import org.cherrygirl.service.SpyService;
import org.springframework.stereotype.Service;

@Service
public class SpyServiceImpl extends ServiceImpl<SpyMapper, SpyDo> implements SpyService {
}
