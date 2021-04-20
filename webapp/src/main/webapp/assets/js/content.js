document.addEventListener("DOMContentLoaded", function(event) {

    const courseSelector = document.getElementById("courseId");
    if(courseSelector != null) {
        courseSelector.addEventListener('change', (event) => {
            document.getElementById("courseSearchBtn").hidden = (event.target.value === undefined)
        });
    }

});

$("#submitContent").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});