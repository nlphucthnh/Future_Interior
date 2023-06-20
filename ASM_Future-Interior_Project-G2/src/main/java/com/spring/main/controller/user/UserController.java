package com.spring.main.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinTaiKhoan;
import com.spring.main.service.CookieService;
import com.spring.main.service.DisplayHeader;
import com.spring.main.service.SessionService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	SessionService session;
	@Autowired
	CookieService cookieService;
	@Autowired
	HttpSession sessions;
	@Autowired
	TaiKhoanDAO tkdao;
	@Autowired
	DisplayHeader displayHeader;

	// @GetMapping("/login-page")
	// public String getLoginPage() {
	// return "dangnhap";
	// }

	// @GetMapping("/sign-up-page")
	// public String getSiginPage() {
	// return "dangky";
	// }

	@GetMapping("/home-page")
	public String getHomePage(Model model) {
		displayHeader.displayHeader(model);
		return "index";
	}

	// @GetMapping("/blog-page")
	// public String getBlogPage() {
	// return "blog";
	// }

	@GetMapping("/blogs-item-page")
	public String getBlogItemPage(Model model) {
		displayHeader.displayHeader(model);
		return "blog-item";
	}

	@GetMapping("/about-page")
	public String getAboutPage(Model model) {
		displayHeader.displayHeader(model);
		return "about";
	}

	@GetMapping("/profile/{user}")
	public String profile(Model model, @ModelAttribute("nguoiDung") TaiKhoan nguoidung,
			// Lấy tennguoidung từ đường dẫn
			@PathVariable("user") String username) {
		displayHeader.displayHeader(model);
		if (displayHeader.getNguoiDung() == null) {
			return "redirect:/home-page";
		} else {
			// lấy người dùng từ session || cookie
			nguoidung = displayHeader.getNguoiDung();
			String tenNguoiDung = displayHeader.getNguoiDung().getTenDangNhap(); // lấy tên nd
			model.addAttribute("tenNguoiDung", tenNguoiDung); // add vào hêlo
			// gán người dùng lên form (người dùng đã đăng nhập)
			model.addAttribute("nguoiDung", tkdao.findById(username).get());
			return "profile";
		}
	}

	@PostMapping("/profile/{tenNguoiDung}")
	public String updateProfile(Model model, @Valid @ModelAttribute("nguoiDung") TaiKhoan nguoidung,
			BindingResult result) {
		if (!result.hasErrors()) {
			tkdao.save(nguoidung);
			model.addAttribute("message", "Cập nhật email thành công");
		}
		String tenNguoiDung = displayHeader.getNguoiDung().getTenDangNhap(); // lấy tên nd
		model.addAttribute("tenNguoiDung", tenNguoiDung); // add vào hêlo
		displayHeader.displayHeader(model); // add lại email người dùng trên header
		return "profile";
	}

	// @GetMapping("/contact-page")
	// public String getContactPage() {
	// return "contact";
	// }
	// @GetMapping("/product-page")
	// public String getProductPage() {
	// return "product";
	// }

	// @GetMapping("/products-list-page")
	// public String getProductList() {
	// return "product-list";
	// }
	// @GetMapping("/product-item-page")
	// public String getProductItem() {
	// return "product-item";
	// }
	// @GetMapping("/cart-page")
	// public String getCartPage() {
	// return "cart";
	// }

	// @GetMapping("/adreess-page")
	// public String getAddressPage() {
	// return "adreess";
	// }
	// @GetMapping("/pay-page")
	// public String getPayPage() {
	// return "pay";
	// }

	// @GetMapping("/demo")
	// public String getDemo() {
	// return "demo";
	// }

}
