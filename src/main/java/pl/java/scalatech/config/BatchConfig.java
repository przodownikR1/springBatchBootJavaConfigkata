package pl.java.scalatech.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
@ComponentScan(basePackages= {"pl.java.scalatech.listener","pl.java.scalatech.tasklet"})
public class BatchConfig {

    
  
    
}
