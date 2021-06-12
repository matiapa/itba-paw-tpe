const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
});

let oldPictureB64 = ""

$("#newPicture").change(async () => {
    $("#submitNewPicture")[0].hidden = false
    $("#cancelNewPicture")[0].hidden = false

    const currPic = $("#currentPicture")[0]
    oldPictureB64 = currPic.src

    const file = $("#newPicture")[0].files[0];
    currPic.src = await toBase64(file)
});

$("#cancelNewPicture").click(() => {
    $("#currentPicture")[0].src = oldPictureB64
    $("#newPicture")[0].value = $("#newPicture")[0].defaultValue

    $("#submitNewPicture")[0].hidden = true
    $("#cancelNewPicture")[0].hidden = true
});

$("#personId").change((event) => {
    console.log('OK')
    $(this).find('select:not(:has(option:selected[value!=""]))').attr('name', '');
    $("#personSearchForm")[0].submit()
});