// ------------------------ Form of creation ------------------------

// Display loading overlay when submitting a chat

$("#submitChatGroup").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});

// ------------------------ Filtering ------------------------

// Auto-submit filter-form when a career is selected

$("#careerCode").change((event) => {
    $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
    $("#chatListFilterForm")[0].submit()
});

// Append page param when a page is chosen

$(".page-link").click((e) => {
    const url = new URL(window.location.href);
    url.searchParams.set('page', `${e.target.id}`);
    window.location.replace(url)
});