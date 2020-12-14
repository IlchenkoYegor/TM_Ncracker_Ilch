package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

public class TaskListFactory {
    public static AbstractTaskList createTaskList(ListTypes.types type){
        if(type == ListTypes.types.LINKED){
            AbstractTaskList res = new LinkedTaskList();
            return res;
        }
        else if(type == ListTypes.types.ARRAY){
            AbstractTaskList res = new ArrayTaskList();
            return res;
        }
        throw new IllegalArgumentException("invalid parameter in method createTaskList");
    }
}
