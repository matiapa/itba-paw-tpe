$("#submitPoll").click(function(){
  $("div.spanner").addClass("show");
  $("div.overlay").addClass("show");
});

document.addEventListener("DOMContentLoaded", function(event) {

  if(document.getElementById("createPollForm") != null) {
    document.getElementById("pollTarget").addEventListener('change', (event) => {

      if(event.target.value === "career"){
        document.getElementById("careerTarget").hidden = false
        document.getElementById("courseTarget").hidden = true
        document.getElementById("courseId").value = ""
      }else if(event.target.value === "course"){
        document.getElementById("courseTarget").hidden = false
        document.getElementById("careerTarget").hidden = true
        document.getElementById("careerId").value = ""
      }else{
        document.getElementById("courseTarget").hidden = true
        document.getElementById("careerTarget").hidden = true
        document.getElementById("courseId").value = ""
        document.getElementById("careerId").value = ""
      }

    });
  }

});