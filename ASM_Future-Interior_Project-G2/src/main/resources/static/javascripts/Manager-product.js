// ------------------XỬ LÝ BÊN TABLE------------------//

// Sắp sếp danh sách
$("#sortField").change(function () {
    $("#Sort").trigger("click");
})

$("#sortOrder").change(function () {
    $("#Sort").trigger("click");
})

function deleteSanPham(idSanPham) {
    let message = "Bạn có muốn xóa sản phẩm này không ?";
    if (confirm(message)) {
        const Http = new XMLHttpRequest();
        $("#idSanPham" + idSanPham).remove();
        const url = '/Manager/product/delete?idSanPham=' + idSanPham;
        Http.open("POST", url);
        Http.send();
        alert("Xóa thành công!");
    }
}


// ------------------XỬ LÝ BÊN FORM------------------//

function UploadImg() {
    $("#fileUpload").trigger("click");
}

var number = 0;
function addHinhAnh(idHinhAnh, size, name) {
    $.ajax({
        type: "POST",
        url: "/Manager/product/addHinhAnh?idHinhAnh=" + idHinhAnh + "&&size=" + size + "&&name=" + name,
        success: function (response) {
            console.log(response);
            var date = response.ngayDang.slice(0, response.ngayDang.indexOf("T"));
            var duongDan = "'" + response.idHinhAnh + "'";
            var tenAnh = "'" + response.tenHinhAnh + "'";
            $("#body-table-hinhanh").append('<tr class="" id="jquery' + number + '"> <form action=""> <td scope="row" style="text-align: left;"> ' + response.tenHinhAnh + ' </td> <td>' + response.dungLuongAnh / 1000 + ' KB</td> <td>' + date + '</td> <td> <a  onclick="deleteHA01(' + number + ',' + tenAnh + ')" class="btn btn-deleteHinhAnh" style="color: var(--gray_600);"><i class="bi bi-trash-fill"></i></a> </td> </form></tr>');
            number++;
        }
    });
}

$("#fileUpload").change(function () {
    $("#btn-loading").show();
    $("#uploadImg").hide();
    const ref = firebase.storage().ref();
    const file = document.querySelector("#fileUpload").files[0];
    const metadata = {
        contentType: 'image/jpeg'
    };
    const nameFile = $("#fileUpload").val().split(/(\\|\/)/g).pop();
    const uploadIMG = ref.child(nameFile).put(file, metadata);
    uploadIMG
        .then(snapshot => snapshot.ref.getDownloadURL())
        .then(url => {
            console.log(url);
            $("#btn-loading").hide();
            $("#uploadImg").show();
            addHinhAnh(url, file.size, nameFile);
            // console.log(file.size);
        })
        .catch(console.error)
});

var listIdChatLieu = [];
function addChatLieu(idChatLieu, idSanPham) {
    $.ajax({
        type: "POST",
        url: "/Manager/product/addChatLieu?idChatLieu=" + idChatLieu,
        success: function (response) {
            if (listIdChatLieu.find(element => element == idChatLieu) == undefined) {
                $("#body-table-chatlieu").append('<tr class="" id="clsp' + response.idChatLieu + '"> <form action=""> <td scope="row" class="td-first-table">  <div class="text-blogs-group"> <h4 class="text-title-blogs text-title">' + response.tenChatLieu + '</h4> <h6 class="text-id-blogs text-title">' + response.idChatLieu + '</h6> </div> </td> <td>' + response.ngayTao + '</td> <td> <a onclick="deleteSPCL01(' + response.idChatLieu + ')" class="btn" style="color: var(--gray_600);"><i class="bi bi-trash-fill"></i></a> </td> </form></tr>');
                listIdChatLieu.push(idChatLieu);
            } else {
                alert("Chất liệu đã được thêm");
            }

        }
    });
}


function deleteSPCL01(idChatLieu) {
    if (confirm("Bạn muốn xóa chất liệu này ?")) {
        $("#clsp" + idChatLieu).remove();
        var index = listIdChatLieu.indexOf(idChatLieu)
        listIdChatLieu.splice(index, 1);
    }
}

function deleteSPCL02(idChatLieu, idChatLieuSanPham) {
    if (confirm("Bạn muốn xóa chất liệu này ?")) {
        $("#clsp" + idChatLieu).remove();
        var index = listIdChatLieu.indexOf(idChatLieu)
        listIdChatLieu.splice(index, 1);
        $.ajax({
            type: "POST",
            url: "/Manager/product/deleteChatLieu?idSanPhamChatLieu=" + idChatLieuSanPham,
            contentType: "application/json",
            success: function (response) {
                console.log(response);
            }
        });
    }
}


