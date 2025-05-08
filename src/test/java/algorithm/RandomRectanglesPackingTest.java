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

public class RandomRectanglesPackingTest {

    private static Container container;
    private List<Rectangle> rectangles;

    @BeforeAll
    static void setUp(){
        container = new Container(20);
    }

    @BeforeEach
    void init(){
        rectangles = new ArrayList<>() {{
            add(new Rectangle(7, 13));
            add(new Rectangle(2, 11));
            add(new Rectangle(5, 8));
            add(new Rectangle(7, 20));
            add(new Rectangle(7, 5));
            add(new Rectangle(2, 4));
            add(new Rectangle(15, 1));
            add(new Rectangle(11, 10));
            add(new Rectangle(10, 17));
            add(new Rectangle(10, 18));
        }};
    }

    @Test
    void randomRectanglesHeight_NFDH(){
        PackingAlgorithm NFDH = new NFDH();

        int expected = 53;
        int actual = NFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_FFDH(){
        PackingAlgorithm FFDH = new FFDH();

        int expected = 53;
        int actual = FFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_BFDH(){
        PackingAlgorithm BFDH = new BFDH();

        int expected = 53;
        int actual = BFDH.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_SplitFit(){
        PackingAlgorithm splitFit = new SplitFit();

        int expected = 53;
        int actual = splitFit.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Join(){
        PackingAlgorithm join = new Join();

        int expected = 66;
        int actual = join.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_FCNR(){
        PackingAlgorithm FCNR = new FCNR();

        int expected = 53;
        int actual = FCNR.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Sleator(){
        PackingAlgorithm sleator = new Sleator();

        int expected = 59;
        int actual = sleator.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void randomRectanglesHeight_Burke(){
        PackingAlgorithm burke = new Burke();

        int expected = 48;
        int actual = burke.solve(container, rectangles);
        Assertions.assertEquals(expected, actual);
    }
}
