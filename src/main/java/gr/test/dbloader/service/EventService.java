package gr.test.dbloader.service;

import gr.test.dbloader.dao.ClickHouseJdbcDao;
import gr.test.dbloader.model.ContactEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {


    ClickHouseJdbcDao clickHouseJdbcDao = new ClickHouseJdbcDao();


    public void add(String name) {
        clickHouseJdbcDao.save(null);
//        ContactEvent event = new ContactEvent(name);
//        eventRepository.save(event);
    }


    public List<ContactEvent> getAll() {
        clickHouseJdbcDao.getAll();
        return null;
//        return eventRepository.findAll();
    }

}
