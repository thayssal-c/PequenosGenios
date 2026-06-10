package battle;

import model.Player;
import model.Enemy;
import questoes.Question;

public class HealAbility implements SpecialAbility {
    @Override
    public String getName() { return "Café Expresso (Cura)"; }
    @Override
    public String getDescription() { return "Recupera instantaneamente 30 de HP."; }

    @Override
    public void use(Player player, Enemy enemy, Question currentQuestion) {
        player.heal(30);
        System.out.println("\n☕ Você tomou um café expresso duplo e recuperou 30 de HP!");
    }
}