$('#img-avata').click(function (e) {
    $("#avatar-input").trigger("click");
});

$("#avatar-input").change(function (e) {
    const ref = firebase.storage().ref();
    const file = document.querySelector("#avatar-input").files[0];
    const metadata = {
        contentType: 'image/jpeg'
    };
    const nameFile = $("#avatar-input").val().split(/(\\|\/)/g).pop();
    const uploadIMG = ref.child(nameFile).put(file, metadata);
    uploadIMG
        .then(snapshot => snapshot.ref.getDownloadURL())
        .then(url => {
            console.log(url);
            uploadAvata(url);
        })
        .catch(console.error)
});

function uploadAvata(url) {
    $.ajax({
        type: "POST",
        url: "/User/person/avata?urlImg=" + url,
        success: function (response) {
            console.log("Đã tới đây");
            location.reload(true);
        }
    });
}