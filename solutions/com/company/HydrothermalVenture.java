package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Vent {
    int x1;
    int y1;

    int x2;
    int y2;

    Vent(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;
    }
}

class Grid {
    int[] cells;
    private int w;
    private int h;

    Grid(int w, int h) {
        // our array is guaranteed to be zero-initialized
        // by the language spec:
        // https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5
        cells = new int[w * h];
        this.w = w;
        this.h = h;
    }

    void markCell(int x, int y) {
        cells[x + w * y]++;
    }
}

public class HydrothermalVenture {
    public static void main(String[] args) {
        File file = new File("hydrothermal_venture_input");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            List vents = new ArrayList<Vent>();

            int maxWidth = 0;
            int maxHeight = 0;

            String line;
            while ((line = reader.readLine()) != null) {
                String[] points = line.split(" -> ");

                String[] x1_y1 = points[0].split(",");
                int x1 = Integer.parseInt(x1_y1[0]);
                int y1 = Integer.parseInt(x1_y1[1]);

                String[] x2_y2 = points[1].split(",");
                int x2 = Integer.parseInt(x2_y2[0]);
                int y2 = Integer.parseInt(x2_y2[1]);

                if (x1 > maxWidth) {
                    maxWidth = x1;
                }
                if (y1 > maxHeight) {
                    maxHeight = y1;
                }
                if (x2 > maxWidth) {
                    maxWidth = x2;
                }
                if (y2 > maxHeight) {
                    maxHeight = y2;
                }

                vents.add(new Vent(x1, y1, x2, y2));
            }

            maxWidth++;
            maxHeight++;

            Grid grid = new Grid(maxWidth, maxHeight);

//            vents.clear();
//
//            vents.add(new Vent(1, 1, 3, 3));

            for (Object ventObject :
                    vents) {
                Vent vent = (Vent)ventObject;

//                if (!(vent.x1 == vent.x2 || vent.y1 == vent.y2)) {
//                    continue;
//                }

                int rangeXStart;
                int rangeXEnd;
                if (vent.x1 > vent.x2) {
                    rangeXStart = vent.x2;
                    rangeXEnd = vent.x1;
                } else {
                    rangeXStart = vent.x1;
                    rangeXEnd = vent.x2;
                }

                int rangeYStart;
                int rangeYEnd;
                if (vent.y1 > vent.y2) {
                    rangeYStart = vent.y2;
                    rangeYEnd = vent.y1;
                } else {
                    rangeYStart = vent.y1;
                    rangeYEnd = vent.y2;
                }

                for (int y = rangeYStart; y <= rangeYEnd; y++) {
                    for (int x = rangeXStart; x <= rangeXEnd; x++) {
                        grid.markCell(x, y);
                    }
                }
            }

            System.out.println(maxWidth);
            System.out.println(maxHeight);

            int i = 0;
            for (int cell: grid.cells) {
                if (cell == 0) {
                    System.out.print('.');
                } else {
                    System.out.print(cell);
                }

                i++;

                if (i % maxWidth == 0) {
                    System.out.println();
                }
            }

            int overlappingCellCount = 0;
            for (int cell: grid.cells) {
                if (cell >= 2) {
                    overlappingCellCount += 1;
                }
            }
            System.out.println("First half answer: " + overlappingCellCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
