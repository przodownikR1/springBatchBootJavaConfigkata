package pl.java.scalatech.tasklet;

import static org.springframework.batch.repeat.RepeatStatus.FINISHED;
import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class HelloTasklet implements Tasklet {
    

    public RepeatStatus execute(final StepContribution sc, final ChunkContext context) throws Exception {
        log.info("First simple task ..... execute !!! ");
        log.info("+++  StepContext :  jobParameters :  {} , stepExecution : {} , stepName :  {} ",context.getStepContext().getJobParameters(),context.getStepContext().getStepExecution(),context.getStepContext().getStepName());
        ExecutionContext jobExecutionContext = context.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        JobParameters jobParams = context.getStepContext().getStepExecution().getJobExecution().getJobParameters();
        log.info("time : {}",jobParams.getDate("time"));
        log.info("test : {}",jobParams.getString("test"));
        return FINISHED;
    }
}