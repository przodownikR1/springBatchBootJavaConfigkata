package pl.java.scalatech.listener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Person;

@Component
@Slf4j
public class ReaderListener implements ItemReadListener<Person>{
    @Override
    public void beforeRead() {
        log.info("$$$R before reader");
        
    }

    @Override
    public void afterRead(Person item) {
        log.info("$$$R after reader: {}",item);
        
    }

    @Override
    public void onReadError(Exception ex) {
        log.info("$$$R on read error {}: ",ex);
        
        
    }

}
