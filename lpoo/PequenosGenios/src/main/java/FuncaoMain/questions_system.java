package FuncaoMain;

import questoes.Question;
import questoes.MultipleChoiceQuestion;
import questoes.TrueFalseQuestion;


import com.theokanning.openai.completion.chat.ChatCompletionRequest; // Importing libraries for OpenAI API interaction and chat completion request handling
import com.theokanning.openai.completion.chat.ChatMessage; // Importing necessary for OpenAI API interaction and chat message handling
import com.theokanning.openai.completion.chat.ChatMessageRole; // Importing libraries for OpenAI API interaction and chat message handling
import com.theokanning.openai.service.OpenAiService; // Importing libraries for OpenAI API interaction
import com.google.gson.JsonArray; // Importing libraries for handling JSON arrays
import com.google.gson.JsonObject; // Importing libraries for OpenAI API interaction and JSON object handling
import com.google.gson.JsonParser; // Importing libraries for OpenAI API interaction and JSON parsing

import java.util.List; // Importing necessary libraries for handling lists

public class questions_system { // Class declaration

    private static final String API_KEY = lerChaveDoArquivo();

    private final OpenAiService service;

    private static String lerChaveDoArquivo() {
        try {
        // Lê o arquivo api_key.txt que está na raiz do projeto
            return java.nio.file.Files.readString(java.nio.file.Paths.get("api_key.txt")).trim();
        } catch (Exception e) {
            System.out.println("[AVISO] Não foi possível ler o arquivo api_key.txt.");
            return "CHAVE_NAO_ENCONTRADA";
        }
    }

    public questions_system() { // Constructor for the questions_system class, initializing the OpenAI service instance using the protected API key
        this.service = new OpenAiService(API_KEY); // Initializing the OpenAI service instance using the protected API key
    }

    private String callingAPI(String level, String type) { // Method to call the OpenAI API and generate a question based on the specified difficulty level and question type
        String prompt = String.format(
            "Gere uma pergunta de conhecimentos gerais em portugues.\n" +
            "Nivel: %s\nTipo: %s\n\n" +
            "Retorne APENAS JSON válido, sem markdown e sem texto extra.\n\n" +
            "Se tipo for multiple_choice:\n" +
            "{\"type\":\"multiple_choice\",\"question\":\"...\",\"options\":[\"A\",\"B\",\"C\",\"D\"],\"correct\":0,\"category\":\"...\",\"level\":\"%s\"}\n\n" +
            "Se tipo for true_false:\n" +
            "{\"type\":\"true_false\",\"question\":\"...\",\"correct\":true,\"category\":\"...\",\"level\":\"%s\"}",
            level, type, level, level
        ); // Prompt for the API call

        // O seu prompt gigante fica em cima, aí você substitui a criação do request por isto:

            ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo") // O modelo de IA que vai gerar a pergunta
                .messages(java.util.List.of(new ChatMessage("user", prompt))) // <-- O PARAMETRO QUE FALTAVA!
                .temperature(0.7)
                .build();

            String raw = service.createChatCompletion(request).getChoices().get(0).getMessage().getContent();
            return raw;
    }

    public Question generateQuestion(String level, String type) { // Method to generate a question based on the specified difficulty level and question type, calling the OpenAI API and parsing the response to create a Question object
        String raw = callingAPI(level, type); // Calling the OpenAI API to generate a question and storing the raw response as a string
        raw = raw.replaceAll("```json|```", "").trim(); // Stripping markdown fences from the raw response and trimming any leading or trailing whitespace

        JsonObject obj = JsonParser.parseString(raw).getAsJsonObject(); // Parsing the raw response string as a JSON object using the JsonParser class from the Gson library

        String returnedType = obj.get("type").getAsString(); // Extracting the "type" field from the parsed JSON object to determine the type of question returned by the API
        String questionText = obj.get("question").getAsString(); // Extracting the "question" field from the parsed JSON object to get the text of the question
        String difficulty   = obj.get("level").getAsString(); // Extracting the "level" field from the parsed JSON object to get the difficulty level of the question
        String category     = obj.get("category").getAsString(); // Extracting the "category" field from the parsed JSON object to get the category of the question
        if (returnedType.equals("multiple_choice")) { // Checking if the returned question type is "multiple_choice" to determine how to parse the rest of the JSON object and create the appropriate Question object
            JsonArray optionsArray = obj.get("options").getAsJsonArray(); // Extracting the "options" field from the parsed JSON object as a JsonArray to get the options for the multiple choice question
            String[] options = new String[optionsArray.size()]; // Creating a new string array to hold the options for the multiple choice question
            for (int i = 0; i < optionsArray.size(); i++) { // Looping through the options array extracted from the JSON object to fill the options string array with the individual options for the multiple choice question
                options[i] = optionsArray.get(i).getAsString();
            }
            int correct = obj.get("correct").getAsInt();
            return new MultipleChoiceQuestion(questionText, difficulty, category, options, correct); // Creating and returning a new MultipleChoiceQuestion object using the extracted question text, difficulty level, category, options array, and correct answer index from the JSON object
        } else {
            boolean correct = obj.get("correct").getAsBoolean();
            return new TrueFalseQuestion(questionText, difficulty, category, correct); // Creating and returning a new TrueFalseQuestion object using the extracted question text, difficulty level, category, and correct answer boolean from the JSON object
        }
    }

    public Question generateRandomQuestion(String level) {
        // Sorteia 50/50 se vai ser múltipla escolha ou V/F
        String tipoPergunta = (Math.random() < 0.5) ? "multiple_choice" : "true_false";
        return generateQuestion(level, tipoPergunta);
    }

    public static void main(String[] args) {
        questions_system generator = new questions_system();
        Question q1 = generator.generateQuestion("easy", "multiple_choice"); // Generating an easy multiple choice question using the generateQuestion method of the questions_system class and storing it in a Question variable
        System.out.println(q1);
        Question q2 = generator.generateQuestion("medium", "true_false"); // Generating a medium true/false question using the generateQuestion method of the questions_system class and storing
        System.out.println(q2);
    }
}