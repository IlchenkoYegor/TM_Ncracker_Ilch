package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class Tasks {
    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Iterable<Task> incoming (Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        AbstractTaskList a = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        Iterator<Task> it = tasks.iterator();
        while(it.hasNext()) {
            Task b = it.next();
            if ((b.nextTimeAfter(start) != null && !b.nextTimeAfter(start).isAfter(end))) {
                a.add(b);
            }
        }
        return a;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        SortedMap<LocalDateTime, Set<Task>> mapin = new TreeMap<LocalDateTime, Set<Task>>();
        Iterable<Task> inc =  incoming(tasks, start, end);
        Iterator<Task> it = inc.iterator();
        while(it.hasNext()) {
            Task b = it.next();
            LocalDateTime curr = b.nextTimeAfter(start);
            if(b.isRepeated()) {
                while (!curr.isAfter(end)) {
                    if(!mapin.containsKey(curr)) {
                        mapin.put(curr, new HashSet<Task>());
                    }
                    curr = curr.plusSeconds(b.getRepeatInterval());
                }
            }
            else{
                if(!mapin.containsKey(curr)) {
                    mapin.put(b.getStartTime(), new HashSet<Task>());
                }
            }
        }

        for(Map.Entry<LocalDateTime, Set<Task>> entry: mapin.entrySet()) {
            Iterator<Task> it1 = inc.iterator();
            while (it1.hasNext()) {
                Task c = it1.next();
                if (c.isRepeated()) {
                    LocalDateTime curr = c.nextTimeAfter(start);
                    while (!curr.isAfter(end)) {
                        if (entry.getKey().equals(curr)) {
                            entry.getValue().add(c);
                        }
                        curr = curr.plusSeconds(c.getRepeatInterval());
                    }
                }
                else{
                    if(c.getStartTime().equals(entry.getKey())){
                        entry.getValue().add(c);
                    }
                }
            }
        }
        return mapin;
    }
}
