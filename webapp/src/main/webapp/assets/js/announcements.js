// ------------------------ Form of creation ------------------------

// Display loading overlay when submitting announcement

$("#submitAnnouncement").click(function(){
    $("div.spanner")[0].addClass("show");
    $("div.overlay")[0].addClass("show");
});

// Display appropiate form fields when selecting a target

$("#announcementTarget").change((e) => {
    if(e.target.value === "career"){
        $("#careerTarget")[0].hidden = false
        $("#courseTarget")[0].hidden = true
        $("#courseId")[0].value = ""
    }else if(e.target.value === "course"){
        $("#courseTarget")[0].hidden = false
        $("#careerTarget")[0].hidden = true
        $("#careerCode")[0].value = ""
    }else{
        $("#courseTarget")[0].hidden = true
        $("#careerTarget")[0].hidden = true
        $("#courseId")[0].value = ""
        $("#careerCode")[0].value = ""
    }
})

// ------------------------ Filtering ------------------------

// Auto-submit filter-form when a course is selected

$("#courseId").change((_) => {
    $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
    $("#courseAnnouncementsFilterForm")[0].submit()
});

// Append showSeen param when show seen switch changes

$("#showSeen").change((e) => {
    const url = new URL(window.location.href);
    url.searchParams.set('showSeen', `${e.target.checked}`);
    window.location.replace(url)
});

// Append page param when a page is chosen

$(".page-link").click((e) => {
    const url = new URL(window.location.href);
    url.searchParams.set('page', `${e.target.id}`);
    window.location.replace(url)
});