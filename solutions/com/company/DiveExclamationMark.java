package com.company;

import java.io.*;

public class DiveExclamationMark {
    public static void main(String[] args) {
        partOne();
        partTwo();
    }

    static void partTwo() {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        File file = new File("dive_exclamation_mark_input");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] command_and_argument = line.split(" ");
                String command = command_and_argument[0];
                int argument = Integer.parseInt(command_and_argument[1]);

                switch (command) {
                    case "forward":
                        horizontalPosition += argument;
                        depth += aim * argument;
                        break;
                    case "down":
                        aim += argument;
                        break;
                    case "up":
                        aim -= argument;
                        break;
                }
            }

            System.out.println("Second half answer: " + horizontalPosition * depth);
        } catch (FileNotFoundException e) {
            System.err.println("The file was not found!");
        } catch (IOException e) {
            System.err.println("There was an error reading the file!");
        }
    }

    static void partOne() {
        int horizontalPosition = 0;
        int depth = 0;

        File file = new File("dive_exclamation_mark_input");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] command_and_argument = line.split(" ");
                String command = command_and_argument[0];
                int argument = Integer.parseInt(command_and_argument[1]);

                switch (command) {
                    case "forward":
                        horizontalPosition += argument;
                        break;
                    case "down":
                        depth += argument;
                        break;
                    case "up":
                        depth -= argument;
                        break;
                }
            }

            System.out.println("First half answer: " + horizontalPosition * depth);
        } catch (FileNotFoundException e) {
            System.err.println("The file was not found!");
        } catch (IOException e) {
            System.err.println("There was an error reading the file!");
        }
    }
}
