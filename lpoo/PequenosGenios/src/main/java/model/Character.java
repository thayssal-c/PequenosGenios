package model;

public class Character {

    public enum CharacterClass {
        MID, NERD
    }

    private final String name;
    private final CharacterClass characterClass;
    private final int powerLevel;
    private final int speedBonus;
    private final int scoreMultiplier;
    private final String description;

    public Character(String name, CharacterClass characterClass,
                     int powerLevel, int speedBonus,
                     int scoreMultiplier, String description) {

        this.name = name;
        this.characterClass = characterClass;
        this.powerLevel = powerLevel;
        this.speedBonus = speedBonus;
        this.scoreMultiplier = scoreMultiplier;
        this.description = description;
    }

    public String getName() { return name; }
    public CharacterClass getCharacterClass() { return characterClass; }
    public int getPowerLevel() { return powerLevel; }
    public int getSpeedBonus() { return speedBonus; }
    public int getScoreMultiplier() { return scoreMultiplier; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format(
                "%s [Conhecimentos Gerais: %d/5 | Bônus Tempo: +%ds | Multiplicador: %d%%]",
                name,
                powerLevel,
                speedBonus,
                scoreMultiplier
        );
    }
}

