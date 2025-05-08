package ru.fil.packing2d.algorithm;

import ru.fil.packing2d.models.Container;
import ru.fil.packing2d.models.Rectangle;

import java.util.List;

public class Solution2DSP {

    private PackingAlgorithm packingAlgorithm;

    public Solution2DSP(PackingAlgorithm packingAlgorithm) {
        this.packingAlgorithm = packingAlgorithm;
    }

    public PackingAlgorithm getPackingAlgorithm() {
        return packingAlgorithm;
    }

    public void setPackingAlgorithm(PackingAlgorithm packingAlgorithm) {
        this.packingAlgorithm = packingAlgorithm;
    }

    public int solve(Container container, List<Rectangle> rectangles) {
        return packingAlgorithm.solve(container, rectangles);
    }
}
