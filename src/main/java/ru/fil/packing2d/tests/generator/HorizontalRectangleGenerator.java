package ru.fil.packing2d.tests.generator;

import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorizontalRectangleGenerator extends RectangleGenerator {

    /**
     * Генерирует горизонтальные прямоугольники:
     * <ul>
     *     <li>Высота прямоугольника может быть случайной: от единицы до половины ширины контейнера</li>
     *     <li>Длина прямоугольника может быть случайной: от удвоенной высоты прямоугольника до ширины контейнера</li>
     * </ul>
     * @return список {@link List} из сгенерированных прямоугольников {@link Rectangle}
     */
    @Override
    public List<Rectangle> generateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < rectanglesNum; ++i) {
            int randHeight = randomGenerator.nextInt(containerWidth / 2) + 1;  // от 1 до containerWidth / 2
            int lowerLimit = randHeight * 2;
            int randWidth = randomGenerator.nextInt(containerWidth - lowerLimit + 1) + lowerLimit;
            rectangles.add(new Rectangle(randWidth, randHeight));
        }
        return rectangles;
    }

}
