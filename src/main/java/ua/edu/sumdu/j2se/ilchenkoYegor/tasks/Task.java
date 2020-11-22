package ua.edu.sumdu.j2se.ilchenkoYegor.tasks;

import java.util.Scanner;

public class Task {
    private String title;
    private int time;
    private int end;
    private int start;
    private int interval;
    private boolean isrepeated;
    private boolean activecond;
    public void setActive(boolean active){
        activecond = active;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }


    public int getTime(){
        if(isRepeated()){
            return start;
        }
        return time;
    }

    public void setTime(int time){
        if(isRepeated()){
            isrepeated = false;
            this.start = 0;
            this.end = 0;
            this.interval = 0;

        }
        this.time = time;
    }

    public int getStartTime(){
        if(!isRepeated()){
            return time;
        }
        return start;
    }
    public int getEndTime(){
        if(!isRepeated()){
            return time;
        }
        return end;
    }

    public int getRepeatInterval(){
        return interval;
    }

    public void setTime(int start, int end, int interval){
        this.start = start;
        this.end = end;
        this.interval = interval;
        isrepeated = true;
        this.time = 0;
    }

    public int nextTimeAfter(int current){
            if(!isActive()){
                return -1;
            }

            if (current >= getEndTime()) {

                return -1;
            }

            if(!isRepeated()) {

                return time;
            }

            if(current<getStartTime()){

                return start;
            }
            else{
                if( (current + interval - (current - start) % interval)<=end){

                    return current + interval - (current - start) % interval;

                }


                    return -1;

            }


    }

    public Task(String title, int start, int end, int interval){
        this.title = title;
        this.start = start;
        this.interval = interval;
        this.end = end;
        isrepeated = true;

    }

    public Task(String title, int time){
        this.title = title;
        this.time = time;
    }

    public boolean isActive(){
        return activecond;
    }



    public boolean isRepeated(){
        return isrepeated;
    }



}
