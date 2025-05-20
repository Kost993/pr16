package tictactoe;

import java.io.*;
import java.util.Date;

public class StatsManager {
    private static final String STATS_FILE = "stats.txt";

    public static void saveStat(String winner) {
        try (FileWriter w = new FileWriter(STATS_FILE, true)) {
            w.write(new Date().toString() + " | " + winner + " | " + Game.size + "x" + Game.size + "\n");
        } catch (IOException e) {
            System.out.println("Не вдалося зберегти статистику.");
        }
    }

    public static void showStats() {
        try (BufferedReader r = new BufferedReader(new FileReader(STATS_FILE))) {
            String line;
            System.out.println("Історія ігор:");
            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Немає статистики.");
        }
    }
}
