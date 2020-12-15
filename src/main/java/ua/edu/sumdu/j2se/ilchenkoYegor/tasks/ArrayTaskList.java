package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.util.*;
import java.util.stream.Stream;


public class ArrayTaskList extends AbstractTaskList implements Cloneable {
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
    @Override
    public Iterator<Task> iterator(){
        Iterator<Task> it = new Iterator<Task>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                if(!hasNext()){
                    throw new IllegalArgumentException();
                }
                return array[index++];
            }

            @Override
            public void remove(){
                if(index == 0){
                    throw new IllegalStateException();
                }
                ArrayTaskList.this.remove(array[--index]);
            }
        };
        return it;
    }

    @Override
    public ArrayTaskList clone() throws CloneNotSupportedException {
        ArrayTaskList cl = (ArrayTaskList)super.clone();
        cl.size-=size;
        cl.sizeofarray-=sizeofarray;
        for(int i = 0; i<size; i++) {
            Task b = (Task)array[i].clone();
            cl.add(b);
        }
        return cl;
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "size=" + size +
                ", sizeofarray=" + sizeofarray +
                ", array=" + Arrays.toString(array) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        boolean ans = true;
        for(int i = 0; i<size; i++){
           if(!that.array[i].equals(array[i])){
               ans = false;
           }
        }
        return size == that.size &&
                sizeofarray == that.sizeofarray &&
                ans;
    }
    @Override
    protected ListTypes.types getType(){
        return ListTypes.types.ARRAY;
    }
    @Override
    public Stream<Task> getStream(){
        List<Task> list = new ArrayList();
        Iterator<Task> it = iterator();
        while(it.hasNext()){
            list.add(it.next());
        }
        return list.stream();
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size, sizeofarray);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }
}
