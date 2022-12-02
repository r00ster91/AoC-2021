package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Heightmap {
    ArrayList<Integer> heights = new ArrayList<>();
    int w;
    int h;

    Integer getHeight(int x, int y) {
        if (x < 0 || x >= w || y < 0 || y >= h) {
            return null;
        }

        return heights.get(x + w * y);
    }

    private void addNonNullHeight(int x, int y, List<Integer> heights) {
        Integer height = getHeight(x, y);
        if (height != null) {
            heights.add(height);
        }
    }

    List<Integer> getAdjacentLocations(int x, int y) {
        List<Integer> heights = new ArrayList<>();
        addNonNullHeight(x, y - 1, heights);
        addNonNullHeight(x, y + 1, heights);
        addNonNullHeight(x - 1, y, heights);
        addNonNullHeight(x + 1, y, heights);

        return heights;
    }

    boolean getBasinSize(int x, int y, AtomicInteger basinSize) {
        // up down left right

        Integer height = getHeight(x, y);
        //System.out.println(x + ", " + y + ": " + height);

        if (height == null || height == 9) {
            return false;
        }

        basinSize.incrementAndGet();

        // TODO: no idea how to do this.
        if (!getBasinSize(x, y - 1, basinSize)) {
            return false;
        }
        if (!getBasinSize(x, y + 1, basinSize)) {
            return false;
        }
//        if (getBasinSize(x - 1, y, basinSize)) {
//            return false;
//        }
//        if (getBasinSize(x + 1, y, basinSize)) {
//            return false;
//        }

        return true;

    }

    Heightmap(int w, int h) {
        this.w = w;
        this.h = h;
    }
}

public class SmokeBasin {
    public static void main(String[] args) {
        Path path = Paths.get("smoke_basin_input");

        try {
            List<String> lines = Files.readAllLines(path);

            // the usage of Optional in this program is definitely unnecessary but I just wanted to try it out.
            Optional<Heightmap> optionalHeightmap = Optional.empty();
            boolean firstIteration = true;
            for (String line : lines) {
                if (firstIteration) {
                    optionalHeightmap = Optional.of(new Heightmap(line.length(), lines.size()));
                    firstIteration = false;
                }

                for (char character : line.toCharArray()) {
                    optionalHeightmap.get().heights.add(Character.getNumericValue(character));
                }
            }

            // TODO: input is temporarily replaced
            final AtomicInteger testBasinSize = new AtomicInteger();
            optionalHeightmap.get().getBasinSize(0, 0, testBasinSize);
            System.out.println(testBasinSize.get());

            if (true) {
                return;
            }

            optionalHeightmap.ifPresent(heightmap -> {
                int lowPointsRiskLevelsSum = 0;
                List<Integer> basinSizes = new ArrayList<>();
                for (int y = 0; y < heightmap.h; y++) {
                    for (int x = 0; x < heightmap.w; x++) {
                        int height = heightmap.getHeight(x, y);
                        List<Integer> adjacentLocations = heightmap.getAdjacentLocations(x, y);

                        boolean isLowPoint = adjacentLocations.stream().allMatch(adjacentLocation -> height < adjacentLocation);

                        if (isLowPoint) {
                            int riskLevel = 1 + height;
                            lowPointsRiskLevelsSum += riskLevel;

                            final AtomicInteger basinSize = new AtomicInteger();
                            heightmap.getBasinSize(x, y, basinSize);
                            basinSizes.add(basinSize.get());
                        }
                    }
                }
                System.out.println("First half answer: " + lowPointsRiskLevelsSum);
                Collections.sort(basinSizes);

                System.out.println(basinSizes.get(0) + " and " + basinSizes.get(1));

                int threeLargestBasinsSizesProduct = basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
