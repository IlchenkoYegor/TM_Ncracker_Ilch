package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.lang.reflect.Array;
import java.math.*;
public class ArrayTaskList {
    private int size;
    private int sizeofarray;
    private Task []array;

    public int getSize(){
        return size;
    }

    public void add(Task task){

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

    public boolean remove(Task task){
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
    public int size(){
        return size;
    }
    public Task getTask(int index){
        return array[index];
    }

    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList Sometasks = new ArrayTaskList();
        for (int i = 0; i<size; i++) {
            if (array[i].nextTimeAfter(from) > 0 && array[i].nextTimeAfter(from) < to) {
                Sometasks.add(array[i]);
            }


        }
        return Sometasks;
    }
}
