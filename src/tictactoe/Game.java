package tictactoe;

import java.util.Scanner;

public class Game {
    private static char[][] game;
    private static Scanner scanner = new Scanner(System.in);
    public static int size = 3;
    public static String playerX = "Гравець X";
    public static String playerO = "Гравець O";

    public static void gameMenu() {
        boolean run = true;
        while (run) {
            System.out.println("\nГоловне меню:");
            System.out.println("1. Грати");
            System.out.println("2. Налаштування");
            System.out.println("3. Статистика");
            System.out.println("4. Вихід");
            String input = scanner.nextLine();

            switch (input) {
                case "1" -> startGame();
                case "2" -> changeSettings();
                case "3" -> StatsManager.showStats();
                case "4" -> run = false;
                default -> System.out.println("Невірний вибір.");
            }
        }
    }

    private static void changeSettings() {
        System.out.println("Оберiть розмiр гри: 3 (1), 5 (2), 7 (3), 9 (4)");
        switch (scanner.nextLine()) {
            case "1" -> size = 3;
            case "2" -> size = 5;
            case "3" -> size = 7;
            case "4" -> size = 9;
            default -> System.out.println("Невірний вибір.");
        }
        System.out.print("Iм'я гравця X: ");
        playerX = scanner.nextLine();
        System.out.print("Iм'я гравця O: ");
        playerO = scanner.nextLine();
        ConfigManager.saveConfig();
    }

    private static void startGame() {
        game = new char[size * 2 - 1][size * 2 - 1];
        initBoard();
        char player = 'X';
        int moves = 0;

        while (true) {
            printBoard();
            if (!playerMove(player)) continue;
            moves++;
            if (checkWin(player)) {
                printBoard();
                System.out.println("Переможець: " + (player == 'X' ? playerX : playerO));
                StatsManager.saveStat((player == 'X' ? playerX : playerO));
                return;
            }
            if (moves == size * size) {
                System.out.println("Нiчия");
                StatsManager.saveStat("Нічия");
                return;
            }
            player = (player == 'X') ? 'O' : 'X';
        }
    }

    private static void initBoard() {
        for (int i = 0; i < game.length; i++)
            for (int j = 0; j < game.length; j++)
                game[i][j] = (i % 2 == 0 && j % 2 == 0) ? ' ' : (i % 2 == 0 ? '|' : (j % 2 == 0 ? '-' : '+'));
    }

    private static void printBoard() {
        for (char[] row : game) {
            for (char cell : row) System.out.print(cell + " ");
            System.out.println();
        }
    }

    private static boolean playerMove(char player) {
        System.out.println("Хiд: " + (player == 'X' ? playerX : playerO) + " (" + player + ")");
        System.out.print("Введiть рядок (1-" + size + "): ");
        int row = getIntInput();
        System.out.print("Введiть стовпець (1-" + size + "): ");
        int col = getIntInput();

        if (row < 1 || row > size || col < 1 || col > size || game[(row - 1) * 2][(col - 1) * 2] != ' ') {
            System.out.println("Неправильний хiд.");
            return false;
        }
        game[(row - 1) * 2][(col - 1) * 2] = player;
        return true;
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    private static boolean checkWin(char player) {
        for (int i = 0; i < size; i++) {
            boolean row = true, col = true;
            for (int j = 0; j < size; j++) {
                if (game[i * 2][j * 2] != player) row = false;
                if (game[j * 2][i * 2] != player) col = false;
            }
            if (row || col) return true;
        }
        boolean diag1 = true, diag2 = true;
        for (int i = 0; i < size; i++) {
            if (game[i * 2][i * 2] != player) diag1 = false;
            if (game[i * 2][(size - 1 - i) * 2] != player) diag2 = false;
        }
        return diag1 || diag2;
    }
}
