// ------------------------ Form of creation ------------------------

// Display loading overlay when submitting a poll

$("#submitPoll").click(function(){
  $("div.spanner").addClass("show");
  $("div.overlay").addClass("show");
});

// Display appropiate form fields when selecting a target

$("#pollTarget").change((event) => {

  if(event.target.value === "career"){
    $("#careerTarget")[0].hidden = false
    $("#courseTarget")[0].hidden = true
    $("#courseId")[0].value = ""
  }else if(event.target.value === "course"){
    $("#courseTarget")[0].hidden = false
    $("#careerTarget")[0].hidden = true
    $("#careerCode")[0].value = ""
  }else{
    $("#courseTarget")[0].hidden = true
    $("#careerTarget")[0].hidden = true
    $("#courseId")[0].value = ""
    $("#careerCode")[0].value = ""
  }

});

// ------------------------ Filtering ------------------------

// Auto-submit filter-form when a course is selected

$("#courseId").change((event) => {
  $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
  $("#coursesPollFilterForm")[0].submit()
});

$("#submitPoll").click((event) => {
  var i=0;
  document.querySelectorAll('#optionsCol input').forEach(field => field.name = field.value == "" ?  "" : `options[${i++}]`)
  $("#createPollForm")[0].submit()
});

// Append page param when a page is chosen

$(".page-link").click((e) => {
  const url = new URL(window.location.href);
  url.searchParams.set('page', `${e.target.id}`);
  window.location.replace(url)
});