// Render quiz review
var quizContainer = document.getElementById("quiz");

for (var i = 0; i < quizData.length; i++) {
    var questionDiv = document.createElement("div");
    questionDiv.classList.add("question");

    var questionText = document.createElement("p");
    questionText.innerHTML = "<strong>Question " + (i + 1) + ":</strong> " + quizData[i].question;
    questionDiv.appendChild(questionText);

    var optionAText = document.createElement("p");
    optionAText.innerHTML = "<strong>Option A:</strong> " + quizData[i].optionA;
    questionDiv.appendChild(optionAText);

    var optionBText = document.createElement("p");
    optionBText.innerHTML = "<strong>Option B:</strong> " + quizData[i].optionB;
    questionDiv.appendChild(optionBText);

    var optionCText = document.createElement("p");
    optionCText.innerHTML = "<strong>Option C:</strong> " + quizData[i].optionC;
    questionDiv.appendChild(optionCText);

    var optionDText = document.createElement("p");
    optionDText.innerHTML = "<strong>Option D:</strong> " + quizData[i].optionD;
    questionDiv.appendChild(optionDText);

    var chosenAnswerText = document.createElement("p");
    var correctAnswerText = document.createElement("p");
    for(var j = 0; j < chosenAnswers.length; j++){
        if(chosenAnswers[j].questionID == quizData[i].questionID){
            chosenAnswerText.innerHTML = "<strong>Chosen Answer:</strong> " + chosenAnswers[j].answerContent;
            questionDiv.appendChild(chosenAnswerText);
            if(chosenAnswers[j].answerContent == quizData[i].correctAnswer){
                correctAnswerText.innerHTML = "<strong>Correct Answer:</strong> " + quizData[i].correctAnswer;
                correctAnswerText.style.color = "green";
                questionDiv.appendChild(correctAnswerText);
            }
            else{
                correctAnswerText.innerHTML = "<strong>Correct Answer:</strong> " + quizData[i].correctAnswer;
                correctAnswerText.style.color = "red";
                questionDiv.appendChild(correctAnswerText);
            }
        }
    }

    quizContainer.appendChild(questionDiv);
}
