package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Sleator - сначала друг на друга укладываются все прямоугольники, длина которых больше половины ширины контейнера.
 * <p>Все остальные сортируются по невозрастанию высоты, и в следующий ряд 'M' укладываются прямоугольники
 * до тех пор, пока занятое расстояние уровня не превысит половину ширины полосы.</p>
 * <p>Все оставшиеся прямоугольники начинают укладываться то слева, то справа по принципу
 * "где на данный момент ниже общая высота":</p>
 * <ul>
 *     <li>Если прямоугольник укладывается слева - он укладывается впритык к левому краю контейнера</li>
 *     <li>Если прямоугольник укладывается справа - он укладывается своим левым краем к середине контейнера.</li>
 * </ul>
 * <p>Именно поэтому ряд 'M' и заполнялся больше, чем на середину контейнера,
 * чтобы остальным прямоугольникам "было на чем стоять"</p>
 */
public class Sleator implements PackingAlgorithm {
    @Override
    public int solve(Container container, List<Rectangle> rectangles) {
        List<Rectangle> rectanglesForDistribution = new ArrayList<>();

        int maxHeight = 0;
        final double halfPart = (double) container.getWidth() / 2;

        for (Rectangle rectangle : rectangles) {
            if (rectangle.getLength() > halfPart) {
                maxHeight += rectangle.getHeight();
            } else {
                rectanglesForDistribution.add(rectangle);
            }
        }

        rectanglesForDistribution.sort(Rectangle::compareTo); // сортировка по невозрастанию высот

        boolean firstAdding = true;
        int leftHeight = maxHeight;
        int rightHeight = 0;
        int oldMaxHeight = maxHeight;
        int sumLengthSecondLevel = 0;

        for (Rectangle rectangle : rectanglesForDistribution) {
            if (sumLengthSecondLevel < halfPart) {  // уровень не заполнен больше чем на половину, укладываем в один ряд
                if (firstAdding) {
                    maxHeight += rectangle.getHeight();
                    leftHeight = maxHeight;  // стартовая высота левой части равна текущей максимальной высоте
                    firstAdding = false;
                }
                sumLengthSecondLevel += rectangle.getLength();
                rightHeight = oldMaxHeight + rectangle.getHeight(); // записываем стартовую высоту правой части

            } else {  // когда уровень заполнился больше чем на половину, начинаем укладывать выше поочереди по принципу "то слева, то справа"
                if(leftHeight < rightHeight) {
                    leftHeight += rectangle.getHeight();
                }
                else{
                    rightHeight += rectangle.getHeight();
                }
            }
        }

        maxHeight = Math.max(leftHeight, rightHeight);

        System.out.println("SLEATOR: " + maxHeight);
        return maxHeight;
    }
}
