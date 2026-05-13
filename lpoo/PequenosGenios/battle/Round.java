package battle;

import model.Player;
import model.Enemy;
import questoes.Question;
import questoes.MultipleChoiceQuestion;
import questoes.TrueFalseQuestion;

import java.util.Scanner;

public class Round {

    private final Player player;
    private final Enemy enemy;
    private final Question question;
    private final Scanner scanner;
    private boolean playerAnsweredCorrectly;

    public Round(Player player, Enemy enemy, Question question, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.question = question;
        this.scanner = scanner;
    }

    // Executa uma rodada completa
    public void execute() {
        showStatus();
        showQuestion();
        String answer = getPlayerAnswer();
        playerAnsweredCorrectly = question.checkAnswer(answer);
        applyEffects();
    }

    // Exibe o status de HP dos personagens
    private void showStatus() {
        System.out.println("\n========================================");
        System.out.printf("  %-20s HP: %d/%d%n", player.getName(), player.getHp(), player.getMaxHp());
        System.out.printf("  %-20s HP: %d/%d%n", enemy.getName(), enemy.getHp(), enemy.getMaxHp());
        System.out.println("========================================\n");
    }

    // Exibe a pergunta no console
    private void showQuestion() {
        System.out.println(">>> PERGUNTA:\n");
        System.out.println(question);

        if (question instanceof TrueFalseQuestion) {
            System.out.println("Responda: true ou false");
        }
    }

    // Lê a resposta do jogador
    private String getPlayerAnswer() {
        System.out.print("\nSua resposta: ");
        return scanner.nextLine().trim();
    }

    // Aplica os efeitos da resposta correta ou errada
    private void applyEffects() {
        System.out.println();
        if (playerAnsweredCorrectly) {
            int damage = player.getAttackPower();
            int points = calculatePoints();
            enemy.takeDamage(damage);
            player.addScore(points);
            System.out.println("✔ CORRETO! Você causou " + damage + " de dano em " + enemy.getName() + "!");
            System.out.println("  +" + points + " pontos!");
        } else {
            int damage = enemy.getAttackPower();
            player.takeDamage(damage);
            System.out.println("✘ ERRADO! " + enemy.getName() + " causou " + damage + " de dano em você!");

            // Mostra a resposta correta
            showCorrectAnswer();
        }
    }

    // Mostra a resposta correta quando o jogador erra
    private void showCorrectAnswer() {
        if (question instanceof MultipleChoiceQuestion mcq) {
            int idx = mcq.getCorrectIndex();
            String[] opts = mcq.getOptions();
            char letter = (char) ('A' + idx);
            System.out.println("  Resposta correta: " + letter + ") " + opts[idx]);
        } else if (question instanceof TrueFalseQuestion tfq) {
            System.out.println("  Resposta correta: " + tfq.getCorrectAnswer());
        }
    }

    // Calcula os pontos baseado na dificuldade
    private int calculatePoints() {
        return switch (question.getDifficulty().toLowerCase()) {
            case "easy"   -> 10;
            case "medium" -> 20;
            case "hard"   -> 30;
            default       -> 10;
        };
    }

    public boolean playerAnsweredCorrectly() { return playerAnsweredCorrectly; }
}
