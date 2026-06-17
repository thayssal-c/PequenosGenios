# Pequenos Gênios - CodeArena: Batalha do Conhecimento 🎓⚔️

Este é o projeto final do jogo de perguntas e respostas em turnos, desenvolvido para a disciplina de Linguagem de Programação Orientada a Objetos (LPOO). 

O jogador escolhe uma área de conhecimento e enfrenta uma campanha progressiva, usando estratégias e habilidades especiais polimórficas.

---

## 🛠️ Pré-requisitos

Para rodar o projeto, você vai precisar de:
- JDK (versão 17 ou superior).
- Apache Maven (versão 3.6 ou superior).

---

## 🔑 Configuração da API (OpenAI)

Como o jogo usa IA para gerar perguntas dinâmicas, siga estes passos:
1. Na pasta raiz do projeto (onde está o `pom.xml`), crie um arquivo chamado `api_key.txt`.
2. Cole sua chave secreta da OpenAI dentro dele (sem espaços ou linhas extras).
3. Salve. (O arquivo já está no `.gitignore` para sua segurança).

---

## 🚀 Como Executar

Abra o terminal na **pasta raiz** do projeto e rode os comandos:

1. Compilar:
   ```bash
   mvn clean compile

2. Rodar:

    Bash
    mvn exec:java -Dexec.mainClass="FuncaoMain.Main" 

Mecânicas
Personagens: Escolha entre Aluno de Humanas (MID) ou Aluno de Exatas (NERD).

Campanha: 3 oponentes com dificuldade crescente.

Habilidades: Use o menu tático para ativar Cura, Escudo ou Dano Duplo.

Estatísticas: Ao final, o jogo mostra um relatório completo de acertos, erros e pontuação.      



Diagrama de Classes UML (Formato Mermaid)



```mermaid
classDiagram
    direction topBottom

    class Main {
        +main(args: String[]) void
        -showWelcome() void
        -selectCharacter(scanner: Scanner) Character
        -showFinalScreen(player: Player, battlesWon: int, totalBattles: int) void
    }

    class BattleManager {
        -Player player
        -Enemy enemy
        -questions_system questionGenerator
        -Scanner scanner
        -int roundNumber
        -boolean abilityUsedThisBattle
        -List~SpecialAbility~ availableAbilities
        +playBattle() boolean
        -exibirMenuTurno(currentQuestion: Question) void
        -showBattleResult() boolean
        -exibirBarraVida(hpAtual: int, hpMaximo: int) String
    }

    class Character {
        -String name
        -CharacterClass characterClass
        -int powerLevel
        -int speedBonus
        -int scoreMultiplier
        -String description
        +getName() String
        +getPowerLevel() int
        +getScoreMultiplier() int
    }

    class Player {
        -String name
        -Character character
        -int hp
        -int maxHp
        -int score
        -int shield
        -boolean doubleDamageActive
        -int perguntasCertas
        -int perguntasErradas
        +takeDamage(damage: int) void
        +heal(amount: int) void
        +incrementaAcerto() void
        +incrementaErro() void
    }

    class Enemy {
        -String name
        -String description
        -int hp
        -int maxHp
        -int attackPower
        -String difficulty
        +takeDamage(damage: int) void
        +isAlive() boolean
    }

    class questions_system {
        +generateQuestion(difficulty: String, type: String) Question
    }

    class Question {
        <<abstract>>
        -String enun
        -String difficulty
        +checkAnswer(answer: String) boolean
    }

    class SpecialAbility {
        <<interface>>
        +getName() String
        +getDescription() String
        +use(player: Player, enemy: Enemy, currentQuestion: Question) void
    }

    class HealAbility {
        +use(player: Player, enemy: Enemy, currentQuestion: Question) void
    }

    class ShieldAbility {
        +use(player: Player, enemy: Enemy, currentQuestion: Question) void
    }

    class DoubleDamageAbility {
        +use(player: Player, enemy: Enemy, currentQuestion: Question) void
    }

    Main ..> Player : Instancia
    Main ..> Enemy : Instancia
    Main ..> BattleManager : Instancia
    BattleManager --> Player : Gerencia
    BattleManager --> Enemy : Gerencia
    BattleManager --> questions_system : Consulta
    BattleManager --> SpecialAbility : Agrega de forma polimórfica
    Player --> Character : Composição
    SpecialAbility <|.. HealAbility : Implementa
    SpecialAbility <|.. ShieldAbility : Implementa
    SpecialAbility <|.. DoubleDamageAbility : Implementa
    questions_system ..> Question : Retorna



Justificativa de Design

Relatório: Arquitetura e Decisões de Design
O projeto Pequenos Gênios foi estruturado para demonstrar o uso prático de Programação Orientada a Objetos. Abaixo, justifico as principais escolhas arquiteturais:

1. Encapsulamento e Proteção de Dados
Atributos críticos, como hp, shield e score, foram definidos como private. O método Player.takeDamage() é um exemplo de encapsulamento bem aplicado: ele não apenas subtrai valor, mas gerencia a lógica de quebra de escudo antes de afetar o HP. Isso garante que a classe de controle (BattleManager) não precise saber como o dano é calculado, ela apenas solicita a ação.

2. Polimorfismo e Extensibilidade
O uso de uma interface para SpecialAbility foi a decisão mais importante para o design. Ao tratar habilidades como HealAbility ou DoubleDamageAbility através da mesma interface, o BattleManager não precisa de comandos if/else gigantescos para verificar qual habilidade foi usada. Ele apenas chama habilidade.use(). Isso segue o Princípio Aberto/Fechado: podemos adicionar dezenas de novas habilidades no futuro sem mexer no código do combate.

3. Composição de Personagens
Optamos por usar Character como uma classe de composição dentro de Player. Isso significa que o jogador tem um personagem, em vez de ser um. Isso facilita muito a troca de atributos (bônus de tempo, multiplicadores) sem ter que criar várias subclasses complexas de jogadores, mantendo o código limpo e fácil de manter.

4. Fluxo e Progressão
Para a entrega final, a Main foi adaptada para um loop de batalha. Ao tratar a lista de Enemy como um conjunto de objetos, o sistema de dificuldade funciona de forma natural: conforme avançamos na lista, o BattleManager recebe um inimigo com attackPower maior, escalando o desafio sem que o código perca a organização.