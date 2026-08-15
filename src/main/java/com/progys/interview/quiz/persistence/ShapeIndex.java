package com.progys.interview.quiz.persistence;

import com.progys.interview.quiz.model.BoundingBox;
import com.progys.interview.quiz.model.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory uniform-grid index over shape bounding boxes. A point query only examines shapes
 * whose bounding box overlaps the grid cell containing the query point, instead of scanning
 * every stored shape. Shapes whose bounding box spans more than {@link #MAX_OVERLAPPED_CELLS}
 * cells are kept in an overflow list checked by every query, which bounds the index memory
 * footprint for very large shapes.
 *
 * @author progys
 */
public final class ShapeIndex {
    private static final double DEFAULT_CELL_SIZE = 10.0;
    private static final int MAX_OVERLAPPED_CELLS = 64;

    private final double cellSize;
    private final Map<Cell, List<StoredShape>> cells = new HashMap<>();
    private final List<StoredShape> largeShapes = new ArrayList<>();

    public ShapeIndex() {
        this(DEFAULT_CELL_SIZE);
    }

    public ShapeIndex(double cellSize) {
        this.cellSize = cellSize;
    }

    public void put(StoredShape stored) {
        BoundingBox bounds = stored.shape().getBounds();
        long minX = cell(bounds.minX());
        long maxX = cell(bounds.maxX());
        long minY = cell(bounds.minY());
        long maxY = cell(bounds.maxY());

        long width = maxX - minX + 1;
        long height = maxY - minY + 1;
        if (width > MAX_OVERLAPPED_CELLS || height > MAX_OVERLAPPED_CELLS
                || width * height > MAX_OVERLAPPED_CELLS) {
            largeShapes.add(stored);
            return;
        }
        for (long x = minX; x <= maxX; x++) {
            for (long y = minY; y <= maxY; y++) {
                cells.computeIfAbsent(new Cell(x, y), key -> new ArrayList<>()).add(stored);
            }
        }
    }

    public Collection<StoredShape> query(Point point) {
        List<StoredShape> candidates =
                cells.getOrDefault(new Cell(cell(point.x), cell(point.y)), List.of());
        if (largeShapes.isEmpty()) {
            return candidates;
        }
        List<StoredShape> result = new ArrayList<>(candidates.size() + largeShapes.size());
        result.addAll(candidates);
        result.addAll(largeShapes);
        return result;
    }

    public void clear() {
        cells.clear();
        largeShapes.clear();
    }

    private long cell(double coordinate) {
        return (long) Math.floor(coordinate / cellSize);
    }

    private record Cell(long x, long y) {
    }
}
