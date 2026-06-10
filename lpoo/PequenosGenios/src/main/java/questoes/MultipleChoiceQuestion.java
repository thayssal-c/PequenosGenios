package questoes; // Package declaration for the MultipleChoiceQuestion class

public class MultipleChoiceQuestion extends Question {  // Class representing a multiple choice question, extending the abstract Question class
    private String[] options; // Array to hold the options for the multiple choice question
    private int correctIndex; // Integer to hold the index of the correct answer in the options array

    public MultipleChoiceQuestion(String questionText, String difficulty, String category, String[] options, int correctIndex) { // Constructor for the MultipleChoiceQuestion class 
        super(questionText, difficulty, category); // Calling the constructor of the superclass (Question) to initialize the common attributes of a question
        this.options = options; // Initializing the options array with the provided options for the multiple choice question
        this.correctIndex = correctIndex; // Initializing the correctIndex with the provided index of the correct answer for the multiple choice question
    }

@Override
public boolean checkAnswer(String answer) {
    if (answer == null || answer.trim().isEmpty()) {
        return false;
    }

    // Limpa espaços e transforma tudo em maiúsculo (ex: "  c) au  " vira "C) AU")
    String cleanAnswer = answer.trim().toUpperCase();
    
    // Pega o primeiro caractere digitado (ex: 'C')
    char primeiraLetra = cleanAnswer.charAt(0);
    
    // Se o usuário digitou uma letra de A a Z
    if (primeiraLetra >= 'A' && primeiraLetra <= 'Z') {
        // Truque de tabela ASCII: 'A' - 'A' = 0 | 'B' - 'A' = 1 | 'C' - 'A' = 2...
        int indexDoUsuario = primeiraLetra - 'A';
        return indexDoUsuario == correctIndex;
    }

    // Mantém este fallback caso em algum teste você queira digitar o número direto (0, 1, 2...)
    try {
        int index = Integer.parseInt(cleanAnswer);
        return index == correctIndex;
    } catch (NumberFormatException e) {
        return false;
    }
}

    @Override
    public String toString() { // Overriding the toString method to provide a string representation of the multiple choice question
        StringBuilder sb = new StringBuilder(super.toString() + "\n"); // Starting the string representation with the common question attributes from the superclass (Question)
        char[] letters = {'A', 'B', 'C', 'D'}; // Array of letters to label the options for display purposes
        for (int i = 0; i < options.length; i++) { // Looping through the options array to append each option to the string representation, prefixed with its index for display purposes
            sb.append(letters[i]).append(") ").append(options[i]).append("\n");
        }
        return sb.toString(); // Returning the complete string representation of the multiple choice question, including the question text and the list of options
    }
}
