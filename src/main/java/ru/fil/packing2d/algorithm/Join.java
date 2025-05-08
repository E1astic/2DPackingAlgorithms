package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Прямоугольники сортируются по невозрастанию высот.
 * После этого они группируются в пары по следующему правилу:
 * <ul>
 *     <li>Высоты прямоугольников в паре должны отличаться не более, чем на 10% от ширины контейнера</li>
 *     <li>Эта пара должна уместиться по ширине в контейнер</li>
 * </ul>
 * <p>Если пара не помещается на текущий уровень с другими парами, она помещается на уровень выше.</p>
 * <p>Когда все пары упакованы, все оставшиеся прямоугольники укладываются выше по правилу {@link FFDH}.</p>
 */
public class Join implements PackingAlgorithm {
    @Override
    public int solve(Container container, List<Rectangle> rectangles) {
        rectangles.sort(Rectangle::compareTo);
        Set<Integer> rectanglesInPair = new HashSet<>();

        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;
        int maxHeight = 0;
        List<Integer> remains = new ArrayList<>();
        List<Integer> levelHeights = new ArrayList<>();
        final double maxHeightRatio = 0.1;
        final double maxHeightDiff = (double) containerWidth * maxHeightRatio;

        // составляем пары прямоугольников
        for (int i = 0; i < rectangles.size() - 1; ++i) {
            Rectangle rectangle = rectangles.get(i);
            if (!rectanglesInPair.contains(i)) {
                for (int j = i + 1; j < rectangles.size(); ++j) {
                    Rectangle nextRectangle = rectangles.get(j);

                    if (!rectanglesInPair.contains(j)) {
                        double heightDiff = rectangle.getHeight() - nextRectangle.getHeight();
                        int sumLength = rectangle.getLength() + nextRectangle.getLength();

                        if (heightDiff <= maxHeightDiff) {    // если высоты прямоугольников отличаются не более чем на 10% от ширины емкости
                            if (sumLength <= freeWidth) {       // и они поместятся на текущий уровень
                                if (maxHeight == 0) {
                                    maxHeight = rectangle.getHeight();
                                    levelHeights.add(rectangle.getHeight());  // устанавливаем высоту текущего уровня
                                }
                                rectanglesInPair.add(i);
                                rectanglesInPair.add(j);
                                freeWidth -= sumLength;
                                break;
                            } else if (sumLength <= containerWidth) {  // не поместятся на текущий уровень, но поместятся на следующий уровень
                                remains.add(freeWidth);
                                levelHeights.add(rectangle.getHeight());  // устанавливаем высоту для нового уровня
                                maxHeight += rectangle.getHeight();
                                freeWidth = containerWidth - sumLength;
                                rectanglesInPair.add(i);
                                rectanglesInPair.add(j);
                                break;
                            }
                        } else
                            break;
                    }
                }
            }
        }
        if (remains.size() != levelHeights.size()) {  // добавляем последнюю пару
            remains.add(freeWidth);
        }

        freeWidth = containerWidth;

        boolean firstIter = true;

        // размещаем прямоугольники, оставшиеся без пары
        for (int i = 0; i < rectangles.size(); ++i) {
            Rectangle rectangle = rectangles.get(i);
            if (!rectanglesInPair.contains(i)) {  // если это еще не упакованный объект
                boolean hasPlace = false;
                for (int j = 0; j < remains.size(); ++j) {      // ищем ему первое подходящее место (FFDH)
                    if (remains.get(j) >= rectangle.getLength() && levelHeights.get(j) >= rectangle.getHeight()) {
                        remains.set(j, remains.get(j) - rectangle.getLength());
                        hasPlace = true;
                        break;
                    }
                }
                if (!hasPlace) {  // если места ниже не нашлось
                    if (rectangle.getLength() <= freeWidth) {  // и на текущем уровне места достаточно
                        if (maxHeight == 0 || firstIter) {
                            maxHeight += rectangle.getHeight();
                            firstIter = false;
                        }
                    } else {
                        remains.add(freeWidth);
                        levelHeights.add(maxHeight - (levelHeights.isEmpty() ? 0 : levelHeights.getLast()));
                        maxHeight += rectangle.getHeight();
                        freeWidth = containerWidth;
                    }
                    freeWidth -= rectangle.getLength();
                }
            }
        }

        System.out.println("JOIN: " + maxHeight);
        return maxHeight;
    }
}
