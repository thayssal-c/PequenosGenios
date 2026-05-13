package model;

public class Player {

    private final String name;
    private final Character character;
    private int hp;
    private final int maxHp;
    private int score;

    public Player(String name, Character character) {
        this.name = name;
        this.character = character;
        this.maxHp = 100;
        this.hp = maxHp;
        this.score = 0;
    }

    // Dano recebido pelo jogador
    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    // Cura do jogador
    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    // Adiciona pontos ao score
    public void addScore(int points) {
        score += points;
    }

    // Poder de ataque baseado nos atributos do personagem
    public int getAttackPower() {
        return character.getPowerLevel() * 4 + character.getScoreMultiplier() / 20;
    }

    public boolean isAlive() { return hp > 0; }
    public String getName() { return name; }
    public Character getCharacter() { return character; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return String.format("%s (%s) | HP: %d/%d | Score: %d",
                name, character.getName(), hp, maxHp, score);
    }
}
