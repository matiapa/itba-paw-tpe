document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("courseId").addEventListener('change', (event) => {
        document.getElementById("courseSearchBtn").hidden = (event.target.value === undefined)
    });

});