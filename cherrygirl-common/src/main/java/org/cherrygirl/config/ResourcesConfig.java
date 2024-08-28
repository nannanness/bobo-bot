package org.cherrygirl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author nannanness
 */
@Data
@Configuration
public class ResourcesConfig {

    @Value("${resources.root.path}")
    private String root;

    @Value("${log.root.path}")
    private String logRoot;

    @Value("${douyin.root.path}")
    private String dyRoot;

    @Value("${bilibili.root.path}")
    private String biliRoot;

}
