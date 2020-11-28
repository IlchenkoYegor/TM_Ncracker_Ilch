package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

public class ArrayTaskList extends AbstractTaskList {
    private int size;
    private int sizeofarray;
    private Task []array;
    @Override
    public int size(){
        return size;
    }
    @Override
    // додає до арейліста
    public void add(Task task){
        if( task == null){
            throw new NullPointerException("the task can`t be null in method add\n");
        }
        if(size>=sizeofarray){
            sizeofarray= (int)(sizeofarray*1.5)+1;
            Task []arrfortask = new Task[sizeofarray];
            for(int i = 0; i<size; i++){
                arrfortask[i] = array[i];
            }
            array = arrfortask;
        }
        array[size] = task;
        size++;
    }
    @Override
    //видаляє
    public boolean remove(Task task){
        if(task == null){
            throw new NullPointerException("removing of null-pointer object\n");
        }
        boolean g = false;
        for(int i = 0; i<size; i++){
            if(array[i].equals(task)){
                g = true;
                size--;
                for(;i<size; i++){
                    array[i] = array[i+1];
                }
            }
        }
        return g;
    }
    @Override
    public Task getTask(int index){
        if(index <0 || index > size){
            throw new ArrayIndexOutOfBoundsException("method getTask negative or unexistable element of array were required\n");
        }
        return array[index];
    }

    //повертає проміжок
 /*   public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList sometasks = new ArrayTaskList();
        for (int i = 0; i<size; i++) {
            if (array[i].nextTimeAfter(from) > 0 && array[i].nextTimeAfter(from) < to) {
                sometasks.add(array[i]);
            }
        }
        return sometasks;
    }*/
}
