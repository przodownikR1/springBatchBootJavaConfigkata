package pl.java.scalatech.config;

import java.util.Collections;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.assertj.core.util.Lists;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class JobConfig {
    @PostConstruct
    public void init() {
        log.info(":: jobConfig");
    }
    
    @Bean
    @StepScope
    public ItemReader<String> reader(@Value("#{jobParameters[message]}") String text) {
        log.info("+++ r-r-r  {} ", text);
      return new ListItemReader<>(Lists.newArrayList(text));
    }
    
    @Bean
    @StepScope
    public ItemWriter<String> writer(@Value("#{jobParameters[message]}") String text) {
      log.info("+++ w-w-w  {} ", text);
      return value -> log.info("w-w-w:  {}",value);
    }
    
    @Bean
    public Step step1(StepBuilderFactory steps, ItemReader<String> reader, ItemWriter<String> writer) throws Exception {
      return steps.get("step1").<String, String> chunk(0).reader(reader).writer(writer).build();
    }

    @Bean
    public Job firstJob(JobBuilderFactory jobs, Step step1) throws Exception {
      return jobs.get("firstJob").start(step1).build();
    }
}
