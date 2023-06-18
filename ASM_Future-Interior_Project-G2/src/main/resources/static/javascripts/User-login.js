$("#btnLogin01").hide();
function checkFormLogin() {
    var flag = [true, true];  // flag = [0,1]
    if ($("#tenDangNhap").val() == "") {
        $("#tenDangNhap-text").show(); // hiện 
        flag[0] = false; // flag = 0
    } else {
        $("#tenDangNhap-text").hide(); // ẩn 
    }
    if ($("#matKhau").val() == "") {
        $("#matKhau-text").show();
        flag[1] = false;  // flag = 1
    } else {
        $("#matKhau-text").hide();
    }

    if (flag[0] && flag[1]) {
        return true;
    }
    return false;
}

$(".login-input").keyup(function (e) {
    if (e.keyCode == 13) {
        LoginCheckError();
    }
});

function LoginCheckError() {
    if (!checkFormLogin()) {
        return;
    }
    var tenDangNhap = $("#tenDangNhap").val();
    var matKhau = $("#matKhau").val();
    console.log(tenDangNhap);
    $.ajax({
        type: "POST",
        url: "/User/login/json/data?tenDangNhap=" + tenDangNhap,
        contentType: "application/json",
        success: function (response) {
            if (response == "") {
                alert("Đăng nhập thất bại, có thể sai tên đăng nhập hoặc mật khẩu");
            } else {
                if (response.matKhau != matKhau) {
                    alert("Đăng nhập thất bại, có thể sai tên đăng nhập hoặc mật khẩu");
                } else if (!response.trangThai) {
                    alert("Tài khoản đã tạm dừng hoạt động !");
                } else {
                    alert("Đăng nhập thành công");
                    $("#btnLogin01").prop('disabled', false);
                    $("#btnLogin01").trigger('click');
                }
            }

        },
        error: function (error) {
            console.log(error);
        }
    });


}

