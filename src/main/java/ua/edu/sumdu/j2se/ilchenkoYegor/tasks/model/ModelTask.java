package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.Notificator.EventManager;
import ua.edu.sumdu.j2se.ilchenkoYegor.tasks.view.ErrorDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;


public class ModelTask {

    //private Timer specialTimer;
    private static final Logger modelTaskLog = LogManager.getLogger(ModelTask.class.getName());
    private AbstractTaskList currTasks;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private EventManager event;
    private final String PATHNAME = "./src/main/java/ua/edu/sumdu/j2se/ilchenkoYegor/tasks/bin/savedtasks.txt";
    private TimerTask activeResponse =  new TimerTask(){
        @Override
        public void run() {
            event.notify(getCalendarFromCurrentTime());
        }
    };
    private void standartCheckTasksTimerOn(){
        Timer specialTimer = new Timer();
        Calendar cldr = Calendar.getInstance();
        cldr.set(LocalDateTime.now().getYear(),LocalDateTime.now().getMonthValue()-1,LocalDateTime.now().getDayOfMonth(),
                LocalDateTime.now().getHour(), LocalDateTime.now().getMinute(),LocalDateTime.now().plusSeconds(4).getSecond());
        specialTimer.schedule(activeResponse, cldr.getTime(), 60000);
        //1800000
    }
    public ModelTask(){
        start();
        event = new EventManager(this);
        standartCheckTasksTimerOn();
    }

    public void setStartTime(LocalDateTime start){
        startTime = start;
    }
    public void setEndTime(LocalDateTime end){
        endTime = end;
    }
    /**
     * while creating

     */
    private void start(){
        if(currTasks == null){
            currTasks = TaskListFactory.createTaskList(ListTypes.types.ARRAY);
        }
        File inputTasks  = new File(PATHNAME);

        try {
            TaskIO.readBinary(currTasks, inputTasks);
        }
        catch (FileNotFoundException e){
            // add logger
            ErrorDialog err = new ErrorDialog(e);
            err.pack();
            err.setVisible(true);
            System.exit(0);
            modelTaskLog.error("Try to open unexistable file", e);
        }

    }
    public boolean isIncomingValid(){
        return startTime != null
                && endTime !=null;
    }
    private void clearFile(File file){
            try {
                FileWriter fstream1 = new FileWriter(file, false);
                fstream1.write("");
                fstream1.close();
            } catch (Exception e)
            {

               modelTaskLog.error("Error in file cleaning: ", e);
            }
    }

    public void end(){
        File outputTasks  = new File(PATHNAME);
        clearFile(outputTasks);
        try {
            TaskIO.writeBinary(currTasks, outputTasks);
            event.notify(getCalendarFromCurrentTime());
        }
        catch (FileNotFoundException e){
            ErrorDialog err = new ErrorDialog(e);
            err.pack();
            err.setVisible(true);
            System.exit(0);
            modelTaskLog.error("Try to find file in end(): ", e);
            // add logger
        }
    }
    public Iterator getSuitIterator(){
        Iterator it;
        if(isIncomingValid()) {
            it = getIncomingTasks().iterator();
        }
        else{
            it = getAllTasksArr().iterator();
        }
        return it;
    }
    public void setActiveAfterTime(LocalDateTime time){
        Iterator<Task> it = currTasks.iterator();
        while(it.hasNext()){
            Task a = it.next();
            if(a.getRepeatInterval() == 0 || a.getEndTime().isBefore(time)){
                a.setActive(false);
            }
        }
    }
    public Iterable getAllTasksArr(){
        return currTasks;
    }
    public Iterable getIncomingTasks(){
        //System.out.println(list + " " + startTime  + " " + endTime);
        return Tasks.incoming(currTasks, startTime, endTime);
    }
    public void adderOfTask(Task taskToAdd){
        currTasks.add(taskToAdd);
    }
    public void deleterOfTask(Task taskToDelete){
        currTasks.remove(taskToDelete);
    }
    public SortedMap<LocalDateTime, Set<Task>> getCalendarFromCurrentTime(){
        return Tasks.calendar(currTasks, LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
    }

}
