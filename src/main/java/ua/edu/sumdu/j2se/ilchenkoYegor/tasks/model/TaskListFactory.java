package ua.edu.sumdu.j2se.ilchenkoYegor.tasks.model;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.Logger;

public class TaskListFactory {
    public static Logger logTasklistF = LogManager.getLogger(TaskListFactory.class.getName());
    public static AbstractTaskList createTaskList(ListTypes.types type){
        if(type == ListTypes.types.LINKED){
            AbstractTaskList res = new LinkedTaskList();
            return res;
        }
        else if(type == ListTypes.types.ARRAY){
            AbstractTaskList res = new ArrayTaskList();
            return res;
        }
        logTasklistF.error("invalid parameter in method createTaskList");
        throw new IllegalArgumentException("invalid parameter in method createTaskList");
    }
}
