package ru.fil.packing2d.tests.generator;

import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SquaresGenerator extends RectangleGenerator {

    /**
     * Генерирует прямоугольники преимущественно квадратной формы:
     * <ul>
     *     <li>Высота прямоугольника может быть случайной: от единицы до ширины контейнера</li>
     *     <li>Длина прямоугольника может быть либо равной высоте, либо с погрешнотью (+-1) от его ширины</li>
     * </ul>
     * @return список {@link List} из сгенерированных прямоугольников {@link Rectangle}
     */
    @Override
    public List<Rectangle> generateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < rectanglesNum; ++i) {
            int randWidth = randomGenerator.nextInt(containerWidth) + 1;  // от 1 до containerWidth
            int randDiff = randomGenerator.nextInt(3) - 1;         // от -1 до 1
            int randHeight = (randWidth + randDiff > containerWidth || randWidth + randDiff <= 0) ?
                    randWidth : randWidth + randDiff;
            rectangles.add(new Rectangle(randWidth, randHeight));
        }
        return rectangles;
    }
}
