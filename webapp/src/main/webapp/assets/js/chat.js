$("form").submit(function() {
    $(this).find('select:not(:has(option:selected[value!=""]))')
        .attr('name', '');
  });  

$("#submitChatGroup").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});