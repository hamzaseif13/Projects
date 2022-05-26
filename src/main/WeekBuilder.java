package main;

import java.time.LocalTime;

public class WeekBuilder {
    private boolean sun=false;
    private boolean mon=false;
    private boolean tue=false;
    private boolean wed=false;
    private boolean thu=false;
    private boolean fri=false;
    private boolean sat=false;
    private LocalTime startTime=null;
    private LocalTime endTime=null;

    public WeekBuilder(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WeekBuilder setSun(boolean bool){
        this.sun=bool;
        return this;
    }
    public WeekBuilder setMon(boolean bool){
        this.mon=bool;
        return this;
    }
    public WeekBuilder setTue(boolean bool){
        this.tue=bool;
        return this;
    }
    public WeekBuilder setWed(boolean bool){
        this.wed=bool;
        return this;
    }
    public WeekBuilder setThu(boolean bool){
        this.thu=bool;
        return this;
    }
    public WeekBuilder setFri(boolean bool){
        this.fri=bool;
        return this;
    }
    public WeekBuilder setSat(boolean bool){
        this.sat=bool;
        return this;
    }
    public WeekBuilder setStartTime(LocalTime time){
        this.startTime=time;
        return this;
    }
    public WeekBuilder setEndTime(LocalTime time){
        this.endTime=time;
        return this;
    }
    public Week build(){
        return new Week(sun,mon,tue,wed,thu,fri,sat,startTime,endTime);
    }
}
