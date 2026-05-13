package FuncaoMain;

import model.Character;
import model.Player;
import model.Enemy;
import battle.BattleManager;
import questoes.QuestionBank;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QuestionBank questionBank = new QuestionBank();
        showWelcome();

        // Seleção de personagem
        Character selectedCharacter = selectCharacter(scanner);
        System.out.print("\nDigite o nome do seu jogador: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Jogador";

        Player player = new Player(playerName, selectedCharacter);
        System.out.println("\nBem-vindo, " + player.getName() + "! Prepare-se para a batalha!\n");

        // Sequência de inimigos (dificuldade crescente)
        Enemy[] enemies = {
            new Enemy("Mariana",  "Fez kumon na infância",       50,  10, "easy"),
            new Enemy("Larissa",    "Sabe nomear todos os estados de todos os paises",                70,  15, "medium"),
            new Enemy("Lucas", "Resolveu a primeira integral com 5 meses",        100, 20, "hard")
        };

        int battlesWon = 0;

        // Loop de batalhas
        for (Enemy enemy : enemies) {
            if (!player.isAlive()) break;

            System.out.println("\n>>> Próximo Aluno: " + enemy.getName());
            System.out.println("    " + enemy.getDescription());
            System.out.print("\n[Pressione ENTER para iniciar a batalha...]");
            scanner.nextLine();

            BattleManager battle = new BattleManager(player, enemy, questionBank, scanner);
            boolean won = battle.playBattle();

            if (won) {
                battlesWon++;
                System.out.println("\nScore atual: " + player.getScore() + " pontos");
            } else {
                break;
            }
        }

        // Tela final
        showFinalScreen(player, battlesWon, enemies.length);
        scanner.close();
    }

    // Tela de boas-vindas
    private static void showWelcome() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║              Pequenos Gênios             ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("\nLuciano Huck: Bem vindo ao pequenos gênios!");
        System.out.println("Aqui, seu único objetivo é batalhar com outros pequenos gênios .");
        System.out.println("Caso você acerte corretamente, você causará dano nos seus oponentes");
        System.out.println("Caso contrário, o dano virá em você. Ah, e se você ficar sem vida, você perde!\n");
    }

    private static Character selectCharacter(Scanner scanner) {
        Character mid = new Character(
                "Aluno de humanas",
                Character.CharacterClass.MID,
                3, 5, 120,
                "Historiador nato"
        );

        Character nerd = new Character(
                "Aluno de exatas",
                Character.CharacterClass.NERD,
                5, 10, 150,
                "Calculadora humana"
        );

        System.out.println("=== ESCOLHA SUA AREA DO CONHECIMENTO ===\n");
        System.out.println("1) " + mid);
        System.out.println("   " + mid.getDescription());
        System.out.println("\n2) " + nerd);
        System.out.println("   " + nerd.getDescription());

        System.out.print("\nEscolha (1 ou 2): ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("2")) {
            System.out.println("\nArea escolhida: " + nerd.getName());
            return nerd;
        } else {
            System.out.println("\nArea escolhida: " + mid.getName());
            return mid;
        }
    }

    // Tela final com estatísticas
    private static void showFinalScreen(Player player, int battlesWon, int totalBattles) {
        System.out.println("\n╔══════════════════════════════════════════╗");
        if (battlesWon == totalBattles) {
            System.out.println("║       ★★★ VOCÊ É O CAMPEÃO! ★★★        ║");
        } else {
            System.out.println("║          FIM DE JOGO                    ║");
        }
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║  Jogador:  %-30s║%n", player.getName());
        System.out.printf("║  Batalhas vencidas: %d/%d                  ║%n", battlesWon, totalBattles);
        System.out.printf("║  Score final: %-27d║%n", player.getScore());
        System.out.println("╚══════════════════════════════════════════╝");
    }
}
