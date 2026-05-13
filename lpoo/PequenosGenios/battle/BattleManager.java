package battle;

import model.Player;
import model.Enemy;
import questoes.Question;
import questoes.QuestionBank;

import java.util.Scanner;

public class BattleManager {

    private final Player player;
    private final Enemy enemy;
    private final QuestionBank questionBank;
    private final Scanner scanner;
    private int roundNumber;

    public BattleManager(Player player, Enemy enemy, QuestionBank questionBank, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.questionBank = questionBank;
        this.scanner = scanner;
        this.roundNumber = 1;
    }

    // Executa a batalha completa entre jogador e inimigo
    public boolean playBattle() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         BATALHA INICIADA!            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  %s  vs  %s%n", player.getName(), enemy.getName());
        System.out.println("║  " + enemy.getDescription());
        System.out.println("╚══════════════════════════════════════╝\n");

        pressEnterToContinue();

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n--- Rodada " + roundNumber + " ---");

            Question question = questionBank.getRandomQuestion(enemy.getDifficulty());
            Round round = new Round(player, enemy, question, scanner);
            round.execute();

            roundNumber++;
            pressEnterToContinue();
        }

        return showBattleResult();
    }

    // Exibe o resultado da batalha e retorna true se o jogador venceu
    private boolean showBattleResult() {
        System.out.println("\n╔══════════════════════════════════════╗");
        if (player.isAlive()) {
            System.out.println("║         ★ VITÓRIA! ★                 ║");
            System.out.printf("║  Você derrotou %s!%n", enemy.getName());
        } else {
            System.out.println("║         ✖ DERROTA...                 ║");
            System.out.printf("║  %s te derrotou!%n", enemy.getName());
        }
        System.out.println("╚══════════════════════════════════════╝");
        return player.isAlive();
    }

    private void pressEnterToContinue() {
        System.out.print("\n[Pressione ENTER para continuar...]");
        scanner.nextLine();
    }
}
