package com.soft1851.article.task;

import com.soft1851.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName TaskPublishArticle.java
 * @Description 定时发布文章，头部注解的作用
 * 1. 标记配置类，使得Springboot容器扫描到
 * 2. 开启定时任务
 * @createTime 2020年11月25日 14:31:00
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskPublishArticle {
    private final ArticleService articleService;
    /**
     * 添加定时任务，注明定时任务表达式，这里表达式的含义是每10秒执行一次
     * 工具站 https://qqe2.com/cron
     */
    @Scheduled(cron = "0/10 * * * * ?")
    private void publishArticle(){
        System.out.println("执行定时任务：" + LocalDateTime.now());
        //调用文章service，把当前时间应该发布的定时文章，状态改为即时
        articleService.updateAppointToPublish();
    }

}
