package main;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Week {
    private final boolean sun;
    private final boolean mon;
    private final boolean tue;
    private final boolean wed;
    private final boolean thu;
    private final boolean fri;
    private final boolean sat;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Week(boolean sun, boolean mon, boolean tue, boolean wed, boolean thu, boolean fri, boolean sat, LocalTime startTime, LocalTime endTime) {
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isSun() {
        return sun;
    }

    public boolean isMon() {
        return mon;
    }

    public boolean isTue() {
        return tue;
    }

    public boolean isWed() {
        return wed;
    }

    public boolean isThu() {
        return thu;
    }

    public boolean isFri() {
        return fri;
    }

    public boolean isSat() {
        return sat;
    }

    @Override
    public String toString() {
       String x ="Work days: ";
        if(sun)x+="Sun ";
        if(mon)x+="Mon ";
        if(tue)x+="Tue ";
        if(wed)x+="Wed ";
        if(thu)x+="Thu ";
        if(fri)x+="Fri ";
        if(sat)x+="Sat ";
        return x;
    }
}
