$("#form-promotion-tab").css("background-color", "var(--gray_50)");
$("#form-promotion-tab").css("border", "1px solid var(--gray_300)");


function deleteKhuyenMai(idKhuyenMai) {
    if (confirm("Bạn muốn xóa mã khuyến mãi này ?")) {
        alert("Xóa thành công!");
        $("#row-khuyenMai" + idKhuyenMai).remove();
        const Http = new XMLHttpRequest();
        const url = '/Manager/filter/khuyenMai/delete/' + idKhuyenMai;
        Http.open("POST", url);
        Http.send();
    }
}

function offButton() {
    $(".btn-tab").css("background-color", "white");
    $(".btn-tab").css("border", "none");
}
function changeTab(nameTab) {
    offButton();
    if (nameTab === 'cl') {
        $("#chat-lieu-tab").css("background-color", "var(--gray_50)");
        $("#chat-lieu-tab").css("border", "1px solid var(--gray_300)");
        $("#chat-lieu").addClass("tab-pane active");
        $("#nhaSanXuat").addClass("tab-pane");
        $("#nhomLoai").addClass("tab-pane");
        $("#form-promotion").addClass("tab-pane");
    } else if (nameTab === 'nsx') {
        $("#nhaSanXuat-tab").css("background-color", "var(--gray_50)");
        $("#nhaSanXuat-tab").css("border", "1px solid var(--gray_300)");
        $("#nhaSanXuat").addClass("tab-pane active");
        $("#chat-lieu").addClass("tab-pane");
        $("#nhomLoai").addClass("tab-pane");
        $("#form-promotion").addClass("tab-pane");
    } else if (nameTab === 'nl') {
        $("#nhomLoai-tab").css("background-color", "var(--gray_50)");
        $("#nhomLoai-tab").css("border", "1px solid var(--gray_300)");
        $("#nhomLoai").addClass("tab-pane active");
        $("#chat-lieu").addClass("tab-pane");
        $("#nhaSanXuat").addClass("tab-pane");
        $("#form-promotion").addClass("tab-pane");
    } else if (nameTab === 'km') {
        $("#form-promotion-tab").css("background-color", "var(--gray_50)");
        $("#form-promotion-tab").css("border", "1px solid var(--gray_300)");
        $("#form-promotion").addClass("tab-pane active");
        $("#chat-lieu").addClass("tab-pane");
        $("#nhaSanXuat").addClass("tab-pane");
        $("#nhomLoai").addClass("tab-pane");
    }
}

function onBtnCreate() {
    $(".modal-input").val("");
    $(".modal-input-number").val(0);
    $(".btn-add").css("display", "block");
    $(".btn-update").css("display", "none");
}

// method nhà sản xuất
function checkFormNSX() {
    var flag = [true, true, true];
    if ($("#tenNhaSanXuat").val() == '') {
        $("#tenNhaSanXuat-text").show();
        flag[0] = false;
    } else {
        $("#tenNhaSanXuat-text").hide();
    }
    if ($("#ngayTaoNSX").val() == '') {
        $("#ngayTaoNSX-text").show();
        flag[1] = false;
    } else {
        $("#ngayTaoNSX-text").hide();
    }
    if ($("#moTaNhaSanXuat").val() == '') {
        $("#moTaNhaSanXuat-text").show();
        flag[2] = false;
    } else {
        $("#moTaNhaSanXuat-text").hide();
    }

    if (flag[0] && flag[1] && flag[2]) {
        return true;
    } else {
        return false;
    }
}

$(".nsx-input").keyup(function (e) {
    if (e.keyCode == 13) {
        insertNhaSanXuat();
    }
});

function insertNhaSanXuat() {
    if (!checkFormNSX()) {
        return;
    }
    $("#ThemMoiNSX").prop('disabled', false);
    $('#ThemMoiNSX').trigger('click');

}

function updateNhaSanXuat() {
    if (!checkFormNSX()) {
        return;
    }
    $("#CapNhatNSX").prop('disabled', false);
    $('#CapNhatNSX').trigger('click');
}

