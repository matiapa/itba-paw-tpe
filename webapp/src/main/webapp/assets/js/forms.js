$("form").submit(function() {
    $(this).find('select:not(:has(option:selected[value!=""]))')
        .attr('name', '');
});