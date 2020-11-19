package com.soft1851.files.resource;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName FileResource.java
 * @Description TODO
 * @createTime 2020年11月19日 14:48:00
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 文件资源类
 */
@Component
@PropertySource("classpath:file-${spring.profiles.active}.properties")
@ConfigurationProperties(prefix = "file")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResource {
    private String host;
    private String endpoint;
    private String bucketName;
    private String objectName;
    private String ossHost;
}
