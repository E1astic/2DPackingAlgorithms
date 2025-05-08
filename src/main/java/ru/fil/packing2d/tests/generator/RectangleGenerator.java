package ru.fil.packing2d.tests.generator;

import ru.fil.packing2d.models.Rectangle;

import java.util.List;
import java.util.Random;

/**
 * Абстрактный класс - генератор случайных прямоугольников.
 * Каждому классу наследнику предоставляет реализацию метода {@code generateRectangles()}
 * для генерации прямоугольников по определенным правилам.
 */
public abstract class RectangleGenerator {

    protected int containerWidth;

    protected int rectanglesNum;

    protected final Random randomGenerator;

    public int getContainerWidth() {
        return containerWidth;
    }

    public void setContainerWidth(int containerWidth) {
        this.containerWidth = containerWidth;
    }

    public int getRectanglesNum() {
        return rectanglesNum;
    }

    public void setRectanglesNum(int rectanglesNum) {
        this.rectanglesNum = rectanglesNum;
    }

    public RectangleGenerator() {
        this.randomGenerator = new Random();
    }

    public RectangleGenerator(int containerWidth, int rectanglesNum) {
        this.containerWidth = containerWidth;
        this.rectanglesNum = rectanglesNum;
        this.randomGenerator = new Random();
    }

    public abstract List<Rectangle> generateRectangles();
}
