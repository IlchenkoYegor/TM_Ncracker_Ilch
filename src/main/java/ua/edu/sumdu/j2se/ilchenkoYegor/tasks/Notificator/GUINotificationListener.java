package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.Notificator;

import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.Task;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model.ModelTask;

import java.time.LocalDateTime;
import java.util.*;

public class GUINotificationListener {
    private LinkedList<Timer> timers = new LinkedList<>();
    private ModelTask modelTask;


    GUINotificationListener(ModelTask model){
        modelTask = model;
    }
    public void updateNotificationCalendar(SortedMap<LocalDateTime, Set<Task>> events){
        {
            if(!timers.isEmpty()){
                timers.forEach(e->{ e.cancel();});
                timers.clear();
            }
            Timer timer = new Timer();
            for(Map.Entry<LocalDateTime,Set<Task>> allEvents : events.entrySet()){
                Calendar caldr = Calendar.getInstance();
                caldr.set(allEvents.getKey().getYear(),allEvents.getKey().getMonthValue()-1,allEvents.getKey().getDayOfMonth(),
                        allEvents.getKey().getHour(), allEvents.getKey().getMinute(),allEvents.getKey().getSecond());
                timer.schedule(new Notifyer(allEvents.getValue(), modelTask), caldr.getTime());
                timers.add(timer);
            }
        }
    }

}
