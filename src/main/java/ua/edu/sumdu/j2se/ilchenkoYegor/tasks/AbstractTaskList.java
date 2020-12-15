package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractTaskList implements Iterable{
    abstract public int size();
    abstract public Task getTask(int index);
    abstract public void add(Task task);
    abstract public boolean remove(Task task);
    abstract public Stream<Task> getStream();
    abstract protected ListTypes.types getType();
    protected ListTypes.types tasks = getType();
    final public AbstractTaskList incoming(int from, int to){
        AbstractTaskList sometasks = TaskListFactory.createTaskList(tasks);
        List a = getStream().filter(c->(c.nextTimeAfter(from) >0 && c.nextTimeAfter(from)<to)).collect(Collectors.toList());
        int n = a.size();
        for(int i =0; i<n;i++) {
            sometasks.add((Task)a.get(i));
        }
        return sometasks;
    }
}