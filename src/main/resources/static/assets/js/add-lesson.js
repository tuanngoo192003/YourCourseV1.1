document.addEventListener("DOMContentLoaded", function() {
    // Initialize lesson form elements
    const lessonContainer = document.getElementById("lesson-container");
    const addLessonBtn = document.getElementById("add-lesson-btn");

    // Add event listener to the "Add Lesson" button
    addLessonBtn.addEventListener("click", function() {
        createLessonForm();
    });

    // Function to create a new lesson form
    function createLessonForm() {
        const lessonForm = document.createElement("form");
        lessonForm.classList.add("lesson-form");

        const lessonTitleInput = document.createElement("input");
        lessonTitleInput.classList.add("lesson-title");
        lessonTitleInput.setAttribute("type", "text");
        lessonTitleInput.placeholder = "Lesson Title";
        lessonTitleInput.required = true;

        const materialsContainer = document.createElement("div");
        materialsContainer.classList.add("materials-container");

        const addMaterialBtn = document.createElement("button");
        addMaterialBtn.classList.add("add-material-btn");
        addMaterialBtn.setAttribute("type", "button");
        addMaterialBtn.textContent = "Add Material";

        const submitBtn = document.createElement("button");
        submitBtn.classList.add("submit-btn");
        submitBtn.setAttribute("type", "submit");
        submitBtn.textContent = "Submit";

        const cancelBtn = document.createElement("button");
        cancelBtn.classList.add("cancel-btn");
        cancelBtn.setAttribute("type", "button");
        cancelBtn.textContent = "Cancel";

        // Add event listener to the "Add Material" button
        addMaterialBtn.addEventListener("click", function() {
            createMaterialRow(materialsContainer);
        });

        // Add event listener to the form submission
        lessonForm.addEventListener("submit", function(event) {
            event.preventDefault();
            const lessonTitle = lessonTitleInput.value;
            const materialRows = Array.from(materialsContainer.getElementsByClassName("material-row"));
            const learningMaterials = [];

            for (const row of materialRows) {
                const materialType = row.querySelector(".material-type").value;
                const materialValue = row.querySelector(".material-value").value;

                if (materialType && materialValue) {
                    learningMaterials.push({ type: materialType, value: materialValue });
                }
            }

            if (learningMaterials.length === 0) {
                alert("Please add at least one learning material.");
                return;
            }

            // Perform your desired logic with the lesson title and learning materials

            const lessonData = {
                title: lessonTitle,
                materials: learningMaterials
            };

            console.log("Lesson data:", lessonData);
            // You can perform further actions like submitting the form or navigating to another page

            // Reset the form
            lessonForm.reset();
            materialsContainer.innerHTML = "";
        });

        // Add event listener to the "Cancel" button
        cancelBtn.addEventListener("click", function() {
            lessonContainer.removeChild(lessonForm);
        });

        lessonForm.appendChild(lessonTitleInput);
        lessonForm.appendChild(materialsContainer);
        lessonForm.appendChild(addMaterialBtn);
        lessonForm.appendChild(submitBtn);
        lessonForm.appendChild(cancelBtn);

        lessonContainer.appendChild(lessonForm);
    }

    // Function to create a new material row
    function createMaterialRow(materialsContainer) {
        const materialRow = document.createElement("div");
        materialRow.classList.add("material-row");

        const materialTypeSelect = document.createElement("select");
        materialTypeSelect.classList.add("material-type");
        materialTypeSelect.required = true;
        const fileOption = document.createElement("option");
        fileOption.value = "file";
        fileOption.text = "File";
        const urlOption = document.createElement("option");
        urlOption.value = "url";
        urlOption.text = "URL";
        materialTypeSelect.appendChild(fileOption);
        materialTypeSelect.appendChild(urlOption);

        const materialValueInput = document.createElement("input");
        materialValueInput.classList.add("material-value");
        materialValueInput.setAttribute("type", "text");
        materialValueInput.placeholder = "File name or URL";
        materialValueInput.required = true;

        const removeMaterialBtn = document.createElement("button");
        removeMaterialBtn.classList.add("remove-material");
        removeMaterialBtn.setAttribute("type", "button");
        removeMaterialBtn.textContent = "Remove";

        materialRow.appendChild(materialTypeSelect);
        materialRow.appendChild(materialValueInput);
        materialRow.appendChild(removeMaterialBtn);

        materialsContainer.appendChild(materialRow);
    }

    // Add event listener to the "Remove" buttons in the lesson forms
    lessonContainer.addEventListener("click", function(event) {
        if (event.target.classList.contains("remove-lesson")) {
            lessonContainer.removeChild(event.target.parentNode);
        }
    });
});
