package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Event> events;

    public Agenda() {
        events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> eventsInDay(LocalDate date) {
        List<Event> eventsInDay = new ArrayList<>();
        for (Event event : events) {
            if (event.isInDay(date)) {
                eventsInDay.add(event);
            }
        }
        return eventsInDay;
    }
}
