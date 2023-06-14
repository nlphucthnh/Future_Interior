// package com.spring.main.controller.thien;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import com.spring.main.dao.DonHangChiTietDAO;
// import com.spring.main.dao.TaiKhoanDAO;
// import com.spring.main.entity.DonHangChiTiet;
// import com.spring.main.entity.TaiKhoan;



// @Controller
// public class quenmatkhau {

//   @Autowired
//     TaiKhoanDAO tkDAO;

//     @Autowired
//     DonHangChiTietDAO dhctDAO;


//     @GetMapping("/demo")
//     public String demo(Model model) {
//          TaiKhoan people = tkDAO.findByTenDangNhap("thienlv");
//         model.addAttribute("people", people);
//         List<DonHangChiTiet> tkmuahang = dhctDAO.findByTaiKhoan("thienlv");
//         model.addAttribute("donhangct", tkmuahang);
//         return "demo";
//     }

// @PostMapping("/upload-avatar")
// public ResponseEntity<String> uploadAvatar(@RequestParam("avatar") MultipartFile avatar) {
//     // Xử lý tên tệp tin
//     String fileName = avatar.getOriginalFilename();
    
//     // Thực hiện các xử lý khác với tệp tin
    
//     return ResponseEntity.ok("File name: " + fileName);
// }


// }
