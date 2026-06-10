package battle;

import model.Player;
import model.Enemy;
import questoes.Question;

public class ShieldAbility implements SpecialAbility {
    @Override
    public String getName() { return "Gamer Pro (Escudo)"; }
    @Override
    public String getDescription() { return "Ganha um escudo protetor de 25 HP que absorve o próximo golpe."; }

    @Override
    public void use(Player player, Enemy enemy, Question currentQuestion) {
        player.setShield(25);
        System.out.println("\n🛡️ Você ativou sua postura defensiva e ganhou um escudo de 25 HP!");
    }
}