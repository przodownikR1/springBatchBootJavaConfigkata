package pl.java.scalatech.listener;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Person;
@Slf4j
@Component
public class WriterListener implements  ItemWriteListener<Person>{

    @Override
    public void beforeWrite(List<? extends Person> items) {
        log.info("$$$W before write: {}", items.size());
        
    }

    @Override
    public void afterWrite(List<? extends Person> items) {
        log.info("$$$W after write: {}", items.size());
        
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Person> items) {
        log.info("$$$W on write error: {} ", items.size());
        
    }

}
