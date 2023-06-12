// Example starter JavaScript for disabling form submissions if there are invalid fields



(function () {
    'use strict'
  
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.querySelectorAll('.needs-validation')
  
    // Loop over them and prevent submission
    Array.prototype.slice.call(forms)
      .forEach(function (form) {
        form.addEventListener('submit', function (event) {
          if (!form.checkValidity()) {
            event.preventDefault()
            event.stopPropagation()
          }
  
          form.classList.add('was-validated')
        }, false)
      })
  })()



// $(document).ready(function() {
//   $('#toggle_password').click(function() {
//     var passwordInput = $('#password');
//     var passwordFieldType = passwordInput.attr('type');

//     if (passwordFieldType === 'password') {
//       passwordInput.attr('type', 'text');
//       $('#toggle_password i').removeClass('bi-eye-slash').addClass('bi-eye');
//     } else {
//       passwordInput.attr('type', 'password');
//       $('#toggle_password i').removeClass('bi-eye').addClass('bi-eye-slash');
//     }
//   });
// });

function togglePassword(inputId) {
  const passwordInput = document.getElementById(inputId);
  const toggleButton = document.getElementById(`toggle_${inputId}`);

  if (passwordInput.type === "password") {
    passwordInput.type = "text";
    toggleButton.innerHTML = '<i class="bi bi-eye"></i>';
  } else {
    passwordInput.type = "password";
    toggleButton.innerHTML = '<i class="bi bi-eye-slash"></i>';
  }
}