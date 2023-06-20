var listIdGioHang = [];
var listIdSanPham = [];
var subTotal = 0;
var Total = 0;
var ondelete = false;
const formatter = new Intl.NumberFormat('it-IT', {
    style: 'currency',
    currency: 'VND',
});
$("input:checkbox.input-check").change(function () {
    ondelete = false;
    if (this.checked) {
        $.ajax({
            type: "POST",
            url: "/User/cart/json/idGioHang?idGioHang=" + $(this).val() + "&&delete=" + ondelete,
            contentType: "application/json",
            success: function (response) {
                var discount = 1 - $("#km" + response.idGioHang).val() / 100;
                if (!isNaN(discount)) {
                    subTotal += (response.sanPhamGH.giaSanPham * discount) * response.soLuong;
                } else {
                    subTotal += response.sanPhamGH.giaSanPham * response.soLuong;
                }
                $("#subtotal").text(formatter.format(subTotal));
                $('#Total').text(formatter.format(subTotal + 30000));
                listIdSanPham.push(response.sanPhamGH.idSanPham);
            }
        });

        listIdSanPham.forEach(element => {
            console.log(element);
        });
    }
});
$("input:checkbox.input-check").click(function () {
    ondelete = true;
    if (!$(this).is(":checked")) {
        var valueUnCheck = $(this).val();
        $.ajax({
            type: "POST",
            url: "/User/cart/json/idGioHang?idGioHang=" + valueUnCheck + "&&delete=" + ondelete,
            contentType: "application/json",
            success: function (response) {
                if (subTotal > 0) {
                    var discount = 1 - $("#km" + response.idGioHang).val() / 100;
                    if (!isNaN(discount)) {
                        subTotal -= (response.sanPhamGH.giaSanPham * discount) * response.soLuong;
                    } else {
                        subTotal -= response.sanPhamGH.giaSanPham * response.soLuong;
                    }
                    $("#subtotal").text(formatter.format(subTotal));
                    $('#Total').text(formatter.format(subTotal + 30000));
                    const index = listIdSanPham.indexOf(response.sanPhamGH.idSanPham);
                    if (index > -1) {
                        listIdSanPham.splice(index, 1);
                    }
                }
            }
        });
    }
});


// $("#pay-bill-id").click(function () { 
//     if(listIdSanPham.length > 0){
//         $("#pay-bill-id").prop('disabled', false);
//     }else {
//         $("#pay-bill-id").prop('disabled', true);
//     }    
// });

function deleteCart(idGioHang) {
    let message = "Bạn muốn xóa sản phẩm này ?";
    if (confirm(message) == true) {
        const Http = new XMLHttpRequest();
        $("#cart" + idGioHang).remove();
        const url = '/User/cart/delete/' + idGioHang;
        Http.open("POST", url);
        Http.send();
    }
}

function refreshCart(item, iStat) {
    item.soLuong = $(".qty")[iStat.index].value;
    if (item.soLuong <= 0) {
        alert("Số lượng không thể âm!")
        $(".qty")[iStat.index].value = 1;
        return;
    }
    if (item.soLuong <= item.sanPhamGH.soLuong) {
        $.ajax({
            type: "POST",
            url: "/User/cart/json",
            data: JSON.stringify(item),
            contentType: "application/json",
            success: function (response) {
                var discount = 1 - $("#km" + response.idGioHang).val() / 100;
                console.log(discount)
                var cutrrency = 0;
                if (isNaN(discount)) {
                    cutrrency = response.sanPhamGH.giaSanPham * response.soLuong;
                    $(".amount")[iStat.index].textContent = formatter.format(cutrrency)
                } else {
                    cutrrency = (response.sanPhamGH.giaSanPham * discount) * response.soLuong;
                    $(".amount")[iStat.index].textContent = formatter.format(cutrrency)
                }
            },
            error: function (error) {
                console.log(error);
            }
        });
    } else {
        alert("Số lượng trong kho không đủ !")
    }
}
