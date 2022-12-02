package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lanternfish {
    public static void main(String[] args) {
        File file = new File("lanternfish_input");

        try {
            FileReader reader = new FileReader(file);

            char[] charBuffer = new char[(int) file.length()];

            int charsRead = reader.read(charBuffer);

            if (charsRead != file.length()) {
                System.err.println("wasn't able to read all characters");
                return;
            }

            String input = new String(charBuffer);
            String[] lanternfishSchoolStrings = input.split(",");
            List<Integer> lanternfishSchool = new ArrayList<>();

            for (String lanternfishString : lanternfishSchoolStrings) {
                int lanternfish = Integer.parseInt(lanternfishString);
                lanternfishSchool.add(lanternfish);
            }

            final int days = 80;
            for (int day = 0; day < days; day++) {
                int lanternfishIndex = 0;
                int lanternfishToAdd = 0;
                for (Object lanternfishObject : lanternfishSchool) {
                    int lanternfish = (int) lanternfishObject;

                    if (lanternfish == 0) {
                        lanternfish = 7;
                        lanternfishToAdd += 1;
                    }

                    // prefix -- is important here!
                    // no postfix operator should be used here.
                    lanternfishSchool.set(lanternfishIndex, --lanternfish);

                    lanternfishIndex++;
                }

                for (; lanternfishToAdd > 0; lanternfishToAdd--) {
                    lanternfishSchool.add(8);
                }

                System.out.print("After day " + (day + 1) + ": ");
                lanternfishSchool.forEach(lanternfish -> {
                    System.out.print(lanternfish + ",");
                });
                System.out.println();
            }
            System.out.println("First half answer: " + lanternfishSchool.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
