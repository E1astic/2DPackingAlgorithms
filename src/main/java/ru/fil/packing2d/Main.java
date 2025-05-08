package ru.fil.packing2d;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.tests.PackingAlgorithmTest;
import ru.fil.packing2d.tests.generator.HorizontalRectangleGenerator;
import ru.fil.packing2d.tests.generator.RandomRectangleGenerator;
import ru.fil.packing2d.tests.generator.RectangleGenerator;
import ru.fil.packing2d.tests.generator.SquaresGenerator;
import ru.fil.packing2d.tests.generator.VerticalRectangleGenerator;

import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Container container = new Container(20);
        int rectanglesNum = 100;

        RectangleGenerator randomRectangleGenerator = new RandomRectangleGenerator();
        RectangleGenerator horizontalRectangleGenerator = new HorizontalRectangleGenerator();
        RectangleGenerator verticalRectangleGenerator = new VerticalRectangleGenerator();
        RectangleGenerator squaresGenerator = new SquaresGenerator();

        System.out.println("\n\n-----RANDOM RECTANGLES-----\n");
        PackingAlgorithmTest packingAlgorithmTest = new PackingAlgorithmTest(
                container, rectanglesNum, randomRectangleGenerator);
        Map<String, Integer> results = packingAlgorithmTest.getTestResults();
        System.out.println(results);

        System.out.println("\n\n-----HORIZONTAL RECTANGLES-----\n");
        packingAlgorithmTest.setRectangleGenerator(horizontalRectangleGenerator);
        results = packingAlgorithmTest.getTestResults();
        System.out.println(results);

        System.out.println("\n\n-----VERTICAL RECTANGLES-----\n");
        packingAlgorithmTest.setRectangleGenerator(verticalRectangleGenerator);
        results = packingAlgorithmTest.getTestResults();
        System.out.println(results);

        System.out.println("\n\n-----SQUARES-----\n");
        packingAlgorithmTest.setRectangleGenerator(squaresGenerator);
        results = packingAlgorithmTest.getTestResults();
        System.out.println(results);
    }
}