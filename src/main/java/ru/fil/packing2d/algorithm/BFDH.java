package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Best Fit Decreasing High - усовершенствованная версия {@link FFDH}.</p>
 * <p>Прямоугольники также сортируются по невозрастанию высот.
 * Первым делом для каждого прямоугольника ищется подходящее место среди всех уровней ниже.
 * Среди всех найденных подходящих мест на уровнях ниже выбирается то, на котором останется
 * минимальное свободное пространство после установки прямоугольника.
 * Если такого места не нашлось, или это первый упаковываемый прямоугольник, он размещается
 * на текущий уровень.</p>
 */
public class BFDH implements PackingAlgorithm {

    @Override
    public int solve(Container container, List<Rectangle> rectangles) {

        rectangles.sort(Rectangle::compareTo);

        int maxHeight = 0;
        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;
        List<Integer> remains = new ArrayList<>();  // храним остатки свободного места на каждом уровне

        for (Rectangle rectangle : rectangles) {
            if (maxHeight == 0) {
                maxHeight = rectangle.getHeight();
                freeWidth -= rectangle.getLength();
            } else {
                // ПЫТАЕМСЯ НАЙТИ ЛУЧШЕЕ МЕСТО СРЕДИ УРОВНЕЙ НИЖЕ
                int indMin = -1;
                int freeWidthMin = Integer.MAX_VALUE;
                for (int i = 0; i < remains.size(); ++i) {
                    if (remains.get(i) >= rectangle.getLength()) {  // если нашлось место
                        if ((remains.get(i) - rectangle.getLength()) < freeWidthMin) {  // запоминаем лучшее место
                            freeWidthMin = remains.get(i) - rectangle.getLength();
                            indMin = i;
                        }
                    }
                }
                if (indMin != -1) {  // если нашлось хотя бы 1 место, то устанавливаем на лучшее
                    remains.set(indMin, freeWidthMin);
                } else if (rectangle.getLength() <= freeWidth) {
                    freeWidth -= rectangle.getLength();
                } else {
                    remains.add(freeWidth);
                    maxHeight += rectangle.getHeight();
                    freeWidth = containerWidth - rectangle.getLength();
                }
            }
        }

        System.out.println("BFDH: " + maxHeight);
        return maxHeight;
    }
}
