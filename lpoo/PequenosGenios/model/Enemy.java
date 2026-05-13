package model;

public class Enemy {

    private final String name;
    private final String description;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final String difficulty;

    public Enemy(String name, String description, int hp, int attackPower, String difficulty) {
        this.name = name;
        this.description = description;
        this.maxHp = hp;
        this.hp = hp;
        this.attackPower = attackPower;
        this.difficulty = difficulty;
    }

    public void takeDamage(int damage) {
        hp = Math.max(0, hp - damage);
    }

    public boolean isAlive() { return hp > 0; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public String getDifficulty() { return difficulty; }

    @Override
    public String toString() {
        return String.format("%s | HP: %d/%d", name, hp, maxHp);
    }
}
