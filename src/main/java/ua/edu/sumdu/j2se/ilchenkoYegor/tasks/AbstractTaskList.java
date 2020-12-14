package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

public abstract class AbstractTaskList implements Iterable{
    abstract public int size();
    abstract public Task getTask(int index);
    abstract public void add(Task task);
    abstract public boolean remove(Task task);
    public AbstractTaskList incoming(int from, int to){
        AbstractTaskList sometasks;
        if(this instanceof ArrayTaskList) {
            sometasks = new ArrayTaskList();
        }
        else{
            sometasks = new LinkedTaskList();
        }
        int size = size();
        for (int i = 0; i<size; i++) {
            if (getTask(i).nextTimeAfter(from) > 0 && getTask(i).nextTimeAfter(from) < to) {
                sometasks.add(getTask(i));
            }
        }
        return sometasks;
    }
}