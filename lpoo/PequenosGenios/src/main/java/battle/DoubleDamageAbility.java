package battle;

import model.Player;
import model.Enemy;
import questoes.Question;

public class DoubleDamageAbility implements SpecialAbility {

    @Override
    public String getName() {
        return "Foco Extremo (Dano Duplo)";
    }

    @Override
    public String getDescription() {
        return "Ativa a mente brilhante. O próximo acerto causará o DOBRO de dano.";
    }

    @Override
    public void use(Player player, Enemy enemy, Question currentQuestion) {
        player.setDoubleDamageActive(true);
        System.out.println("\n🔥 Foco Extremo ativado! Se acertares a próxima pergunta, o teu ataque será devastador!");
    }
}