package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Split Fit делит контейнер по ширине на 3 равных части.
 * Все прямоугольники распределяются по 3 группам:
 * <ul>
 *    <li>В группу A попадают те, длина которых больше, чем 2/3 ширины контейнера</li>
 *    <li>В группу B попадают те, которые не попали в группу А, при этом их длина больше, чем 1/2 ширины контейнера</li>
 *    <li>В группу С попадают все остальные: длина которых меньше, либа равна 1/2 ширины контейнера</li>
 * </ul>
 * <p>Таким образом, вторая группа гарантировано оставляет свободное пространство в виде 1/3 ширины контейнера.</p>
 * <p>Прямоугольники групп А и В последовательно укладываются друг на друга, а в группе С
 * сортируются по невозрастанию высот.</p>
 * <p>Первым делом происходит попытка поместить прямоугольники группы С в оставшееся пространство группы В.
 * Если прямоугольник не умещается в остаток группы В, он укладывается выше по алгоритму {@link FFDH}</p>
 */
public class SplitFit implements PackingAlgorithm {

    @Override
    public int solve(Container container, List<Rectangle> rectangles) {

        int maxHeight = 0;
        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;
        List<Integer> remains = new ArrayList<>();  // остатки свободного пространства на уровнях группы С

        final double halfPart = (double) containerWidth / 2;
        final double twoThirdsPart = containerWidth * 0.66667;
        final double oneThirdsPart = containerWidth - twoThirdsPart;

        List<Rectangle> groupC = new ArrayList<>();
        int heightA = 0, heightB = 0, heightC = 0;
        double freeWidthB = oneThirdsPart;

        for (Rectangle rectangle : rectangles) {
            if (rectangle.getLength() > twoThirdsPart) {
                heightA += rectangle.getHeight();
            } else if (rectangle.getLength() > halfPart) {
                heightB += rectangle.getHeight();
            } else {
                groupC.add(rectangle);
            }
        }
        maxHeight = heightA + heightB;

        // сортируем каждую группу по невозрастанию высот
        groupC.sort(Rectangle::compareTo);

        for (Rectangle rectangle : groupC) {
            if (heightB > 0 && freeWidthB >= rectangle.getLength()    // если прямоугольник уместится по длине в остаток группы B
                    && rectangle.getHeight() <= heightB) {            // и по высоте не превзойдет высоту группы B (при условии, что группа В существует)
                freeWidthB -= rectangle.getLength();
            } else {  // если не уместился в остаток группы В
                if (heightC == 0) {  // если это первое добавление в группу С
                    heightC += rectangle.getHeight();
                    maxHeight += rectangle.getHeight();
                    freeWidth -= rectangle.getLength();
                } else {
                    // ищем место среди уровней ниже группы С по FFDH
                    boolean hasPlace = false;
                    for (int i = 0; i < remains.size(); ++i) {
                        if (remains.get(i) >= rectangle.getLength()) {
                            hasPlace = true;
                            remains.set(i, remains.get(i) - rectangle.getLength());
                            break;
                        }
                    }

                    if (!hasPlace && rectangle.getLength() > freeWidth) { // если места ниже и на текущем уровне не нашлось
                        remains.add(freeWidth);
                        maxHeight += rectangle.getHeight();
                        freeWidth = containerWidth - rectangle.getLength();
                    } else if (!hasPlace) {  // если места ниже не нашлось, но на текущем уровне нашлось
                        freeWidth -= rectangle.getLength();
                    }
                }
            }
        }

        System.out.println("SPLITFIT: " + maxHeight);
        return maxHeight;
    }
}
