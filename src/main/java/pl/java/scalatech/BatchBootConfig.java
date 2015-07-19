package pl.java.scalatech;

import java.util.Map;

import org.assertj.core.util.Maps;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BatchBootConfig implements CommandLineRunner{

    @Autowired
    private JobLauncher jobLauncher;
    
    @Autowired
    private Job firstJob; 
    
    public static void main(String[] args) throws Exception {
        SpringApplication.run(BatchBootConfig.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,JobParameter> params = Maps.newHashMap();
        params.put("message", new JobParameter("przodownik -> batch -> first -> example"));
        JobParameters jobParameters = new JobParameters(params);
        jobLauncher.run(firstJob, jobParameters);
        
    }
}
