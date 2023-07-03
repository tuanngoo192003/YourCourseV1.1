// Sample quiz data
var quizData = [
    {
        question: "What is the capital of France?",
        answer: "Paris"
    },
    {
        question: "Who painted the Mona Lisa?",
        answer: "Leonardo da Vinci"
    },
    {
        question: "What is the largest planet in our solar system?",
        answer: "Jupiter"
    }
];

// Render quiz review
var quizContainer = document.getElementById("quiz");

for (var i = 0; i < quizData.length; i++) {
    var questionDiv = document.createElement("div");
    questionDiv.classList.add("question");

    var questionText = document.createElement("p");
    questionText.innerHTML = "<strong>Question " + (i + 1) + ":</strong> " + quizData[i].question;
    questionDiv.appendChild(questionText);

    var answerText = document.createElement("p");
    answerText.innerHTML = "<strong>Answer:</strong> " + quizData[i].answer;
    questionDiv.appendChild(answerText);

    quizContainer.appendChild(questionDiv);
}
