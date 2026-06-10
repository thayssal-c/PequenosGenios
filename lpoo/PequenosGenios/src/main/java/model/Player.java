package model;

public class Player {

    private final String name;
    private final Character character;
    private int hp;
    private final int maxHp;
    private int score;
    
    // Novas mecânicas para suporte ao sistema de habilidades
    private int shield = 0;
    private boolean doubleDamageActive = false;

    public Player(String name, Character character) {
        this.name = name;
        this.character = character;
        this.maxHp = 100;
        this.hp = maxHp;
        this.score = 0;
    }

    // Dano recebido modificado para processar o Escudo antes do HP (Requisito 3)
    public void takeDamage(int damage) {
        if (shield > 0) {
            if (shield >= damage) {
                shield -= damage;
                System.out.println("🛡️ O escudo absorveu TODOS os " + damage + " de dano! Escudo restante: " + shield);
                damage = 0;
            } else {
                damage -= shield;
                System.out.println("🛡️ O escudo quebrou absorvendo " + shield + " de dano. Você sofreu " + damage + " na pele.");
                shield = 0;
            }
        }
        hp = Math.max(0, hp - damage);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }

    public void addScore(int points) {
        score += points;
    }

    public int getAttackPower() {
        return character.getPowerLevel() * 4 + character.getScoreMultiplier() / 20;
    }

    // Getters e Setters das novas mecânicas de batalha
    public int getShield() { return shield; }
    public void setShield(int shield) { this.shield = shield; }

    public boolean isDoubleDamageActive() { return doubleDamageActive; }
    public void setDoubleDamageActive(boolean active) { this.doubleDamageActive = active; }
    
    public void setHp(int hp) { this.hp = Math.min(maxHp, hp); } // Garante conformidade com chamadas antigas

    public boolean isAlive() { return hp > 0; }
    public String getName() { return name; }
    public Character getCharacter() { return character; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return String.format("%s (%s) | HP: %d/%d | Escudo: %d | Score: %d",
                name, character.getName(), hp, maxHp, shield, score);
    }
}