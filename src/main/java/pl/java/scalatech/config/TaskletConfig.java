package pl.java.scalatech.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import pl.java.scalatech.tasklet.HelloTasklet;
@Configuration
public class TaskletConfig {
    
    @Autowired
    private JobBuilderFactory jobs;
    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private StepBuilderFactory stepBuilders;

    
    @Bean
    public Job job(JobExecutionListener listener,Step stepTasklet) {
        return jobs.get("HelloJob").listener(listener).start(stepTasklet()).build();
    }

    @Bean
    public Step stepTasklet() {
        return stepBuilders.get("stepTasklet").tasklet(helloTasklet()).build();
    }
    

    @Bean
    public Tasklet helloTasklet() {
        return new HelloTasklet();
    }
}