function setFormNhaSanXuat(idNhaSanXuat) {
    $(".btn-add").css("display", "none");
    $(".btn-update").css("display", "block");
    $.ajax({
        type: "POST",
        url: "/Manager/filter/nhaSanXuat?idnsx=" + idNhaSanXuat,
        contentType: "application/json",
        success: function (response) {
            $("#maNhaSanXuat").val(response.idNhaSanXuat);
            $("#tenNhaSanXuat").val(response.tenNhaSanXuat);
            $("#ngayTaoNSX").val(response.ngayTao);
            $("#moTaNhaSanXuat").val(response.moTaNhaSanXuat);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function deleteNhaSanXuat(idNhaSanXuat) {
    let message = "Bạn muốn xóa nhà sản xuất này ?";
    if (confirm(message) == true) {
        const Http = new XMLHttpRequest();
        $("#nha-san-xuat" + idNhaSanXuat).remove();
        const url = '/Manager/filter/nhaSanXuat/delete/' + idNhaSanXuat;
        Http.open("POST", url);
        Http.send();
        alert("Xóa thành công!");

    }
}


// method nhóm loại    

function checkFormNL() {
    var flag = [true, true, true];
    if ($("#tenNhomLoai").val() == '') {
        $("#tenNhomLoai-text").show();
        flag[0] = false;
    } else {
        $("#tenNhomLoai-text").hide();
    }
    if ($("#ngayTaoNL").val() == '') {
        $("#ngayTaoNL-text").show();
        flag[1] = false;
    } else {
        $("#ngayTaoNL-text").hide();
    }
    if ($("#moTaNhomLoai").val() == '') {
        $("#moTaNhomLoai-text").show();
        flag[2] = false;
    } else {
        $("#moTaNhomLoai-text").hide();
    }

    if (flag[0] && flag[1] && flag[2]) {
        return true;
    } else {
        return false;
    }
}


$(".nl-input").keyup(function (e) {
    if (e.keyCode == 13) {
        insertNhomLoai();
    }
});


function insertNhomLoai() {
    if (!checkFormNL()) {
        return;
    }
    $("#ThemMoiNhomLoai").prop('disabled', false);
    $("#ThemMoiNhomLoai").trigger('click');
}


function updateNhomLoai() {
    if (!checkFormNL()) {
        return;
    }
    $("#CapNhatNhomLoai").prop('disabled', false);
    $("#CapNhatNhomLoai").trigger('click');
}

function setFormNhomLoai(idNhomLoai) {
    $(".btn-add").css("display", "none");
    $(".btn-update").css("display", "block");
    $.ajax({
        type: "POST",
        url: "/Manager/filter/nhomLoai?idnl=" + idNhomLoai,
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            $("#maNhomLoai").val(response.idPhanNhomLoai);
            $("#tenNhomLoai").val(response.tenPhanLoaiNhom);
            $("#ngayTaoNL").val(response.ngayTao);
            $("#moTaNhomLoai").val(response.moTaPhanLoai);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function deleteNhomLoai(idNhomLoai) {
    let message = "Bạn muốn xóa nhóm loại này ?";
    if (confirm(message) == true) {
        const Http = new XMLHttpRequest();
        $("#nhom-loai" + idNhomLoai).remove();
        const url = '/Manager/filter/delete/nhomLoai/' + idNhomLoai;
        Http.open("POST", url);
        Http.send();
        alert("Xóa thành công!");

    }
}


// method chất liệu 
function checkFormCl() {
    var flag = [true, true, true];
    if ($("#tenChatLieu").val() == '') {
        $("#tenChatLieu-text").show();
        flag[0] = false;
    } else {
        $("#tenChatLieu-text").hide();
    }
    if ($("#ngayTaoCL").val() == '') {
        $("#ngayTaoCL-text").show();
        flag[1] = false;
    } else {
        $("#ngayTaoCL-text").hide();
    }
    if ($("#moTaChatLieu").val() == '') {
        $("#moTaChatLieu-text").show();
        flag[2] = false;
    } else {
        $("#moTaChatLieu-text").hide();
    }

    if (flag[0] && flag[1] && flag[2]) {
        return true;
    } else {
        return false;
    }
}

$(".cl-input").keyup(function (e) {
    if (e.keyCode == 13) {
        insertChatLieu();
    }
});

function insertChatLieu() {
    if (!checkFormCl()) {
        return;
    }
    $("#ThemMoiChatLieu").prop('disabled', false);
    $('#ThemMoiChatLieu').trigger('click');
}

function updateChatLieu() {
    if (!checkFormCl()) {
        return;
    }
    $("#CapNhatChatLieu").prop('disabled', false);
    $('#CapNhatChatLieu').trigger('click');
}

function deleteChatLieu(idChatLieu) {
    let message = "Bạn muốn xóa chất liệu này ?";
    if (confirm(message) == true) {
        alert("Xóa thành công!");
        $("#chatlieu" + idChatLieu).remove();
        const Http = new XMLHttpRequest();
        const url = '/Manager/filter/delete/chatlieu/' + idChatLieu;
        Http.open("POST", url);
        Http.send();
    }
}

function setFormChatLieu(idChatLieu) {
    $(".btn-add").css("display", "none");
    $(".btn-update").css("display", "block");
    $.ajax({
        type: "POST",
        url: "/Manager/filter/chatLieu?idlc=" + idChatLieu,
        contentType: "application/json",
        success: function (response) {
            $("#maChatLieu").val(response.idChatLieu);
            $("#tenChatLieu").val(response.tenChatLieu);
            $("#ngayTaoCL").val(response.ngayTao);
            $("#moTaChatLieu").val(response.moTaChatLieu);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

