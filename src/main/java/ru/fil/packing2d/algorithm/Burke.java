package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;
import ru.fil.packing2d.models.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Burke - не уровневый алгоритм, который хранит карту высот.</p>
 * <p>Алгоритм использует массив, который для каждой точки по ширине контейнера
 * хранит текущую занятую высоту.</p>
 * <p>Прямоугольники сортируются по невозрастанию ширины. На каждой итерации алгоритма ищется
 * интервал(ы) с минимальной занятой высотой, в который выполняется попытка разместить
 * наиболее подходящий прямоугольник, который оставил бы после размещения минимальное свободное пространство.
 * После упаковки высота текущего интервала увеличивается на высоту упакованного прямоугольника.
 * </p>
 * <p>Если для текущего интервала не находится подходящего по длине прямоугольника,
 * имитируется заполнение интервала до минимальной высоты
 * одного из интервалов-соседей (находящихся слева и справа от текущего). Таким образом мы увеличивается длину интервала
 * с новой минимальной высотой, повышая вероятность упаковки следующего прямоугольника</p>
 */
public class Burke implements PackingAlgorithm {

    @Override
    public int solve(Container container, List<Rectangle> rectangles) {
        List<Rectangle> rectanglesCopy = new ArrayList<>(rectangles.stream().map(Rectangle::copy).toList());
        rectanglesCopy.sort((rect1, rect2) -> Integer.compare(rect2.getLength(), rect1.getLength()));  // сортировка по невозрастанию длин

        int[] heights = new int[container.getWidth()];

        int i = rectanglesCopy.size();  // количество прямоугольников, которые необходимо разместить
        while (i > 0) {
            int minHeight = getMinHeight(heights);  // минимальная высота на данном шаге
            List<Interval> intervals = getIntervalsWithMinHeight(heights, minHeight);  // интервалы с минимальной высотой
            int intervalsSize = intervals.size();  // количество интервалов, в которые можем разместить прямоугольники

            for (Interval interval : intervals) {    // для каждого интервала будем искать подходящий прямоугольник
                Rectangle suitableRectangle = null;

                for (Rectangle rectangle : rectanglesCopy) {             // так как прямоугольники отсортированы по невозрастанию длин
                    if (rectangle.getLength() <= interval.getLength()) {  // выбираем первый подходящий прямоугольник
                        suitableRectangle = rectangle;                   // он автоматически будет тем, который оставит минимум свободного пространства
                        break;
                    }
                }

                if (suitableRectangle == null) {  // если прямоугольника не нашлось, интервал остался незанятым
                    --intervalsSize;
                    updateHeightsOnInterval(heights, interval);
                } else {
                    updateHeightsOnInterval(heights, interval, suitableRectangle);
                    rectanglesCopy.remove(suitableRectangle);  // удаляем установленный прямоугольник, чтобы в последствии его не рассматривать
                }
            }
            i -= intervalsSize;  // уменьшаем количество упаковываемых прямоугольников на количество размещенных
        }

        int maxHeight = Integer.MIN_VALUE;

        for (int j = 0; j < heights.length; ++j) {
            if (heights[j] > maxHeight) {
                maxHeight = heights[j];
            }
        }

        System.out.println("Burke: " + maxHeight);
        return maxHeight;
    }


    private int getMinHeight(int[] heights) {
        int minHeight = Integer.MAX_VALUE;
        for (int i = 0; i < heights.length; ++i) {
            if (heights[i] < minHeight) {
                minHeight = heights[i];
            }
        }
        return minHeight;
    }

    private List<Interval> getIntervalsWithMinHeight(int[] heights, int minHeight) {
        List<Interval> intervals = new ArrayList<>();
        int startInd = 0;
        boolean hasStart = false;

        for (int i = 0; i < heights.length; ++i) {
            if (heights[i] == minHeight && !hasStart) {  // фиксируем начало подходящего интервала
                startInd = i;
                hasStart = true;
            } else if (heights[i] != minHeight && hasStart) {  // фиксируем конец подходящего интервала
                intervals.add(new Interval(startInd, i - 1, i - startInd));
                hasStart = false;
            }
        }
        if (hasStart) {  // добавляем последний интервал в конце контейнера, если такой есть
            intervals.add(new Interval(startInd, heights.length - 1, heights.length - startInd));
        }

        return intervals;
    }

    /**
     * Обновляет высоту интервала после успешного добавления прямоугольника в этот интервал
     *
     * @param heights           карта выот
     * @param interval          обновляемый интервал
     * @param suitableRectangle установленный прямоугольник
     */
    private void updateHeightsOnInterval(int[] heights, Interval interval, Rectangle suitableRectangle) {
        for (int i = interval.getStart(); i < interval.getStart() + suitableRectangle.getLength(); ++i) {
            heights[i] += suitableRectangle.getHeight();
        }
    }

    /**
     * Поднимает высоту интервала (имитирует заполнение пустого интервала) до минимальной высоты
     * одного из интервалов-соседей (ближайшего слева или справа).
     * Предполагается, что ближайший интервал слева или справа существует и обновить высоту удастся,
     * иначе происходит попытка добавления прямоугольника, который в принципе не вмещается в контейнер.
     *
     * @param heights  карта высот
     * @param interval обновляемый интервал
     */
    private void updateHeightsOnInterval(int[] heights, Interval interval) {
        int leftIntervalHeight = Integer.MAX_VALUE;
        int rightIntervalHeight = Integer.MAX_VALUE;

        if (interval.getStart() > 0) {
            leftIntervalHeight = heights[interval.getStart() - 1];
        }
        if (interval.getEnd() < heights.length - 1) {
            rightIntervalHeight = heights[interval.getEnd() + 1];
        }

        int minHeight = Math.min(leftIntervalHeight, rightIntervalHeight);

        for (int i = interval.getStart(); i <= interval.getEnd(); ++i) {
            heights[i] = minHeight;
        }
    }
}
