package FuncaoMain;

import model.Character;
import model.Player;
import model.Enemy;
import battle.BattleManager;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        questions_system questionGenerator = new questions_system();
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

        
    for (Enemy enemy : enemies) {
        if (!player.isAlive()) break;

        System.out.println("\n==================================================");
        System.out.println(">>> Próximo Aluno: " + enemy.getName());
        System.out.println("    " + enemy.getDescription());
        System.out.println("    Dificuldade: " + enemy.getDifficulty().toUpperCase());
        System.out.println("==================================================\n");
        System.out.print("[Pressione ENTER para iniciar a batalha...]");
        scanner.nextLine();

        BattleManager battle = new BattleManager(player, enemy, questionGenerator, scanner);
        boolean won = battle.playBattle();

        if (won) {
            battlesWon++;
            // Para simplificar, assumimos que ganhaste e acertaste algumas (o BattleManager é que devia incrementar, mas faremos a versão simples da Main)
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║   VITÓRIA! " + enemy.getName() + " FOI DERROTADO(A)!   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.println("Score atual: " + player.getScore() + " pontos");
        
            // Requisito: Curar entre batalhas
            player.heal(30);
            System.out.println("A tua Genialidade inspirou-te. Recuperaste 30 de HP! (HP atual: " + player.getHp() + ")");
        } else {
            break;
        }
    }

        // Tela final
        showFinalScreen(player, battlesWon, enemies.length);
        scanner.close();
    }

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

    private static void showFinalScreen(Player player, int battlesWon, int totalBattles) {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        if (battlesWon == totalBattles) {
            System.out.println("║            ★★★ VOCÊ É O CAMPEÃO! ★★★             ║");
        } else {
            System.out.println("║                   GAME OVER                      ║");
        }
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf("║ Jogador: %-40s║%n", player.getName());
        System.out.printf("║ Batalhas vencidas: %d/%d                           ║%n", battlesWon, totalBattles);
        System.out.printf("║ Score final: %-36d║%n", player.getScore());
        System.out.println("║                                                  ║");
        System.out.println("║ ESTATÍSTICAS DA PARTIDA:                         ║");
        System.out.printf("║ - Acertos: %-38d║%n", player.getPerguntasCertas());
        System.out.printf("║ - Erros: %-40d║%n", player.getPerguntasErradas());
        System.out.println("╚══════════════════════════════════════════════════╝");
    }
}