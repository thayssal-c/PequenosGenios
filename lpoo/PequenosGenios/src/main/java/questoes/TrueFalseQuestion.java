package questoes;

public class TrueFalseQuestion extends Question {
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, String difficulty, String category, boolean correctAnswer) { // Constructor for the TrueFalseQuestion class
        super(questionText, difficulty, category); // Calling the constructor of the superclass (Question) to initialize the common attributes of a question
        this.correctAnswer = correctAnswer; // Initializing the correctAnswer with the provided boolean value indicating the correct answer for the true/false question
    }

    @Override // Overriding the checkAnswer method from the Question class to provide the logic for checking if a given answer is correct for a true/false question
    public boolean checkAnswer(String answer) {
        return Boolean.parseBoolean(answer.trim()) == correctAnswer; // Parsing the provided answer as a boolean and comparing it to the correctAnswer for the true/false question
    }

    public boolean getCorrectAnswer() { return correctAnswer; } // Getter method to retrieve the correct answer for the true/false question

    @Override // Overriding the toString method to provide a string representation of the true/false question
    public String toString() {
        return super.toString() + " (Verdadeiro/Falso)"; // Returning a string representation of the true/false question
    }
}