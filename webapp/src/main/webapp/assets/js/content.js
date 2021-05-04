// ------------------------ Form of creation ------------------------

// Display loading overlay when submitting a content

$("#submitContent").click(function(){
    $("div.spanner").addClass("show");
    $("div.overlay").addClass("show");
});

// ------------------------ Filtering ------------------------

// Auto-submit filter-form when a career is selected

$("#courseId").change((event) => {
    $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
    $("#contentListFilterForm")[0].submit()
});

// Append page param when a page is chosen

$(".page-link").click((e) => {
    const url = new URL(window.location.href);
    url.searchParams.set('page', `${e.target.id}`);
    window.location.replace(url)
});

$("#courseSearchBtn").click((e) => {
    $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
})