package org.cherrygirl.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author nannanness
 */
//@Configuration
//@EnableScheduling
public class TalkScheduleTask {

    private TimedCache<String, Integer> timedCache;

    /**
     * 每天6点执行
     */
//    @Scheduled(cron = "0 0 6 * * ?")
    private void createCache() {
        timedCache = CacheUtil.newTimedCache(24 * 60 * 58 * 1000);
    }

    /**
     * 每天5:59点执行
     */
//    @Scheduled(cron = "0 59 5 * * ?")
    private void clearCache() {
        timedCache.clear();
    }

    public void setTimedCache(TimedCache<String, Integer> timedCache) {
        this.timedCache = timedCache;
    }
}