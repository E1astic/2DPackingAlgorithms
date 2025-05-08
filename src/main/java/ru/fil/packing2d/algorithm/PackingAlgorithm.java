package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.List;

public interface PackingAlgorithm {

    /**
     * @param container контейнер {@link Container}, в который упаковываются объекты
     * @param rectangles список всех прямоугольников {@link Rectangle}, которые надо упаковать в контейнер
     * @return Максимальная высота, заполняемая прямоугольниками
     * с учетом возможных пустот
     */
    int solve(Container container, List<Rectangle> rectangles);
}
