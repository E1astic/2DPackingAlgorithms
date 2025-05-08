package ru.fil.packing2d.models;

import java.util.Objects;

public class Interval {

    private int start;
    private int end;
    private int length;

    public Interval(int start, int end, int length) {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "start=" + start +
                ", end=" + end +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return start == interval.start && end == interval.end && length == interval.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, length);
    }
}
