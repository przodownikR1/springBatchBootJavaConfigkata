package pl.java.scalatech.job;

import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import pl.java.scalatech.domain.Person;

@Configuration
@Profile("flat")
@Slf4j
public class File2DbJob {

    @Autowired
    private ItemWriteListener<Person> itemWriterListener;
    @Autowired
    private ItemReadListener<Person> itemReaderListener;
    
    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JobExecutionListener jobLoggerListener;

    @Bean(name="flatJob")
    public Job fileToDbJob() {
        return jobBuilders.get("file2dbJob").listener(jobLoggerListener).start(stepFlat()).build();
    }

    @Bean
    public Step stepFlat() {
        return stepBuilders.get("stepFlat").<Person, Person> chunk(2).reader(flatReader()).processor(processor()).writer(dbWriter()).
                listener(jobLoggerListener)
                .listener(itemWriterListener)
                .listener(itemReaderListener)
                .build();
    }

    @Bean
    public FlatFileItemReader<Person> flatReader() {
        FlatFileItemReader<Person> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setResource(new ClassPathResource("sample-data.csv"));
        return itemReader;
    }

    @Bean
    public LineMapper<Person> lineMapper() {
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "login", "age" });
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return item ->
        {
            final String firstName = item.getLogin().toUpperCase();
            String passwd = Hashing.md5().hashString("passwd_" + firstName, Charsets.UTF_8).toString();
            final Person transformed = new Person(firstName, passwd, item.getAge());
            log.info("flat transform (" + item + ") -> (" + transformed + ")");
            return transformed;
        };
    }

    @Bean
    public ItemWriter<Person> dbWriter() {
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO person (login, passwd, age) VALUES (:login,:passwd,:age)");
        writer.setDataSource(dataSource);
        return writer;
    }
   
}