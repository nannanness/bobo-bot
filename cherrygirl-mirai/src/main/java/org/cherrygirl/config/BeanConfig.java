package org.cherrygirl.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean配置
 */
@Configuration
public class BeanConfig {

    @Bean
    public TimedCache<String, Integer> timedCache(){
        TimedCache<String, Integer> timedCache = CacheUtil.newTimedCache(24 * 60 * 58 * 1000);;
        return timedCache;
    }

}
