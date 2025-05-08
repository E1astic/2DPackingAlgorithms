package ru.fil.packing2d.algorithm;


import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Floor Ceiling No Rotation - алгоритм, который на каждом уровне позволяет размещать прямоугольники
 * на "пол" и "потолок."</p>
 * <p>Для каждого прямоугольника сначала ищется свободное место "на полу" среди всех уровней ниже (как в {@link FFDH}).
 * Если такого места не нашлось, выполняется поиск свободного места "на потолке" каждого из уровней -
 * начиная с нижнего уровня и справа налево в каждом уровне.</p>
 * <p>Если свободного места ниже не нашлось, прямоугольник помещается на новый уровень.</p>
 */
public class FCNR implements PackingAlgorithm {
    @Override
    public int solve(Container container, List<Rectangle> rectangles) {
        rectangles.sort(Rectangle::compareTo);  // сортировка по невозрастанию высот

        int maxHeight = 0;
        final int containerWidth = container.getWidth();
        int freeWidth = containerWidth;

        // оставшееся место на "потолках" каждого уровня
        List<List<Integer>> widthCeilRemains = new ArrayList<>();
        List<List<Integer>> heightCeilRemains = new ArrayList<>();

        // оставшееся место на "потолке" текущего уровня
        List<Integer> currWidthCeilRemains = null;
        List<Integer> currHeightCeilRemains = null;
        int currLevelHeight = 0;

        for (Rectangle rectangle : rectangles) {
            if (maxHeight == 0) {
                maxHeight = rectangle.getHeight();
                freeWidth -= rectangle.getLength();
                currLevelHeight = rectangle.getHeight();
                currWidthCeilRemains = new ArrayList<>();
                currHeightCeilRemains = new ArrayList<>();

                currWidthCeilRemains.add(freeWidth);
                currHeightCeilRemains.add(currLevelHeight);

                widthCeilRemains.add(currWidthCeilRemains);
                heightCeilRemains.add(currHeightCeilRemains);
            } else {

                // Свободные места на каждом из уровней ниже
                List<Integer> lowerLevelsWidthCeilRemains;
                List<Integer> lowerLevelsHeightCeilRemains;

                // ищем место на "полу" среди уровней ниже до текущего включительно
                boolean hasPlace = false;
                for (int i = 0; i < heightCeilRemains.size(); ++i) {
                    lowerLevelsWidthCeilRemains = widthCeilRemains.get(i);
                    lowerLevelsHeightCeilRemains = heightCeilRemains.get(i);
                    int lastInd = lowerLevelsWidthCeilRemains.size() - 1;
                    int widthRemain = lowerLevelsWidthCeilRemains.get(lastInd);
                    int heightRemain = lowerLevelsHeightCeilRemains.get(lastInd);

                    if (rectangle.getLength() <= widthRemain  // если место нашлось
                            && rectangle.getHeight() <= heightRemain) {

                        // добавляем уменьшенный оставшийся пустой блок после добавления прямоугольника
                        lowerLevelsWidthCeilRemains.add(widthRemain - rectangle.getLength());
                        lowerLevelsHeightCeilRemains.add(heightRemain);

                        // обновляем блок, в который поместили прямоугольник
                        lowerLevelsWidthCeilRemains.set(lastInd, rectangle.getLength());
                        lowerLevelsHeightCeilRemains.set(lastInd, heightRemain - rectangle.getHeight());

                        hasPlace = true;

                        if (i == heightCeilRemains.size() - 1)
                            freeWidth -= rectangle.getLength();

                        break;
                    }
                }

                // ищем место на "потолке" среди уровней ниже, если не нашлось место на "полу"
                if (!hasPlace) {
                    for (int i = 0; i < widthCeilRemains.size(); ++i) {
                        lowerLevelsWidthCeilRemains = widthCeilRemains.get(i);
                        lowerLevelsHeightCeilRemains = heightCeilRemains.get(i);

                        // пройдем по всем "потолкам" уровней снизу вверх и справа налево
                        for (int j = lowerLevelsWidthCeilRemains.size() - 1; j >= 0; --j) {
                            int sumLength = 0;

                            int k = j;
                            // пытаемся уместить прямоугольник на потолок
                            while (k >= 0 && rectangle.getLength() > sumLength) {
                                sumLength += lowerLevelsWidthCeilRemains.get(k);

                                if (rectangle.getHeight() > lowerLevelsHeightCeilRemains.get(k))
                                    break;
                                --k;
                            }

                            // если прямоугольник уместился на потолок по ширине и высоте
                            k = Math.max(k, 0);
                            if (rectangle.getHeight() <= lowerLevelsHeightCeilRemains.get(k) && rectangle.getLength() <= sumLength) {
                                hasPlace = true;

                                // обновим оставшуюся высоту на каждом блоке потолка
                                for (int z = k; z < lowerLevelsWidthCeilRemains.size(); ++z) {
                                    lowerLevelsHeightCeilRemains.set(z, lowerLevelsHeightCeilRemains.get(z) - rectangle.getHeight());
                                }
                                break;
                            }
                        }
                        if (hasPlace)
                            break;
                    }

                    if (!hasPlace && rectangle.getLength() > freeWidth) { // если среди уровней ниже и на текущем места вообще не нашлось, переносим на уровень выше
                        maxHeight += rectangle.getHeight();
                        freeWidth = containerWidth - rectangle.getLength();

                        currWidthCeilRemains = new ArrayList<>();
                        currHeightCeilRemains = new ArrayList<>();
                        currLevelHeight = rectangle.getHeight();
                        currWidthCeilRemains.add(freeWidth);
                        currHeightCeilRemains.add(currLevelHeight);

                        widthCeilRemains.add(currWidthCeilRemains);
                        heightCeilRemains.add(currHeightCeilRemains);
                    }
                }
            }
        }

        System.out.println("FCNR: " + maxHeight);
        return maxHeight;
    }
}
