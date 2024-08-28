package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.UpMapper;
import org.cherrygirl.domain.UpDO;
import org.cherrygirl.service.UpService;
import org.springframework.stereotype.Service;

/**
 * @author nannanness
 */
@Service
public class UpServiceImpl extends ServiceImpl<UpMapper, UpDO> implements UpService {
}
