package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheTreacheryOfWhales {
    public static void main(String[] args) {
        File file = new File("the_treachery_of_whales_input");

        try {
            FileReader reader = new FileReader(file);

            char[] charBuffer = new char[(int) file.length()];
            int charsRead = reader.read(charBuffer);

            if (charsRead != charBuffer.length) {
                System.err.println("not all characters read!");
                return;
            }

            String input = new String(charBuffer);
            String[] crabSubmarineStrings = input.split(",");

            int[] crabSubmarines = new int[crabSubmarineStrings.length];
            for (int i = 0; i < crabSubmarineStrings.length; i++) {
                crabSubmarines[i] = Integer.parseInt(crabSubmarineStrings[i]);
            }

            List<Integer> usedFuelGroups = new ArrayList<>();

            for (int horizontalPositionToAlignTo = 0; horizontalPositionToAlignTo < 1000; horizontalPositionToAlignTo++) {
                int fuelUsed = 0;
                for (int submarine : crabSubmarines) {
                    // If you want the first half answer,
                    // comment the following...
                    int adder = 1;
                    for (int j = 0; j < Math.abs(submarine - horizontalPositionToAlignTo); j++) {
                        fuelUsed += adder;
                        adder++;
                    }

                    // ...and uncomment this
                    // fuelUsed += Math.abs(submarine - horizontalPositionToAlignTo);
                }
                usedFuelGroups.add(fuelUsed);
            }

            Collections.sort(usedFuelGroups);

            System.out.println("First or second half (see comment above) answer: " + usedFuelGroups.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
