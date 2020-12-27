package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;


public class TaskIO {
    public static void write(AbstractTaskList tasks, OutputStream out) throws IOException{
        DataOutput output = new DataOutputStream(out);
        Iterator<Task> it = tasks.iterator();
        output.writeLong(tasks.size());
        while(it.hasNext()){
            Task a = it.next();
            output.writeLong(a.getTitle().length());
            output.writeUTF(a.getTitle());
            output.writeBoolean(a.isActive());
            output.writeInt(a.getRepeatInterval());
            if(a.isRepeated()){
                output.writeLong(a.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                    output.writeLong(a.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
            else{
                output.writeLong(a.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
        out.flush();
    }
    public static void read(AbstractTaskList tasks, InputStream in) throws IOException{
        DataInput input = new DataInputStream(in);
        long size = input.readLong();
        for(long i = 0; i<size; i++)
        {
            Task a = new Task();
            input.readLong();
            String title = input.readUTF();
            a.setTitle(title);
            a.setActive(input.readBoolean());
            int interval = input.readInt();
            if(interval>0) {
                LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(input.readLong()), ZoneId.systemDefault());
                LocalDateTime end =  LocalDateTime.ofInstant(Instant.ofEpochMilli(input.readLong()), ZoneId.systemDefault());

                a.setTime(start, end, interval);
            }
            else{
                LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(input.readLong()),ZoneId.systemDefault());
                a.setTime(start);
            }
            tasks.add(a);
        }

    }
    public static void writeBinary(AbstractTaskList tasks, File file) throws FileNotFoundException {
        try (FileOutputStream out = new FileOutputStream(file)) {
            write(tasks, out);
        } catch (IOException e) {
            System.out.println("I/O ERROR");
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file)    throws FileNotFoundException{
        try (FileInputStream in = new FileInputStream(file)){
            read(tasks, in);
        }catch (IOException e){
            System.out.println("I/O ERROR");
        }
    }
    public static void write(AbstractTaskList tasks, Writer out) throws IOException{
        Gson gson = new Gson();
        ArrayTaskList jsonlist = new ArrayTaskList();
       Iterator<Task> it = tasks.iterator();
        while(it.hasNext()){
            jsonlist.add(it.next());
        }
        gson.toJson(jsonlist, out);
        out.flush();
    }
    public static void read(AbstractTaskList tasks, Reader in) throws IOException{
        Gson gson = new Gson();
        ArrayTaskList jsonlist;
        for (Object o : jsonlist = gson.fromJson(in, ArrayTaskList.class)) {
            tasks.add((Task)o);
        }
    }
    public static void writeText(AbstractTaskList tasks, File file) throws  FileNotFoundException, IOException {
        try (FileWriter out = new FileWriter(file)) {
            write(tasks, out);
        }catch (IOException e){
                System.out.println("I/O ERROR");
        }
    }
    public static void readText(AbstractTaskList tasks, File file) throws FileNotFoundException{
        try (FileReader in = new FileReader(file)){
            read(tasks, in);
        }catch (IOException e){
            System.out.println("I/O ERROR");
        }
    }
}
