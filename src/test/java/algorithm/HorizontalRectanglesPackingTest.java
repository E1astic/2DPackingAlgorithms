package algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fil.packing2d.algorithm.BFDH;
import ru.fil.packing2d.algorithm.Burke;
import ru.fil.packing2d.algorithm.FCNR;
import ru.fil.packing2d.algorithm.FFDH;
import ru.fil.packing2d.algorithm.Join;
import ru.fil.packing2d.algorithm.NFDH;
import ru.fil.packing2d.algorithm.PackingAlgorithm;
import ru.fil.packing2d.algorithm.Sleator;
import ru.fil.packing2d.algorithm.SplitFit;
import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRectanglesPackingTest {
    private static Container container;
    private List<Rectangle> rectangles;

    @BeforeAll
    static void setUp(){
        container = new Container(20);
    }

    @BeforeEach
    void init(){
        rectangles = new ArrayList<>() {{
            add(new Rectangle(10, 4));
            add(new Rectangle(14, 4));
            add(new Rectangle(8, 3));
            add(new Rectangle(20, 9));
            add(new Rectangle(20, 10));
            add(new Rectangle(10, 3));
            add(new Rectangle(18, 9));
            add(new Rectangle(8, 1));
            add(new Rectangle(10, 4));
            add(new Rectangle(20, 5));
        }};
    }

    @Test
    void randomRectanglesHeight_NFDH(){
        PackingAlgorithm NFDH = new NFDH();

        int expected = 48;
        int actual = NFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_FFDH(){
        PackingAlgorithm FFDH = new FFDH();

        int expected = 45;
        int actual = FFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_BFDH(){
        PackingAlgorithm BFDH = new BFDH();

        int expected = 45;
        int actual = BFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_SplitFit(){
        PackingAlgorithm splitFit = new SplitFit();

        int expected = 45;
        int actual = splitFit.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Join(){
        PackingAlgorithm join = new Join();

        int expected = 45;
        int actual = join.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_FCNR(){
        PackingAlgorithm FCNR = new FCNR();

        int expected = 45;
        int actual = FCNR.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Sleator(){
        PackingAlgorithm sleator = new Sleator();

        int expected = 47;
        int actual = sleator.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Burke(){
        PackingAlgorithm burke = new Burke();

        int expected = 45;
        int actual = burke.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }
}
