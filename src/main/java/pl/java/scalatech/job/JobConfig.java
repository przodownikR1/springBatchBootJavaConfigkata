package pl.java.scalatech.job;

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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;
    
    
    @Bean
    @StepScope
    public ItemReader<String> firstReader(@Value("#{jobParameters[message]}") String text) {
        log.info("+++ r-r-r  {} ", text);
      return new ListItemReader<>(Lists.newArrayList(text));
    }
    
    @Bean
    @StepScope
    public ItemWriter<String> firstWriter(@Value("#{jobParameters[message]}") String text) {
      log.info("+++ w-w-w  {} ", text);
      return value -> log.info("w-w-w:  {}",value);
    }
    
    @Bean(name="first")
    public Step firstStep(ItemReader<String> firstReader, ItemWriter<String> firstWriter) throws Exception {
      return stepBuilders.get("firstStep").<String, String> chunk(0).reader(firstReader).writer(firstWriter).build();
    }

    @Bean(name="firstJob")
    public Job firstJob(JobBuilderFactory jobs,ItemReader<String> firstReader, ItemWriter<String> firstWriter) throws Exception {
      return jobs.get("firstJob").start(firstStep(firstReader,firstWriter)).build();
    }
}
