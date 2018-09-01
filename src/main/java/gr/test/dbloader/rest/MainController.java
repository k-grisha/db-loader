package gr.test.dbloader.rest;

import gr.test.dbloader.model.ContactEvent;
import gr.test.dbloader.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event")
public class MainController {

    @Autowired
    private EventService eventService;

    @GetMapping(path = "/")
    public List<ContactEvent> hello() {
        return eventService.getAll();
    }

    @GetMapping("add/{name}")
    public void add(@PathVariable String name) {
        eventService.add(name);
    }
}
