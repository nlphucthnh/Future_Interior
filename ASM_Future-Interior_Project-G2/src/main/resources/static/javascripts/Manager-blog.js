
var listBlogsTab = document.getElementById("list-blogs-tab");
var formBlogsTab = document.getElementById("form-blogs-tab");
var formBlogs = document.getElementById("form_blogs");
var listBlogs = document.getElementById("list_blogs");
var NameBlogBg = document.getElementById("NameBg");
listBlogsTab.style.backgroundColor = 'var(--gray_50)';
listBlogsTab.style.border = '1px solid var(--gray_300)';
NameBlogBg.style.display = 'none';
$("#UploadFileBg").change(function () {
    var nameFile = this.value.split(/(\\|\/)/g).pop()
    NameBlogBg.value = nameFile;
});
function TabList() {
    listBlogsTab.style.backgroundColor = 'var(--gray_50)';
    listBlogsTab.style.border = '1px solid var(--gray_300)';
    formBlogsTab.style.backgroundColor = 'white';
    formBlogsTab.style.border = 'none';
    listBlogs.className = "tab-pane active";
    formBlogs.className = "tab-pane";
}


function TabForm() {
    formBlogsTab.style.backgroundColor = 'var(--gray_50)';
    formBlogsTab.style.border = '1px solid var(--gray_300)';
    listBlogsTab.style.backgroundColor = 'white';
    listBlogsTab.style.border = 'none';
    formBlogs.className = "tab-pane active";
    listBlogs.className = "tab-pane";
}

function confomDelete(nameBlogs, idBlogs) {
    let message = "Bạn muốn xóa bài đăng : " + nameBlogs;

    if (confirm(message) == true) {
        const Http = new XMLHttpRequest();
        const url = '/Manager/blog/delete/' + idBlogs;
        Http.open("POST", url);
        Http.send();
        alert("Xóa thành công!");
        location.reload(true);
    }
}

function onInputName() {
    var UploadFileBg = document.getElementById("UploadFileBg");
    var NameBg = document.getElementById("NameBg");
    UploadFileBg.style.display = 'none';
    NameBg.style.display = 'block';
}

function UploadFile() {
    $("#UploadFileBg").show();
    $("#NameBg").hide();
}

$("#UploadFileBg").change(function () {
    const ref = firebase.storage().ref();
    const file = document.querySelector("#UploadFileBg").files[0];
    console.log(file);
    const metadata = {
        contentType: 'image/jpeg'
    };
    const nameFile = $("#UploadFileBg").val().split(/(\\|\/)/g).pop();
    const uploadIMG = ref.child(nameFile).put(file, metadata);
    uploadIMG
        .then(snapshot => snapshot.ref.getDownloadURL())
        .then(url => {
            console.log(url);
            NameBlogBg.value = url;
        })
        .catch(console.error)
})