function deleteHA01(number, tenAnh) {
    if (confirm("Bạn muốn xóa hình ảnh này ?")) {
        $("#jquery" + number).remove();
        const storageRef = firebase.storage().ref();
        var desertRef = storageRef.child(tenAnh);
        desertRef.delete().then(() => {
            alert("xóa thành công");
        }).catch((error) => {
            console.log(error);
        });
    }
}

function deleteHA02(number, tenAnh) {
    if (confirm("Bạn muốn xóa hình ảnh này ?")) {
        $("#dtbase" + number).remove();
        const storageRef = firebase.storage().ref();
        var desertRef = storageRef.child(tenAnh);
        desertRef.delete().then(() => {
            alert("xóa thành công");
        }).catch((error) => {
            console.log("không tìm thấy ảnh");
        });
        $.ajax({
            type: "POST",
            url: "/Manager/product/deleteHinhAnh?tenHinhAnh=" + tenAnh,
            contentType: "application/json",
            success: function (response) {
                console.log(response);
            }
        });

    }
}


// ------------------XỬ LÝ CHECK FORM------------------//

function checkFormProduct() {
    var flag = true;
    if ($("#id_product").val() == "") {
        $('#id_product-text').show();
        flag = false;
    } else {
        $('#id_product-text').hide();
    }

    if ($('#name_product').val() == "") {
        $('#name_product-text').show();
        flag = false;
    } else {
        $('#name_product-text').hide();
    }

    if ($('#price_product').val() == "") {
        $('#price_product-text').show();
        flag = false;
    } else {
        $('#price_product-text').hide();
    }
    // 
    if ($("#quatity_product").val() == "") {
        $('#quatity_product-text').show();
        flag = false;
    } else {
        $('#quatity_product-text').hide();
    }

    if ($("#mass_product").val() == "") {
        $('#mass_product-text').show();
        flag = false;
    } else {
        $('#mass_product-text').hide();
    }

    if ($("#date_product").val() == "") {
        $('#date_product-text').show();
        flag = false;
    } else {
        $('#date_product-text').hide();
    }
    //
    if ($('#length_product').val() == "") {
        $('#length_product-text').show();
        flag = false;
    } else {
        $('#length_product-text').hide();
    }

    if ($("#width_product").val() == "") {
        $('#width_product-text').show();
        flag = false;
    } else {
        $('#width_product-text').hide();
    }

    if ($("#height_product").val() == "") {
        $('#height_product-text').show();
        flag = false;
    } else {
        $('#height_product-text').hide();
    }
    //
    if ($("#description_product").val() == "") {
        $('#description_product-text').show();
        flag = false;
    } else {
        $('#description_product-text').hide();
    }
    return flag;
}

function addProduct() {
    if (!checkFormProduct()) {
        alert("Vui lòng kiểm tra lại form !");
        return;
    }
    var idProduct = $("#id_product").val();
    $.ajax({
        type: "POST",
        url: "/Manager/product/json?idProduct=" + idProduct,
        contentType: "application/json",
        success: function (response) {
            if (response) {
                alert("Thêm thành công");
                $('#btn-add-01').prop('disabled', false);
                $('#btn-add-01').trigger('click');
            } else {
                alert("Mã sản phẩm đã tồn tại !");
            }
        },
        error: function (error) {
            console.log(error);
        }
    });
}



$('.input-form').keyup(function (e) {
    if (e.keyCode == 13) {
        addProduct();
    }
});

$('#btn-add-02').click(function () {
    addProduct();
});

$('#btn-update-02').click(function () {
    if (!checkFormProduct()) {
        alert("Vui lòng kiểm tra lại form !");
        return;
    }
    $('#btn-update-01').prop('disabled', false);
    $('#btn-update-01').trigger('click');
});


// ------------------XỬ LÝ CHUYỂN TAB------------------//

$('#list-blogs-tab').css("background-color", "var(--gray_50)");
$('#list-blogs-tab').css("border", "1px solid var(--gray_300)");
$('#list_blogs').show();
$('#list-blogs-tab').click(function (e) {
    $('#list-blogs-tab').css("background-color", "var(--gray_50)");
    $('#list-blogs-tab').css("border", "1px solid var(--gray_300)");
    $('#form-blogs-tab').css("background-color", "white");
    $('#form-blogs-tab').css("border", "none");
    $('#list_blogs').show();
    $('#form_blogs').hide();
});

$('#form-blogs-tab').click(function (e) {
    $('#form-blogs-tab').css("background-color", "var(--gray_50)");
    $('#form-blogs-tab').css("border", "1px solid var(--gray_300)");
    $('#list-blogs-tab').css("background-color", "white");
    $('#list-blogs-tab').css("border", "none");
    $('#form_blogs').show();
    $('#list_blogs').hide();
});



