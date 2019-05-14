package com.config;

/**
 * Created by simon on 5/14/2019.
 */
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import static java.util.Collections.singletonList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableMongoRepositories("com.repository")
@EnableTransactionManagement
public class MongoDBConfiguration extends AbstractMongoConfiguration {

    @Bean
    public Mongo mongo() throws Exception {
        // Production
//        return new MongoClient(singletonList(new ServerAddress("10.100.16.22", 27017)), singletonList(MongoCredential.createCredential("limetropy", "limetropy", "limetropy33".toCharArray())));
        return new MongoClient("127.0.0.1", 27017);
        //return new MongoClient("172.17.0.2", 27017);
        // Testing
        // return new MongoClient(singletonList(new ServerAddress("10.100.18.84", 27017)), singletonList(MongoCredential.createCredential("limetropy", "limetropy", "limetropy33".toCharArray())));
    }

    @Override
    protected String getDatabaseName() {
        return "surveyeco";
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    public MongoClient mongoClient() {
        return new MongoClient("127.0.0.1", 27017);
    }
}
