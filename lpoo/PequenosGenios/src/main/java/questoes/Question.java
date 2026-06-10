package questoes;

public abstract class Question {
    protected String questionText;
    protected String difficulty;
    protected String category;

    public Question(String questionText, String difficulty, String category) {
        this.questionText = questionText;
        this.difficulty = difficulty;
        this.category = category;
    }

    public abstract boolean checkAnswer(String answer);

    public String getQuestionText() { return questionText; }
    public String getDifficulty()   { return difficulty; }
    public String getCategory()     { return category; }

    @Override
    public String toString() {
        return "[" + difficulty.toUpperCase() + " | " + category + "] " + questionText;
    }
}
