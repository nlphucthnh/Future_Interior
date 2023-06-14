$(document).ready(function () {
    $('#avatar-input').change(function () {
        var input = this;
        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#avatar-img').attr('src', e.target.result);

                // Gửi tệp ảnh mới đến controller
                var formData = new FormData();
                formData.append('avatar', input.files[0]);

                $.ajax({
                    url: '/upload',
                    type: 'POST',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function (response) {
                        console.log('Avatar uploaded successfully');
                        // Xử lý kết quả trả về từ controller (nếu cần)
                    },
                    error: function (xhr, status, error) {
                        console.error('Error uploading avatar:', error);
                    }
                });
            };
            reader.readAsDataURL(input.files[0]);
        }
    });
});

function toggleButtonVisibility() {
    var button = document.getElementById('confirm-button');
    button.style.display = button.style.display === 'none' ? 'block' : 'none';
}

function checkFileSelected() {
    var fileInput = document.getElementById('avatar-input');
    var button = document.getElementById('confirm-button');
    if (fileInput.value) {
        button.style.display = 'block';
    } else {
        button.style.display = 'none';
    }
}

function notify() {
    Swal.fire({
        title: 'Đổi Avatar thành công',
        icon: 'success',
        confirmButtonColor: '#3085d6',
        confirmButtonText: 'OK'
    }).then((result) => {
        if (result.isConfirmed) {
            window.location.href = '/person';
        }
    });
}