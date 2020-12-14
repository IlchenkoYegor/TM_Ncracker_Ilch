package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;


import java.util.Iterator;
import java.util.Objects;

public class LinkedTaskList extends AbstractTaskList implements Cloneable{
    public class LNode
    {
        public Task value;
        public LNode next;
        public LNode(Task value){
            this.value = value;
        }
    }
    private int counter = 0;
    private LNode now = null;
    private LNode head;
    private int size;
    private LNode tale;
    @Override
    // додає до арейліста
    public void add(Task task){
        if( task == null){
            throw new NullPointerException("the task can`t be null in method add\n");
        }
        if(size == 0){
            head = new LNode(task);
            tale = head;
        }
        else{
            tale.next = new LNode(task);
            tale = tale.next;
        }
        size++;
    }
    @Override
    //видаляє
    public boolean remove(Task task){
        if(task == null){
            throw new NullPointerException("removing of null-pointer object\n");
        }
        LNode nowfordelete = head;
        boolean g = false;
        if(head.value.equals(task)){
            head = head.next;
            size--;
            g = true;
        }
        else{
            while(nowfordelete.next!=tale && !g){
                if(nowfordelete.next.value.equals(task)){
                    nowfordelete.next = nowfordelete.next.next;
                    size--;
                    g = true;
                }
                nowfordelete = nowfordelete.next;
            }
            if(tale==nowfordelete.next && tale.value.equals(task)){
                g = true;
                tale = nowfordelete;
                nowfordelete.next = null;
                size--;
            }
        }

        return g;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public Task getTask(int index){

        if(index <0 || index > size){
            throw new ArrayIndexOutOfBoundsException("method getTask negative or unexistable element of array were required\n");
        }
        if(index<=counter || now==null){
            now = head;
            counter = 0;
        }
        while(counter!=index){
            now =  now.next;
            counter++;
        }
        return now.value;
    }
    public Iterator<Task> iterator(){
        Iterator<Task> it = new Iterator<Task>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public Task next() {
                return getTask(index++);
            }

            @Override
            public void remove(){
                if(index == 0){
                    throw new IllegalStateException();
                }
                LinkedTaskList.this.remove(getTask(--index));

            }
        };
        return it;
    }
    public LinkedTaskList clone() throws CloneNotSupportedException {
        LinkedTaskList cl = (LinkedTaskList)super.clone();
        cl.size-=size;
        for(int i = 0; i<size; i++) {
            cl.add(getTask(i));
        }
        return cl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        LNode noweq = head;
        LNode othereq = that.head;
        boolean flag = true;
        if(size == 0 && that.size == 0){
            return true;
        }
        while(noweq.next!=null && flag){
            if(!noweq.value.equals(othereq.value)){
                flag = false;
            }
            noweq = noweq.next;
            othereq = othereq.next;
        }
        return counter == that.counter &&
                size == that.size &&
                flag;

    }

    @Override
    public int hashCode() {
        return Objects.hash(head.value, size, tale.value);
    }

    @Override
    public String toString() {
        return "LinkedTaskList{" +
                ", head=" + head +
                ", size=" + size +
                ", tale=" + tale +
                '}';
    }
}
