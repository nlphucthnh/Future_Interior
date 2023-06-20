$("#liveAlertBtn").hide();

$('.input-enter').keyup(function (e) {
    if (e.keyCode == 13) {
        loginDiaLogForm();
    }
});

function checkFormLogin() {
    var flag = [true, true];
    if ($("#tenDangNhap").val() == "") {
        $("#tenDangNhap-text").show();
        flag[0] = false;
    } else {
        $("#tenDangNhap-text").hide();
    }
    if ($("#matKhau").val() == "") {
        $("#matKhau-text").show();
        flag[1] = false;
    } else {
        $("#matKhau-text").hide();
    }

    if (flag[0] && flag[1]) {
        return true;
    }
    return false;
}

function loginDiaLogForm() {
    if (!checkFormLogin()) {
        return;
    }

    var tenDangNhap = $("#tenDangNhap").val();
    var matKhau = $("#matKhau").val();
    var remember = $("#matKhaurm").val();
    $.ajax({
        type: "POST",
        url: "/Manager/login/json?username=" + tenDangNhap,
        contentType: "application/json",
        success: function (response) {
            console.log(response);
            if (response.tenDangNhap == "") {
                alert("Đăng nhập thất bại, có thể sai tên đăng nhập hoặc mật khẩu");
            } else {
                if (response.matKhau != matKhau) {
                    alert("Đăng nhập thất bại, có thể sai tên đăng nhập hoặc mật khẩu");
                } else if (!response.vaiTro || !response.trangThai) {
                    alert("Tài khoản không có quyền truy cập");
                } else {
                    alert("Đăng nhập thành công");
                    $('#liveAlertBtn').prop('disabled', false);
                    $("#liveAlertBtn").trigger("click");
                }
            }
        },
        error: function (error) {
            console.log(error);
        }
    });

}


