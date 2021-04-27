document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("careerId").addEventListener('change', (event) => {
        $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
        document.getElementById("chatListFilterForm").submit()
    });

});

$("#submitChatGroup").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});