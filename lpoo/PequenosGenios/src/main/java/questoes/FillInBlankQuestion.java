package questoes;

public class FillInBlankQuestion extends Question implements TimedQuestion {
    private final String correctAnswer;

    public FillInBlankQuestion(String questionText, String difficulty, String category, String correctAnswer) {
        super(questionText, difficulty, category);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) return false;
        // Remove espaços extras e ignora maiúsculas/minúsculas
        return answer.trim().equalsIgnoreCase(this.correctAnswer.trim());
    }

    @Override
    public int getTimeLimitSeconds() {
        if (this.getDifficulty().equalsIgnoreCase("hard")) return 15;
        if (this.getDifficulty().equalsIgnoreCase("medium")) return 25;
        return 40; // easy
    }
}