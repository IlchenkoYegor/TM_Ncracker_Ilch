package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Cloneable, Serializable {
    private String title;
    private LocalDateTime time;
    private LocalDateTime end;
    private LocalDateTime start;
    private int interval;
    private boolean isrepeated;
    private boolean activecond;
    public void setActive(boolean active){
        activecond = active;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        if(title == null){
            throw new IllegalArgumentException("The title of task is null\n");
        }
        this.title = title;
    }

    public LocalDateTime getTime(){
        if(isRepeated()){
            return start;
        }
        return time;
    }

    public void setTime(LocalDateTime time) throws NullPointerException{
        if(time == null) {
            throw new IllegalArgumentException("Negative values in method setTime(time)\n");
        }

        if(isRepeated()){
            isrepeated = false;
            this.interval = 0;

            this.start = null;
            this.end = null;
        }
        this.time = cloneTime(time);

    }

    public LocalDateTime getStartTime(){
        if(!isRepeated()){
            return time;
        }
        return start;
    }
    public LocalDateTime getEndTime(){
        if(!isRepeated()){
            return time;
        }
        return end;
    }

    public int getRepeatInterval(){
        return interval;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, int interval){
        if(start == null || end == null || interval <= 0){
            throw new IllegalArgumentException("Negative values in method setTime(Interval)\n");
        }
        this.start = cloneTime(start);
        this.end = cloneTime(end);
        this.interval = interval;
        isrepeated = true;
        this.time = null;
    }

    public LocalDateTime nextTimeAfter(LocalDateTime current){
            if(current == null){
                throw new IllegalArgumentException("the current time can`t be negative (method nextTimeafter)\n");
            }
            if(!isActive()){
                return null;
            }
            if (current.isAfter(getEndTime())||current.isEqual(getEndTime())) {
            return null;
            }
            if(!isRepeated()) {
                return time;
            }
            if(current.isBefore(getStartTime())){
                return start;
            }
            else{
                LocalDateTime thenexttime = cloneTime(start);
                while(thenexttime.isBefore(current)|| thenexttime.isEqual(current)){
                    thenexttime = thenexttime.plusSeconds(interval);
                }
                if(thenexttime.isAfter(end)){
                    return null;
                }
                return thenexttime;
            }
    }
    public Task(String title, LocalDateTime start, LocalDateTime end, int interval){
        if(end == null || start == null || interval <= 0){
            throw new IllegalArgumentException();
        }
        if(title == null){
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.start = start;
        this.interval = interval;
        this.end = end;
        isrepeated = true;
    }
    public Task(String title, LocalDateTime time){
        if(time == null){
            throw new IllegalArgumentException();
        }
        if(title == null){
            throw new IllegalArgumentException();
        }
        this.title = title;
        this.time = time;
    }
    public boolean isActive(){
        return activecond;
    }
    public boolean isRepeated(){
        return isrepeated;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return interval == task.interval &&
                activecond == task.activecond &&
                title.equals(task.title) &&
                getStartTime().equals(task.getStartTime()) &&
                getEndTime().equals(task.getEndTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, time, end, start, interval, isrepeated, activecond);
    }

    private LocalDateTime cloneTime(LocalDateTime sourse){
        LocalDateTime a = LocalDateTime.of(sourse.getYear(), sourse.getMonth(), sourse.getDayOfMonth(), sourse.getHour(), sourse.getMinute(), sourse.getSecond(), sourse.getNano());
        return a;
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        if(this.interval==0){
            Task a = new Task(this.title, this.time);
            return a;
        }
        Task a = new Task(this.title,this.getStartTime(),this.getEndTime(), this.interval);
        return a;
    }
    Task(){

    }
}
