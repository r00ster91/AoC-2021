package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BinaryDiagnostic {
    public static void main(String[] args) {
        try {
            Path path = Paths.get("binary_diagnostic_input");
            List<String> lines = Files.readAllLines(path);

            // determine the required array length with the first line
            int length = lines.get(0).length();

            int powerConsumption = getPowerConsumption(length, lines);
            System.out.println("First half answer: " + powerConsumption);

            int lifeSupportRating = verifyLifeSupportRating(length, lines);
            System.out.println("Second half answer: " + lifeSupportRating);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getPowerConsumption(int length, List<String> lines) {
        Binary gammaRate = new Binary();

        for (int i = 0; i < length; i++) {
            int zeroCount = 0;
            int oneCount = 0;
            for (String line : lines) {
                int digit = Integer.parseInt("" + line.charAt(i));
                if (digit == 0) {
                    zeroCount += 1;
                } else if (digit == 1) {
                    oneCount += 1;
                } else {
                    System.err.println("Unexpected: " + digit);
                }
            }

            if (zeroCount > oneCount) {
                gammaRate.add(0);
            } else {
                gammaRate.add(1);
            }
        }

        // to get the epsilon rate, simply invert the gamma rate
        Binary andOperand = new Binary();
        for (int i = 0; i < length; i++) {
            andOperand.add(1);
        }

        int epsilonRate = (~gammaRate.decimal & andOperand.decimal);

        return gammaRate.decimal * epsilonRate;
    }

    private static int verifyLifeSupportRating(int length, List<String> lines) {
        List<String> lines1 = new ArrayList<>(lines);
        int oxygenGeneratorRating = 0;
        for (int i = 0; i < length; i++) {
            Binary bits = new Binary();
            for (String line : lines1) {
                int currentBit = Character.getNumericValue(line.charAt(i));
                bits.add(currentBit);
            }

            // you could also use `char` here
            Character charToCheckFor;
            if (bits.zeroCount == bits.oneCount || bits.oneCount > bits.zeroCount) {
                charToCheckFor = '0';
            } else {
                charToCheckFor = '1';
            }

            int lineIndex = i;
            lines1.removeIf(line -> (line.charAt(lineIndex) == charToCheckFor));

            if (lines1.size() == 1) {
                oxygenGeneratorRating = Integer.parseInt(lines1.get(0), 2);
            }
        }

        List<String> lines2 = new ArrayList<>(lines);
        int CO2ScrubberRating = 0;
        for (int i = 0; i < length; i++) {
            Binary bits = new Binary();
            for (String line : lines2) {
                int currentBit = Character.getNumericValue(line.charAt(i));
                bits.add(currentBit);
            }

            // you could also use `char` here
            Character charToCheckFor;
            if (bits.zeroCount == bits.oneCount || bits.oneCount > bits.zeroCount) {
                charToCheckFor = '1';
            } else {
                charToCheckFor = '0';
            }

            int lineIndex = i;
            lines2.removeIf(line -> (line.charAt(lineIndex) == charToCheckFor));

            if (lines2.size() == 1) {
                CO2ScrubberRating = Integer.parseInt(lines2.get(0), 2);
            }
        }

        return oxygenGeneratorRating * CO2ScrubberRating;
    }
}

class Binary {
    int decimal;
    int zeroCount;
    int oneCount;

    Binary() {
        this.decimal = 0;
        this.zeroCount = 0;
        this.oneCount = 0;
    }

    void add(int zero_or_one) {
        this.decimal <<= 1;
        if (zero_or_one == 0) {
            this.zeroCount += 1;
        } else if (zero_or_one == 1) {
            this.decimal ^= 1;
            this.oneCount += 1;
        }
    }
}
