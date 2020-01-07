package com.rebook.elasticsearch;

import com.rebook.elasticsearch.config.ElasticConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan("com.rebook.elasticsearch.*")
@Configuration
//@EnableAutoConfiguration(exclude = {ElasticConfig.class})
public class ElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchApplication.class, args);
	}

}
