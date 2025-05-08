package ru.fil.packing2d.tests.generator;

import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRectangleGenerator extends RectangleGenerator {

    /**
     * Генерирует случайные прямоугольники прямоугольники:
     * <ul>
     *     <li>И высота и длина прямоугольника может быть случайной: от единицы до ширины контейнера</li>
     * </ul>
     * @return список {@link List} из сгенерированных прямоугольников {@link Rectangle}
     */
    @Override
    public List<Rectangle> generateRectangles() {
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < rectanglesNum; ++i) {
            int randWidth = randomGenerator.nextInt(containerWidth) + 1;  // от 1 до containerWidth
            int randHeight = randomGenerator.nextInt(containerWidth) + 1; // от 1 до containerWidth
            rectangles.add(new Rectangle(randWidth, randHeight));
        }
        return rectangles;
    }

}
