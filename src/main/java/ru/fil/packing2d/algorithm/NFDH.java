package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.List;

/**
 * <p>Next Fit Decreasing High - один из самых простейших алгоритмов.</p>
 * <p>Все упаковываемые объекты сортируются по невозрастанию высот.
 * Упаковка происхоит по уровням. Высота каждого уровня есть высота первого элемента на этом уровне.
 * Прямоугольники размещаются последовательно слева направо. Если прямоугольник не умещается по ширине
 * на текущий уровень, он переносится на уровень выше.</p>
 */
public class NFDH implements PackingAlgorithm {

    @Override
    public int solve(Container container, List<Rectangle> rectangles) {

        rectangles.sort(Rectangle::compareTo);  // сортировка по невозрастанию высот

        int maxHeight = 0;
        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;

        for (Rectangle rectangle : rectangles) {
            if (maxHeight == 0) {  // первая итерация цикла
                maxHeight = rectangle.getHeight();
            } else if (freeWidth < rectangle.getLength()) {  // если текущий прямоугольник не поместится в ряд
                maxHeight += rectangle.getHeight();
                freeWidth = containerWidth;
            }
            freeWidth -= rectangle.getLength();
        }

        System.out.println("NFDH: " + maxHeight);
        return maxHeight;
    }
}
