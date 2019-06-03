package com.tools;

import java.util.Date;

/**
 * Created by simon on 6/3/2019.
 */
public class IntervalHistory {
    private String name;
    private Date start;
    private Date end;

    public IntervalHistory() {
    }

    public IntervalHistory(String name, Date start, Date end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "IntervalHistory{" + "name=" + name + ", start=" + start + ", end=" + end + '}';
    }
}
