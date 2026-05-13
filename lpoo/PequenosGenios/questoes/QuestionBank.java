package questoes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionBank {

    private final List<Question> questions;

    public QuestionBank() {
        questions = new ArrayList<>();
        loadQuestions();
    }

    private void loadQuestions() {

        // === FÁCEIS - Múltipla Escolha ===
        questions.add(new MultipleChoiceQuestion(
                "Qual é a capital do Brasil?",
                "easy", "Geografia",
                new String[]{"São Paulo", "Brasília", "Rio de Janeiro", "Salvador"}, 1));

        questions.add(new MultipleChoiceQuestion(
                "Quanto é 8 x 7?",
                "easy", "Matemática",
                new String[]{"54", "56", "64", "48"}, 1));

        questions.add(new MultipleChoiceQuestion(
                "Qual planeta é conhecido como Planeta Vermelho?",
                "easy", "Astronomia",
                new String[]{"Vênus", "Júpiter", "Marte", "Saturno"}, 2));

        questions.add(new MultipleChoiceQuestion(
                "Quantos continentes existem no mundo?",
                "easy", "Geografia",
                new String[]{"5", "6", "7", "8"}, 2));

        // === FÁCEIS - Verdadeiro ou Falso ===
        questions.add(new TrueFalseQuestion(
                "A água ferve a 100°C ao nível do mar.",
                "easy", "Ciências", true));

        questions.add(new TrueFalseQuestion(
                "O Brasil é o maior país da América do Sul.",
                "easy", "Geografia", true));

        questions.add(new TrueFalseQuestion(
                "O Sol é uma estrela.",
                "easy", "Astronomia", true));

        questions.add(new TrueFalseQuestion(
                "A baleia é um peixe.",
                "easy", "Biologia", false));

        // === MÉDIAS - Múltipla Escolha ===
        questions.add(new MultipleChoiceQuestion(
                "Quem escreveu 'Dom Casmurro'?",
                "medium", "Literatura",
                new String[]{"José de Alencar", "Machado de Assis", "Carlos Drummond", "Guimarães Rosa"}, 1));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o símbolo químico do ouro?",
                "medium", "Química",
                new String[]{"Go", "Or", "Au", "Ag"}, 2));

        questions.add(new MultipleChoiceQuestion(
                "Em que ano o Brasil foi descoberto?",
                "medium", "História",
                new String[]{"1492", "1500", "1498", "1510"}, 1));

        questions.add(new MultipleChoiceQuestion(
                "Qual é o maior órgão do corpo humano?",
                "medium", "Biologia",
                new String[]{"Fígado", "Pulmão", "Pele", "Intestino"}, 2));

        // === MÉDIAS - Verdadeiro ou Falso ===
        questions.add(new TrueFalseQuestion(
                "Albert Einstein ganhou o Nobel de Física pela Teoria da Relatividade.",
                "medium", "História da Ciência", false));

        questions.add(new TrueFalseQuestion(
                "O DNA tem formato de dupla hélice.",
                "medium", "Biologia", true));

        questions.add(new TrueFalseQuestion(
                "A Revolução Francesa ocorreu no século XVIII.",
                "medium", "História", true));

        // === DIFÍCEIS - Múltipla Escolha ===
        questions.add(new MultipleChoiceQuestion(
                "Qual é o menor país do mundo?",
                "hard", "Geografia",
                new String[]{"Mônaco", "San Marino", "Vaticano", "Liechtenstein"}, 2));

        questions.add(new MultipleChoiceQuestion(
                "Quantos ossos tem o corpo humano adulto?",
                "hard", "Biologia",
                new String[]{"196", "206", "216", "226"}, 1));

        // === DIFÍCEIS - Verdadeiro ou Falso ===
        questions.add(new TrueFalseQuestion(
                "O número Pi é um número racional.",
                "hard", "Matemática", false));

        questions.add(new TrueFalseQuestion(
                "A velocidade da luz no vácuo é de aproximadamente 300.000 km/s.",
                "hard", "Física", true));
    }

    // Retorna perguntas filtradas por dificuldade
    public List<Question> getQuestionsByDifficulty(String difficulty) {
        return questions.stream()
                .filter(q -> q.getDifficulty().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());
    }

    // Retorna uma pergunta aleatória por dificuldade
    public Question getRandomQuestion(String difficulty) {
        List<Question> filtered = getQuestionsByDifficulty(difficulty);
        if (filtered.isEmpty()) {
            Collections.shuffle(questions);
            return questions.get(0);
        }
        Collections.shuffle(filtered);
        return filtered.get(0);
    }

    public int getTotalQuestions() { return questions.size(); }
}
