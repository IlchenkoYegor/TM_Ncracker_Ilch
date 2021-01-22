package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.Notificator;

import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;

import java.time.LocalDateTime;
import java.util.*;

public class EventManager {
    SortedMap<LocalDateTime, Set<Task>> events;
    GUINotificationListener listener;
    public EventManager(ModelTask model){
        events= model.getCalendarFromCurrentTime();
        listener = new GUINotificationListener(model);
    }

    public void notify(SortedMap<LocalDateTime, Set<Task>> events){
        this.events = events;
        listener.updateNotificationCalendar(events);
    }
}
