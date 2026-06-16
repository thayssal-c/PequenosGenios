package battle;

import model.Player;
import model.Enemy;
import questoes.Question;
import questoes.TimedQuestion;
import FuncaoMain.questions_system;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class BattleManager {

    private final Player player;
    private final Enemy enemy;
    private final questions_system questionGenerator;
    private final Scanner scanner;
    private int roundNumber;
    private boolean abilityUsedThisBattle;
    private final List<SpecialAbility> availableAbilities;

    public BattleManager(Player player, Enemy enemy, questions_system questionGenerator, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.questionGenerator = questionGenerator;
        this.scanner = scanner;
        this.roundNumber = 1;
        this.abilityUsedThisBattle = false;
        
        this.availableAbilities = new ArrayList<>();
        this.availableAbilities.add(new HealAbility());
        this.availableAbilities.add(new ShieldAbility());
    }

    public boolean playBattle() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║         BATALHA INICIADA!            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.printf("║  %s  vs  %s%n", player.getName(), enemy.getName());
        System.out.println("║  " + enemy.getDescription());
        System.out.println("╚══════════════════════════════════════╝\n");

        pressEnterToContinue();

        while (player.isAlive() && enemy.isAlive()) {
            System.out.println("\n================================================================================");
            System.out.println("--- Rodada " + roundNumber + " ---");
            // INTERFACE MELHORADA: Barras de vida dinâmicas para o fator UAU na Entrega 3
            System.out.printf("Jogador -> %-12s %s %3d/%-3d HP | Escudo: %-2d | Score: %d%n", 
                    player.getName(), exibirBarraVida(player.getHp(), player.getMaxHp()), player.getHp(), player.getMaxHp(), player.getShield(), player.getScore());
            System.out.printf("Inimigo -> %-12s %s %3d/%-3d HP | Dificuldade: %s%n", 
                    enemy.getName(), exibirBarraVida(enemy.getHp(), enemy.getMaxHp()), enemy.getHp(), enemy.getMaxHp(), enemy.getDifficulty().toUpperCase());
            System.out.println("================================================================================");

            String tipoPergunta = (Math.random() < 0.5) ? "multiple_choice" : "true_false";
            Question question = questionGenerator.generateQuestion(enemy.getDifficulty(), tipoPergunta);

            exibirMenuTurno(question);

            System.out.println("\n" + question);
            
            if (question instanceof TimedQuestion) {
                int limite = ((TimedQuestion) question).getTimeLimitSeconds();
                System.out.println("⚠️ ATENÇÃO CRÍTICA: Pergunta com tempo limite de " + limite + " segundos!");
            }

            long startTime = System.currentTimeMillis();
            System.out.print("\nSua resposta: ");
            String answer = scanner.nextLine();
            long endTime = System.currentTimeMillis();
            
            long tempoGasto = (endTime - startTime) / 1000;
            boolean tempoEsgotado = false;

            if (question instanceof TimedQuestion) {
                int limite = ((TimedQuestion) question).getTimeLimitSeconds();
                if (tempoGasto > limite) {
                    tempoEsgotado = true;
                }
            }

            int danoDoJogador = player.getAttackPower();
            int danoDoInimigo = enemy.getAttackPower();
            int pontosDaRodada = 10;

            if (enemy.getDifficulty().equalsIgnoreCase("medium")) {
                danoDoJogador *= 1.2;
                danoDoInimigo *= 1.3;
                pontosDaRodada = 25;
            } else if (enemy.getDifficulty().equalsIgnoreCase("hard")) {
                danoDoJogador *= 1.5;
                danoDoInimigo *= 1.6;
                pontosDaRodada = 50;
            }

            // Avaliação do Turno com injeção das Estatísticas da Entrega Final
            if (question.checkAnswer(answer) && !tempoEsgotado) {
                if (player.isDoubleDamageActive()) {
                    danoDoJogador *= 2;
                    player.setDoubleDamageActive(false);
                    System.out.println("💥 ATAQUE CRÍTICO! Dano dobrado computado com sucesso!");
                }
                
                System.out.println("✨ RESPOSTA CORRETA! Você infligiu " + danoDoJogador + " de dano em " + enemy.getName());
                enemy.takeDamage(danoDoJogador); 
                
                // REGISTA O ACERTO NAS ESTATÍSTICAS
                player.incrementaAcerto();
                
                player.addScore(pontosDaRodada);
                System.out.println("🏆 +" + pontosDaRodada + " pontos adicionados ao seu placar!");
            } else {
                if (tempoEsgotado) {
                    System.out.println("⏰ TEMPO ESGOTADO! Você estourou o cronômetro demorando " + tempoGasto + "s.");
                } else {
                    System.out.println("❌ RESPOSTA INCORRETA!");
                }
                
                // REGISTA O ERRO NAS ESTATÍSTICAS
                player.incrementaErro();
                
                System.out.println("💥 " + enemy.getName() + " aproveitou sua falha e te atacou causando " + danoDoInimigo + " de dano!");
                player.takeDamage(danoDoInimigo);
            }

            roundNumber++;
            pressEnterToContinue();
        }

        return showBattleResult();
    }

    private void exibirMenuTurno(Question currentQuestion) {
        System.out.println("\n[MENU DE TURNO]:");
        System.out.println("1) Visualizar pergunta e responder diretamente");
        if (!abilityUsedThisBattle) {
            System.out.println("2) Invocar uma Habilidade Especial");
        } else {
            System.out.println("2) [Habilidade já gasta nesta batalha]");
        }
        System.out.print("Escolha: ");
        String opcao = scanner.nextLine().trim();

        if (opcao.equals("2") && !abilityUsedThisBattle) {
            System.out.println("\n=== HABILIDADES DISPONÍVEIS ===");
            for (int i = 0; i < availableAbilities.size(); i++) {
                SpecialAbility ab = availableAbilities.get(i);
                System.out.printf("%d) %s -> %s\n", (i + 1), ab.getName(), ab.getDescription());
            }
            System.out.print("Escolha a numeração correspondente (ou 0 para recuar): ");
            String escolhaHab = scanner.nextLine().trim();
            
            try {
                int index = Integer.parseInt(escolhaHab) - 1;
                if (index >= 0 && index < availableAbilities.size()) {
                    availableAbilities.get(index).use(player, enemy, currentQuestion);
                    abilityUsedThisBattle = true;
                }
            } catch (Exception e) {
                System.out.println("Entrada corrompida. Direcionando para a pergunta...");
            }
        }
    }

    private boolean showBattleResult() {
        System.out.println("\n==═╗");
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
        System.out.print("\n[Pressione ENTER para avançar...]");
        scanner.nextLine();
    }

    // MÉTODO AUXILIAR PARA RENDERIZAR A BARRA GRÁFICA NA CONSOLA
    private String exibirBarraVida(int hpAtual, int hpMaximo) {
        int tamanhoBarra = 10; 
        double percentagem = (double) hpAtual / hpMaximo;
        int blocosPreenchidos = (int) Math.round(percentagem * tamanhoBarra);

        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < tamanhoBarra; i++) {
            if (i < blocosPreenchidos) {
                barra.append("█");
            } else {
                barra.append("░");
            }
        }
        barra.append("]");
        return barra.toString();
    }
}