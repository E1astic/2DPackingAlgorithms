package ru.fil.packing2d.tests.generator;

import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VerticalRectangleGenerator extends RectangleGenerator {

    /**
     * Генерирует вертикальные прямоугольники:
     * <ul>
     *     <li>Длина прямоугольника может быть случайной: от единицы до половины ширины контейнера</li>
     *     <li>Высота прямоугольника может быть случайной: от удвоенной длины прямоугольника до ширины контейнера</li>
     * </ul>
     * @return список {@link List} из сгенерированных прямоугольников {@link Rectangle}
     */
    @Override
    public List<Rectangle> generateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < rectanglesNum; ++i) {
            int randWidth = randomGenerator.nextInt(containerWidth / 2) + 1;  // от 1 до containerWidth / 2
            int lowerLimit = randWidth * 2;
            int randHeight = randomGenerator.nextInt(containerWidth - lowerLimit + 1) + lowerLimit;
            rectangles.add(new Rectangle(randWidth, randHeight));
        }
        return rectangles;
    }
}
