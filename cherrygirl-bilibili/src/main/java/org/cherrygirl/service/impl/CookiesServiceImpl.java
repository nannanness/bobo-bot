package org.cherrygirl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cherrygirl.dao.CookiesMapper;
import org.cherrygirl.domain.CookiesDO;
import org.cherrygirl.service.CookiesService;
import org.springframework.stereotype.Service;

/**
 * @author nannanness
 */
@Service
public class CookiesServiceImpl extends ServiceImpl<CookiesMapper, CookiesDO> implements CookiesService {
}
