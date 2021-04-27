$("#submitAnnouncement").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});

document.addEventListener("DOMContentLoaded", function(event) {

    document.getElementById("courseId").addEventListener('change', (event) => {
        $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
        document.getElementById("courseAnnouncementsFilterForm").submit()
    });

    document.getElementById("announcementTarget").addEventListener('change', (event) => {

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

    const showHiddenSwitch = document.getElementById("showHiddenSwitch")
    showHiddenSwitch.addEventListener('change', (event) => {
        for(let d of document.getElementsByClassName("card")){
            console.log(d.children[0].innerText === true)
            d.hidden = showHiddenSwitch.checked ? false : (d.children[0].innerText === 'true')
        }
    });

});

async function hideAnnouncement(id){
    document.getElementsByName(id).forEach(e => e.hidden = true)

    const res = await fetch(`announcements/markSeen?id=${id}`,{method: 'POST'});

    if(res.status !== 200){
        document.getElementById(id).hidden = false
        document.querySelector("#errorToast .toast-body").hidden = false
        document.querySelector("#errorToast .toast-body").innerHTML =
            "No se ha podido marcar el anuncio como visualizado";
        $('#errorToast').toast("show");
    }

}