package com.spring.main.controller.admin;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.spring.main.dao.TaiKhoanDAO;
import com.spring.main.entity.BaiDang;
import com.spring.main.entity.TaiKhoan;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AccountManagerController {

	@Autowired
	HttpSession session;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;
	private Sort sort = Sort.by(Direction.DESC, "tenDangNhap");
	private String informatinSort[] = { "DESC", "false", "", "false", "true" };
	private TaiKhoan taiKhoan = new TaiKhoan();
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	@GetMapping("/Manager/account")
	public String getDateAccount(Model model, @RequestParam("page") Optional<Integer> page) {
		System.out.println(session.getAttribute("PhucThinh"));
		try {
			Pageable pageable = PageRequest.of(page.orElse(0), 10, sort);
			Page<TaiKhoan> PageTK = taiKhoanDAO.findByTenDangNhap(informatinSort[2],
					Boolean.parseBoolean(informatinSort[1]), pageable);
			if (PageTK.getTotalElements() == 0) {
				model.addAttribute("disnableTable", true);
			} else {
				model.addAttribute("disnableTable", false);
				model.addAttribute("PageTK", PageTK);
			}

			if (!(taiKhoan.getTenDangNhap() == null)) {
				model.addAttribute("ChangeTab", true);
			} else {
				model.addAttribute("ChangeTab", false);
			}
			model.addAttribute("TaiKhoan", taiKhoan);
			model.addAttribute("inforSort", informatinSort);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "Manager/Manager-account-page";
	}

	// Set type sort in table
	@PostMapping("/Manager/account/sort")
	public String SortTableAccount(@RequestParam(name = "methodSort") String methodSort,
			@RequestParam(name = "fieldSort") String fieldSort) {
		informatinSort[0] = methodSort;
		informatinSort[1] = fieldSort;
		return "redirect:/Manager/account";
	}

	// Set content search in table
	@PostMapping("/Manager/account/search")
	public String SearchTableAccount(@RequestParam(name = "usernameSearch") String usernameSearch) {

		informatinSort[2] = usernameSearch;
		return "redirect:/Manager/account";
	}

//	handle method delete element in table
	@PostMapping("/Manager/account/delete/{tenDangNhap}")
	public String DeleteElementAccount(@PathVariable("tenDangNhap") String tenDangNhap, Model model) {
		TaiKhoan taiKhoanDelete = taiKhoanDAO.findByTenDangNhap(tenDangNhap);
		if (taiKhoanDelete != null) {
			taiKhoanDAO.delete(taiKhoanDelete);
			taiKhoanDelete = new TaiKhoan();
		}
		return "redirect:/Manager/account";
	}

	// set informatin to form
	@PostMapping("/Manager/account/edit/{tenDangNhap}")
	public String EditElementBlog(@PathVariable("tenDangNhap") String tenDangNhap, Model model) {
		taiKhoan = taiKhoanDAO.findByTenDangNhap(tenDangNhap);		
		if (taiKhoan != null) {
			informatinSort[3] = String.valueOf(taiKhoan.isVaiTro());
			informatinSort[4] = String.valueOf(taiKhoan.isTrangThai());
			return "redirect:/Manager/account";
		}
		return "error-404";
	}

	// clear form
	@PostMapping("/Manager/account/clearform")
	public String ClearFormBlogs() {
		taiKhoan = new TaiKhoan();
		System.out.println(taiKhoan);
		return "redirect:/Manager/account";
	}

	@PostMapping("/Manager/account/update")
	public String UpdateElementBlog(@Valid @ModelAttribute("TaiKhoan") TaiKhoan taiKhoanModel, BindingResult result,
			Model model) {
		System.out.println(taiKhoanModel.getTenDangNhap());

		if (result.hasErrors()) {
			Pageable pageable = PageRequest.of(0, 10, sort);
			Page<TaiKhoan> PageTK = taiKhoanDAO.findByTenDangNhap(informatinSort[2],
					Boolean.parseBoolean(informatinSort[1]), pageable);
			if (PageTK.getTotalElements() == 0) {
				model.addAttribute("disnableTable", true);
			} else {
				model.addAttribute("disnableTable", false);
				model.addAttribute("PageTK", PageTK);
			}
			model.addAttribute("inforSort", informatinSort);
			return "Manager/Manager-account-page";
		} else {
			taiKhoanDAO.save(taiKhoanModel);
			taiKhoan = new TaiKhoan();
			return "redirect:/Manager/account";
		}

	}

}
