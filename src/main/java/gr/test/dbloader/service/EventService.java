package gr.test.dbloader.service;

import gr.test.dbloader.dao.ClickHouseDao;
import gr.test.dbloader.dao.ClickHouseJdbcDao;
import gr.test.dbloader.model.ContactEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
public class EventService {

    @Autowired
    ClickHouseDao clickHouseDao;

    ClickHouseJdbcDao clickHouseJdbcDao = new ClickHouseJdbcDao();


    public void add(int val) {
        while (true) {
            List<ContactEvent> contactEvents = new ArrayList<>(val);
            IntStream.range(0, val).forEach(i -> contactEvents.add(ContactEvent.build()));
            clickHouseDao.save(contactEvents);
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        LongStream.range(0, val).forEach(i -> clickHouseJdbcDao.save(ContactEvent.build()));
//        clickHouseJdbcDao.save(ContactEvent.build());
//        ContactEvent event = new ContactEvent(name);
//        eventRepository.save(event);
    }


    public List<ContactEvent> getAll() {
        clickHouseJdbcDao.getAll();
        return null;
//        return eventRepository.findAll();
    }

}
