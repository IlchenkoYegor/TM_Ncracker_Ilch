package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;



public class LinkedTaskList {
    public class LNode
    {
        public Task value;
        public LNode next;
        public LNode(Task value){
            this.value = value;
        }
    }
    private LNode head;
    private int size;
    private LNode tale;

    public int getSize(){
        return size;
    }

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

    //видаляє
    public boolean remove(Task task){
        if(task == null){
            throw new NullPointerException("removing of null-pointer object\n");
        }
        LNode now = head;
        boolean g = false;
        if(head.value.equals(task)){
            head = head.next;
            size--;
            g = true;
        }
        else{
        while(now.next!=tale && !g){
            if(now.next.value.equals(task)){
                now.next = now.next.next;
                size--;
                g = true;
            }
            now = now.next;
        }
        if(tale==now.next && tale.value.equals(task)){
            g = true;
            tale = now;
            now.next = null;
            size--;
        }
        }

        return g;
    }

    public int size(){
        return size;
    }

    public Task getTask(int index){
        if(index <0 || index > size){
            throw new ArrayIndexOutOfBoundsException("method getTask negative or unexistable element of array were required\n");
        }
        int counter = 0;
        LNode now = head;
        while(counter!=index){
            now =  now.next;
            counter++;
        }
        return now.value;
    }

    //повертає проміжок
    public LinkedTaskList incoming(int from, int to) {
        LinkedTaskList sometasks = new LinkedTaskList();
        LNode now = head;
        while(now != tale.next) {
            if (now.value.nextTimeAfter(from) > 0 && now.value.nextTimeAfter(from) < to) {
                sometasks.add(now.value);
            }
            now = now.next;
        }
        return sometasks;
    }
}
