package ru.fil.packing2d.tests;

import ru.fil.packing2d.models.AlgorithmScore;
import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;
import ru.fil.packing2d.algorithm.BFDH;
import ru.fil.packing2d.algorithm.Burke;
import ru.fil.packing2d.algorithm.FCNR;
import ru.fil.packing2d.algorithm.FFDH;
import ru.fil.packing2d.algorithm.Join;
import ru.fil.packing2d.algorithm.NFDH;
import ru.fil.packing2d.algorithm.PackingAlgorithm;
import ru.fil.packing2d.algorithm.Sleator;
import ru.fil.packing2d.algorithm.SplitFit;
import ru.fil.packing2d.tests.generator.RectangleGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Тестовый класс, прогоняющий алгоритмы упаковки по нескольким различным
 * генерациям прямоугольников {@link RectangleGenerator},
 * подсчитывающий результаты упаковки и формирующий таблицу с очками для каждого алгоритма.</p>
 * <p>Чем лучше алгоритм упаковал объекты, тем больше очков он получает. В случае равных результатов
 * упаковки алгоритмы получают одинаковое число очков</p>
 */
public class PackingAlgorithmTest {

    private Container container;

    private int rectanglesNum;

    private RectangleGenerator rectangleGenerator;

    private final List<PackingAlgorithm> packingAlgorithms;

    private final Map<String, Integer> globalAlgorithmPoints;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public int getRectanglesNum() {
        return rectanglesNum;
    }

    public void setRectanglesNum(int rectanglesNum) {
        this.rectanglesNum = rectanglesNum;
    }

    public RectangleGenerator getRectangleGenerator() {
        return rectangleGenerator;
    }

    public void setRectangleGenerator(RectangleGenerator rectangleGenerator) {
        this.rectangleGenerator = rectangleGenerator;
    }

    public PackingAlgorithmTest(Container container, int rectanglesNum,
                                RectangleGenerator rectangleGenerator) {
        this.container = container;
        this.rectanglesNum = rectanglesNum;
        this.rectangleGenerator = rectangleGenerator;
        packingAlgorithms = new ArrayList<>(List.of(
                new NFDH(), new FFDH(), new BFDH(), new SplitFit(), new Join(), new FCNR(), new Sleator(), new Burke())
        );
        globalAlgorithmPoints = new HashMap<>() {{
            put(NFDH.class.getSimpleName(), 0);
            put(FFDH.class.getSimpleName(), 0);
            put(BFDH.class.getSimpleName(), 0);
            put(SplitFit.class.getSimpleName(), 0);
            put(Join.class.getSimpleName(), 0);
            put(FCNR.class.getSimpleName(), 0);
            put(Sleator.class.getSimpleName(), 0);
            put(Burke.class.getSimpleName(), 0);
        }};
    }

    /**
     * Метод в цикле выполняет прогон всех алгоритмов упаковки {@link PackingAlgorithm}
     * по определенной генерации прямоугольников {@link RectangleGenerator} с подсчетом очков каждого алгоритма
     * для определения качества упаковки.
     *
     * @return итоговая карта результатов {@link Map} с очками для каждого алгоритма
     * после прогонов по некоторой генерации прямоугольников {@link RectangleGenerator}
     */
    public Map<String, Integer> getTestResults() {
        rectangleGenerator.setContainerWidth(container.getWidth());
        rectangleGenerator.setRectanglesNum(rectanglesNum);

        for (int i = 0; i < 3; ++i) {
            List<AlgorithmScore> algorithmPoints = getTestResultsForRectanglesGeneration(rectangleGenerator);

            System.out.println("--------------------------");
            System.out.println(algorithmPoints);

            accumulateAlgorithmPoints(algorithmPoints);
        }
        return globalAlgorithmPoints;
    }

    private void accumulateAlgorithmPoints(List<AlgorithmScore> algorithmPoints) {
        for (AlgorithmScore algorithmScore : algorithmPoints) {
            globalAlgorithmPoints.put(algorithmScore.getAlgorithmName(),
                    globalAlgorithmPoints.get(algorithmScore.getAlgorithmName()) + algorithmScore.getPoints());
        }
    }

    private List<AlgorithmScore> getTestResultsForRectanglesGeneration(RectangleGenerator generator) {
        List<Rectangle> rectangles = generator.generateRectangles();
        rectangles.forEach(System.out::println);
        List<AlgorithmScore> algorithmScores = new ArrayList<>();

        for (PackingAlgorithm algorithm : packingAlgorithms) {
            String algorithmName = algorithm.getClass().getSimpleName();
            int height = algorithm.solve(container, rectangles);
            algorithmScores.add(new AlgorithmScore(algorithmName, height));
        }

        // сортировка результатов алгоритмов по неубыванию высоты (от лучшего к худшему)
        algorithmScores.sort(Comparator.comparingInt(AlgorithmScore::getPoints));

        return castTestResultsToPoints(algorithmScores);
    }

    private List<AlgorithmScore> castTestResultsToPoints(List<AlgorithmScore> algorithmScores) {
        List<AlgorithmScore> algorithmPoints = algorithmScores.stream().map(AlgorithmScore::copy).toList();

        int currentPoints = algorithmPoints.size();  // максимальное число очков - количество алгоритмов в тесте
        int step = 1;  // шаг уменьшения количества очков
        algorithmPoints.getFirst().setPoints(currentPoints);

        for (int i = 1; i < algorithmPoints.size(); ++i) {
            AlgorithmScore currAlgorithmScore = algorithmPoints.get(i);

            // алгоритмы с одинаковыми результатами получают одинаковое число очков
            if (algorithmScores.get(i - 1).getPoints() == currAlgorithmScore.getPoints()) {
                ++step;
            } else {
                currentPoints -= step;
                step = 1;
            }
            currAlgorithmScore.setPoints(currentPoints);
        }
        return algorithmPoints;
    }
}
