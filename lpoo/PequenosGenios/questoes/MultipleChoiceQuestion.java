package questoes; // Package declaration for the MultipleChoiceQuestion class

public class MultipleChoiceQuestion extends Question {  // Class representing a multiple choice question, extending the abstract Question class
    private String[] options; // Array to hold the options for the multiple choice question
    private int correctIndex; // Integer to hold the index of the correct answer in the options array

    public MultipleChoiceQuestion(String questionText, String difficulty, String category, String[] options, int correctIndex) { // Constructor for the MultipleChoiceQuestion class 
        super(questionText, difficulty, category); // Calling the constructor of the superclass (Question) to initialize the common attributes of a question
        this.options = options; // Initializing the options array with the provided options for the multiple choice question
        this.correctIndex = correctIndex; // Initializing the correctIndex with the provided index of the correct answer for the multiple choice question
    }

    @Override // Overriding the checkAnswer method from the Question class to provide the logic for checking if a given answer is correct for a multiple choice question
    public boolean checkAnswer(String answer) { // Method to check if the provided answer is correct for the multiple choice question, overriding the abstract method from the Question class
        answer = answer.trim().toUpperCase(); // Trimming any leading or trailing whitespace from the provided answer and converting it to uppercase for case-insensitive comparison
        try {
            int index = Integer.parseInt(answer.trim()); // Attempting to parse the provided answer as an integer index
            return index == correctIndex; // Returning true if the parsed index matches the correctIndex for the multiple choice question
        } catch (NumberFormatException e) {
            return false; // If the provided answer cannot be parsed as an integer (e.g., if it's not a valid index)
        }
    }

    public String[] getOptions()   { return options; } // Getter method to retrieve the options array for the multiple choice question
    public int getCorrectIndex()   { return correctIndex; } // Getter method to retrieve the index of the correct answer for the multiple choice question

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
