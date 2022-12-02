package com.company;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

class Buffer {
    int[] buf;
    int index;

    Buffer() {
        this.buf = new int[3];
        this.index = 0;
    }

    // Adds the element to the buffer unless the buffer is already full (has size 3).
    void add(int element) {
        if (this.index != 3) {
            this.buf[this.index++] = element;
        }
    }

    int length() {
        return this.index;
    }
}

public class SonarSweep {
    public static void main(String[] args) {
        File file = new File("sonar_sweep_input");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            // part one
            int previousDepthMeasurement = 0;
            int depthMeasurementIncreases = -1;

            // part two
            List<Buffer> threeMeasurementSlidingWindows = new ArrayList<Buffer>();

            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int depthMeasurement = Integer.parseInt(line);

                    // part one
                    if (depthMeasurement > previousDepthMeasurement) {
                        depthMeasurementIncreases += 1;
                    }
                    previousDepthMeasurement = depthMeasurement;

                    // part two
                    // create a new group on every iteration and fill those up
                    Buffer newThreeMeasurementSlidingWindow = new Buffer();
                    threeMeasurementSlidingWindows.add(newThreeMeasurementSlidingWindow);
                    for (Buffer threeMeasurementSlidingWindow : threeMeasurementSlidingWindows) {
                        threeMeasurementSlidingWindow.add(depthMeasurement);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("First half answer: " + depthMeasurementIncreases);

            int x = 0;
            System.out.println("x++: " + x++); // the postfix increment operator increases `x` AFTER this statement!
            System.out.println("x += 1: " + (x += 1)); // `x` is increased IN this statement!
            System.out.println("++x: " + ++x); // the prefix increment operator increases `x` IN this statement!

            // omit unfinished buffers
            threeMeasurementSlidingWindows.removeIf(threeMeasurementSlidingWindow -> (threeMeasurementSlidingWindow.length() != 3));

            // part two
            int measurementSumIncreases = 0;
            for (int i = 0; i < threeMeasurementSlidingWindows.size(); i++) {
                try {
                    Buffer threeMeasurementSlidingWindowLeft = threeMeasurementSlidingWindows.get(i);
                    Buffer threeMeasurementSlidingWindowRight = threeMeasurementSlidingWindows.get(i + 1);

                    // there are two inbuilt semantically equal ways to sum int arrays:
                    int sum1 = Arrays.stream(threeMeasurementSlidingWindowLeft.buf).sum();
                    int sum2 = IntStream.of(threeMeasurementSlidingWindowRight.buf).sum();

                    if (sum2 > sum1) {
                        measurementSumIncreases += 1;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // we couldn't fill all measurement sliding windows
                }
            }

            System.out.println("Second half answer: " + measurementSumIncreases);
        } catch (Exception err) {
            System.err.println(err);
        }
    }
}
