package com.soft1851.files;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName GridConfig.java
 * @Description TODO
 * @createTime 2020年11月22日 00:42:00
 */
@Component
public class GridConfig {
    @Value("${spring.data.mongodb.database}")
    private String mongodb;

    /**
     *
     * @param mongoClient
     * @return
     */
    @Bean
    public GridFSBucket gridFSBucket(MongoClient mongoClient) {
        System.out.println(mongodb);
        System.out.println(mongodb);
        System.out.println(mongodb);
        System.out.println(mongodb);
        System.out.println(mongodb);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(mongodb);
        return GridFSBuckets.create(mongoDatabase);
    }
}
