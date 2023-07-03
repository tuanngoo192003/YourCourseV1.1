document.getElementById("addLessonsBtn").addEventListener("click", function() {
    // Get form values
    var categoryInput = document.getElementById("category");
    var courseNameInput = document.getElementById("courseName");
    var descriptionInput = document.getElementById("description");
    var startDateInput = document.getElementById("startDate");
    var endDateInput = document.getElementById("endDate");

    // Validate form fields
    if (!validateFormInputs([categoryInput, courseNameInput, descriptionInput, startDateInput, endDateInput])) {
        alert("Please fill in all fields.");
        return;
    }

    // Store form values in session storage
    sessionStorage.setItem("category", categoryInput.value);
    sessionStorage.setItem("courseName", courseNameInput.value);
    sessionStorage.setItem("description", descriptionInput.value);
    sessionStorage.setItem("startDate", startDateInput.value);
    sessionStorage.setItem("endDate", endDateInput.value);

    // Navigate to the lessons page
    // window.location.href = "/addlesson.html";

});

function goToLessonsPage(){
    window.location.href = "/addLesson.html";
}

function validateFormInputs(inputs) {
    for (var i = 0; i < inputs.length; i++) {
        if (!inputs[i].value) {
            return false;
        }
    }
    return true;
}
