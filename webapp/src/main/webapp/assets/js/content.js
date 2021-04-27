document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("courseId").addEventListener('change', (event) => {
        $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
        document.getElementById("contentListFilterForm").submit()
    });

});

$("#submitContent").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});