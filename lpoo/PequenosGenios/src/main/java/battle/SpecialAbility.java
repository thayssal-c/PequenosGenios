package battle;

import model.Player;
import model.Enemy;
import questoes.Question;

public interface SpecialAbility {
    String getName();
    String getDescription();
    void use(Player player, Enemy enemy, Question currentQuestion);
}