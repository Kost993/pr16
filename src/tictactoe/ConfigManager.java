package tictactoe;

import java.io.*;

public class ConfigManager {
    private static final String CONFIG_FILE = "config.txt";

    public static void saveConfig() {
        try (FileWriter w = new FileWriter(CONFIG_FILE)) {
            w.write(Game.size + "\n" + Game.playerX + "\n" + Game.playerO + "\n");
        } catch (IOException e) {
            System.out.println("Помилка збереження конфігурації.");
        }
    }

    public static void loadConfig() {
        try (BufferedReader r = new BufferedReader(new FileReader(CONFIG_FILE))) {
            Game.size = Integer.parseInt(r.readLine());
            Game.playerX = r.readLine();
            Game.playerO = r.readLine();
        } catch (Exception e) {
        }
    }
}
