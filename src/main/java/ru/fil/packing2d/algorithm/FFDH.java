package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>First Fit Decreasing High - усовершенствованная версия алгоритма {@link NFDH}.</p>
 * <p>Прямоугольники также сортируются по невозрастанию высот.
 * Первым делом для каждого прямоугольника ищется свободное место среди всех уровней ниже.
 * Прямоугольник устанавливается на первое подходящее место среди уровней ниже.
 * Если такого места не нашлось, или это первый упаковываемый прямоугольник, он размещается
 * на текущий уровень.</p>
 */
public class FFDH implements PackingAlgorithm {

    @Override
    public int solve(Container container, List<Rectangle> rectangles) {

        rectangles.sort(Rectangle::compareTo);  // сортируем по невозрастанию высот

        int maxHeight = 0;
        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;
        List<Integer> remains = new ArrayList<>();  // остатки свободного расстояния на каждом уровне

        for (Rectangle rectangle : rectangles) {
            if (maxHeight == 0) {  // первая итерация цикла
                maxHeight = rectangle.getHeight();
                freeWidth -= rectangle.getLength();
            } else {
                boolean hasPlace = false;  // нашлось место среди уровней ниже
                for (int i = 0; i < remains.size(); ++i) {
                    if (remains.get(i) >= rectangle.getLength()) {
                        remains.set(i, remains.get(i) - rectangle.getLength());
                        hasPlace = true;
                        break;
                    }
                }

                if (!hasPlace && rectangle.getLength() > freeWidth) {  // если места ниже не нашлось и не уместится на текущий уровень
                    remains.add(freeWidth);
                    maxHeight += rectangle.getHeight();
                    freeWidth = containerWidth - rectangle.getLength();
                } else if (!hasPlace) { // если места ниже не нашлось, но уместится на текущий уровень
                    freeWidth -= rectangle.getLength();
                }
            }
        }

        System.out.println("FFDH: " + maxHeight);
        return maxHeight;
    }
}
