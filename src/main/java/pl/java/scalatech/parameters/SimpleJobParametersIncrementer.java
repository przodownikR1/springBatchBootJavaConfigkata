package pl.java.scalatech.parameters;

import java.time.LocalTime;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class SimpleJobParametersIncrementer implements JobParametersIncrementer {
	
	@Override
	public JobParameters getNext(JobParameters parameters) {				
		final JobParametersBuilder jobParametersBuilder = new JobParametersBuilder(parameters);
		jobParametersBuilder.addLong("time", LocalTime.now().toNanoOfDay());
		return jobParametersBuilder.toJobParameters();
	}

}