document.addEventListener("DOMContentLoaded", function(event) {

    const courseSelector = document.getElementById("courseId");
    if(courseSelector != null) {
        courseSelector.addEventListener('change', (event) => {
            document.getElementById("courseSearchBtn").hidden = (event.target.value === undefined)
        });
    }

});

async function hideAnnouncement(id){
    document.getElementById(id).hidden = true

    const res = await fetch(`announcements/markSeen?id=${id}`,{method: 'POST'});

    if(res.status !== 200){
        document.getElementById(id).hidden = false
        document.querySelector("#errorToast .toast-body").innerHTML =
            "No se ha podido marcar el anuncio como visualizado";
        $('#errorToast').toast("show");
    }

}

$("#submitAnnouncement").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});