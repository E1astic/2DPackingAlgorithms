package ru.fil.packing2d.models;

public class Rectangle implements Comparable<Rectangle> {

    private int length;
    private int height;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle(int length, int height) {
        this.length = length;
        this.height = height;
    }

    public Rectangle copy() {
        return new Rectangle(this.length, this.height);
    }

    @Override
    public int compareTo(Rectangle o) {
        return Integer.compare(o.height, this.height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rectangle rectangle = (Rectangle) o;
        return length == rectangle.length && height == rectangle.height;
    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "Rectangle {" +
                "length=" + length +
                ", height=" + height +
                '}';
    }

}
