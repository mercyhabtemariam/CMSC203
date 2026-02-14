package cmsc203;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GradeCalculator {

    /*
     * Class: CMSC203
     * Instructor: Prof. Ashique Tanveer
     * Description: Grade Calculator reads config + student scores, computes overall average and letter grade,
     *              prints summary and writes report file.
     * Due: (put your due date)
     * Platform/compiler: Eclipse (Java)
     * I pledge that I have completed the programming assignment independently.
     * I have not copied the code from a student or any source. I have not given my code to any student.
     * Print your Name here: mercy habtemariam
     */

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   CMSC203 Project 1 - Grade Calculator");
        System.out.println("========================================\n");

        String configFileName = "gradeconfig.txt";
        String inputFileName  = "grades_input.txt";
        String outputFileName = "grades_report.txt";

        // Default configuration - 3 categories only
        boolean defaultUsed = false;
        String courseName = "CMSC203 Computer Science I";

        String c1 = "Projects", c2 = "Quizzes", c3 = "Exams";
        int w1 = 40, w2 = 30, w3 = 30;

        System.out.println("Loading configuration from " + configFileName + " ...");

        // Try reading config (must be 3 categories)
        try {
            Scanner cfg = new Scanner(new File(configFileName));

            if (cfg.hasNextLine()) {
                String line = cfg.nextLine().trim();
                if (line.length() > 0) courseName = line;
            }

            int n = -1;
            if (cfg.hasNextInt()) n = cfg.nextInt();

            boolean ok = (n == 3);

            if (ok) {
                // category1
                if (cfg.hasNext()) c1 = cfg.next();
                else ok = false;
                if (cfg.hasNextInt()) w1 = cfg.nextInt();
                else ok = false;

                // category2
                if (cfg.hasNext()) c2 = cfg.next();
                else ok = false;
                if (cfg.hasNextInt()) w2 = cfg.nextInt();
                else ok = false;

                // category3
                if (cfg.hasNext()) c3 = cfg.next();
                else ok = false;
                if (cfg.hasNextInt()) w3 = cfg.nextInt();
                else ok = false;

                if (ok && (w1 + w2 + w3 != 100)) ok = false;
            }

            cfg.close();

            if (!ok) {
                defaultUsed = true;
                courseName = "CMSC203 Computer Science I";
                c1 = "Projects"; c2 = "Quizzes"; c3 = "Exams";
                w1 = 40; w2 = 30; w3 = 30;
                System.out.println("Configuration invalid. Using DEFAULT configuration.");
            } else {
                System.out.println("Configuration loaded successfully.");
            }

        } catch (FileNotFoundException e) {
            defaultUsed = true;
            System.out.println("Configuration file missing. Using DEFAULT configuration.");
        }

        System.out.println("\nUsing input file: " + inputFileName);
        System.out.println("Using output file: " + outputFileName);
        System.out.println("\nReading student scores...\n");

        // Open input file
        Scanner in = null;
        try {
            in = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Input file '" + inputFileName + "' not found or unreadable.");
            System.out.println("Program exiting gracefully.");
            return;
        }

        // Read student name
        String first = in.hasNextLine() ? in.nextLine().trim() : "";
        String last  = in.hasNextLine() ? in.nextLine().trim() : "";

        System.out.println("Student: " + first + " " + last);
        System.out.println("Course: " + courseName + "\n");
        System.out.println("Category Results:");

        StringBuilder report = new StringBuilder();
        report.append("Course: ").append(courseName).append("\n");
        report.append("Student: ").append(first).append(" ").append(last).append("\n\n");
        report.append("Category Results:\n");

        double overall = 0.0;

        // Read categories in a loop
        while (in.hasNextLine()) {
            String cat = in.nextLine().trim();
            if (cat.length() == 0) continue;

            if (!in.hasNextLine()) break;
            String countLine = in.nextLine().trim();

            int numScores;
            try {
                numScores = Integer.parseInt(countLine);
            } catch (NumberFormatException ex) {
                System.out.println("  ERROR: Bad score count for " + cat + " (skipping).");
                if (in.hasNextLine()) in.nextLine(); // skip scores line
                continue;
            }

            if (!in.hasNextLine()) break;
            String scoresLine = in.nextLine().trim();

            if (numScores <= 0) {
                System.out.println("  ERROR: Number of scores must be > 0 for " + cat + " (skipping).");
                continue;
            }

            //  FIXED: Read EXACTLY numScores scores (no arrays)
            Scanner s = new Scanner(scoresLine);
            double sum = 0.0;
            int read = 0;

            while (s.hasNext() && read < numScores) {
                if (s.hasNextDouble()) {
                    sum += s.nextDouble();
                    read++;
                } else {
                    s.next(); // skip bad token
                }
            }
            s.close();

            if (read < numScores) {
                System.out.println("  ERROR: Not enough valid scores for " + cat +
                                   " (expected " + numScores + ", got " + read + ") (skipping).");
                continue;
            }

            // FIXED: average uses numScores (not read-all)
            double avg = sum / numScores;

            // Match category to one of the 3 configured categories
            int weight = -1;
            if (cat.equalsIgnoreCase(c1)) weight = w1;
            else if (cat.equalsIgnoreCase(c2)) weight = w2;
            else if (cat.equalsIgnoreCase(c3)) weight = w3;

            if (weight < 0) {
                System.out.println("  ERROR: Category '" + cat + "' not in configuration (skipping).");
                continue;
            }

            overall += avg * (weight / 100.0);

            System.out.printf("  %s (%d%%): average = %.2f%n", cat, weight, avg);
            report.append(String.format("  %s (%d%%): average = %.2f%n", cat, weight, avg));
        }

        in.close();

        // Keyboard input validation loop
        Scanner keyboard = new Scanner(System.in);
        String choice;
        do {
            System.out.print("\nApply +/- grading? (Y/N): ");
            choice = keyboard.nextLine().trim();
        } while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N"));

        boolean plusMinus = choice.equalsIgnoreCase("Y");

        // Base letter grade
        String base;
        if (overall >= 90.0) base = "A";
        else if (overall >= 80.0) base = "B";
        else if (overall >= 70.0) base = "C";
        else if (overall >= 60.0) base = "D";
        else base = "F";

        String finalGrade = base;

        // +/- grading simple cutoffs you can change
        if (plusMinus && !base.equals("F")) {
            double decimal = overall - (int) overall;
            if (decimal >= 0.70) finalGrade = base + "+";
            else if (decimal <= 0.30) finalGrade = base + "-";
        }

        System.out.printf("%nOverall numeric average: %.2f%n", overall);
        System.out.println("Base letter grade: " + base);
        System.out.println("Final letter grade: " + finalGrade);

        report.append(String.format("%nOverall numeric average: %.2f%n", overall));
        report.append("Base letter grade: ").append(base).append("\n");
        report.append("Final letter grade: ").append(finalGrade).append("\n");
        report.append("Default configuration used: ").append(defaultUsed ? "YES" : "NO").append("\n");

        // Write report file
        try {
            PrintWriter out = new PrintWriter(outputFileName);
            out.print(report.toString());
            out.close();
            System.out.println("\nSummary written to " + outputFileName);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Could not write output file: " + outputFileName);
        }

        System.out.println("Program complete. Goodbye!");
        keyboard.close();
    }
}
