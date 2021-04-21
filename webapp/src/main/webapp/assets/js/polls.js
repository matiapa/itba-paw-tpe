$("form[name='searchForm']").submit(function() {
  console.log('OK')
  $(this).find('input[type!="radio"][value=""],select:not(:has(option:selected[value!=""]))')
      .attr('name', '');
});