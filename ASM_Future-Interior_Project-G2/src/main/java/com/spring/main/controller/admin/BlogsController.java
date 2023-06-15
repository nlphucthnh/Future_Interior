package com.spring.main.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.entity.BaiDang;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.export.BaiDangExport;
import com.spring.main.service.SessionService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerContainer;

import com.spring.main.dao.BaiDangDAO;
import com.spring.main.dao.TaiKhoanDAO;

@Controller
public class BlogsController {

	@Autowired
	BaiDangDAO baiDangDAO;

	@Autowired
	TaiKhoanDAO taiKhoanDAO;

	@Autowired
	ServletContext app;

	@Autowired
	HttpSession Session;

	private Sort sort = Sort.by(Direction.DESC, "tieuDeBaiDang");
	private String informatinSort[] = { "DESC", "tieuDeBaiDang", "", "" };
	private BaiDang baiDang = new BaiDang();
	private BaiDangExport BlogExport = new BaiDangExport();
	private final String excelFilePath = "..\\ASM_Future-Interior_Project-G2\\src\\main\\resources\\static\\fileExport\\baiDangs.xlsx";
	@Autowired
	HttpServletRequest request;

//	get data in table and paging
	@GetMapping("/Manager/blog")
	public String getDataTable(Model model, @RequestParam("page") Optional<Integer> page) {
		baiDang.setTaiKhoanBaiDang((TaiKhoan) Session.getAttribute("Admin"));
		try {
			Pageable pageable = PageRequest.of(page.orElse(0), 10, sort);
			Page<BaiDang> PageBD = baiDangDAO.findByTieuDeBaiDang(informatinSort[2], pageable);
			// informatinSort[2] là tên tiêu đề tìm kiếm
			if (PageBD.getTotalElements() == 0) {
				model.addAttribute("disnableTable", true);
			} else {
				model.addAttribute("disnableTable", false);
				model.addAttribute("PageBD", PageBD);
			}
			if (baiDang.getIdBaiDang() > 0) {
				model.addAttribute("ChangeTab", true);
			} else {
				model.addAttribute("ChangeTab", false);
			}
			File excel = new File(excelFilePath);
			if(excel.exists()) {
				excel.delete();
				BlogExport.writeExcelBlogs(baiDangDAO.findAll(), excelFilePath);
			}
			model.addAttribute("duongDan", excel.getName());
			model.addAttribute("BaiDang", baiDang);
			model.addAttribute("inforSort", informatinSort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Manager-blog-page";
	}

// Set type sort in table
	@PostMapping("/Manager/blog/sort")
	public String SortTableBlogs(@RequestParam(name = "methodSort") String methodSort,
			@RequestParam(name = "fieldSort") String fieldSort) {
		sort = Sort.by(methodSort.equals("DESC") ? Direction.DESC : Direction.ASC, fieldSort);
		informatinSort[0] = methodSort;
		informatinSort[1] = fieldSort;
		return "redirect:/Manager/blog";
	}

// Set content search in table	
	@PostMapping("/Manager/blog/search")
	public String SearchTableBlogs(@RequestParam(name = "nameBlogs") String nameBlogs) {
		informatinSort[2] = nameBlogs;
		return "redirect:/Manager/blog";
	}

//Update blogs
	@PostMapping("/Manager/blog/update")
	public String UpdateElementBlog(@Valid @ModelAttribute("BaiDang") BaiDang baiDangModel, BindingResult result,
			Model model) {
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<BaiDang> PageBD = baiDangDAO.findByTieuDeBaiDang(informatinSort[2], pageable);
		model.addAttribute("disnableTable", false);
		model.addAttribute("PageBD", PageBD);
		model.addAttribute("inforSort", informatinSort);
		baiDang.setData(baiDangModel);
		System.out.println(baiDang.getIdBaiDang());
		if (result.hasErrors()) {
			return "Manager-blog-page";
		} else {
			baiDangDAO.save(baiDang);
			baiDang = new BaiDang();
			return "redirect:/Manager/blog";
		}

	}

	@PostMapping("/Manager/blog/create")
	public String CreateElementBlog(@Valid @ModelAttribute("BaiDang") BaiDang baiDangModel, BindingResult result,
			Model model) {
		Pageable pageable = PageRequest.of(0, 10, sort);
		Page<BaiDang> PageBD = baiDangDAO.findByTieuDeBaiDang(informatinSort[2], pageable);
		model.addAttribute("disnableTable", false);
		model.addAttribute("PageBD", PageBD);
		model.addAttribute("inforSort", informatinSort);
		System.out.println(baiDang.getIdBaiDang());
		if (result.hasErrors()) {
			return "Manager-blog-page";
		} else {
			baiDangDAO.save(baiDangModel);
			baiDang = new BaiDang();
			return "redirect:/Manager/blog";
		}

	}

// set informatin to form
	@PostMapping("/Manager/blog/edit/{idBlogs}")
	public String EditElementBlog(@PathVariable("idBlogs") Integer idBlogs, Model model) {
		baiDang = baiDangDAO.findByIdBaiDang(idBlogs);
		informatinSort[3] = baiDang.getTaiKhoanBaiDang().getTenDangNhap();
//		System.out.println(baiDang.getTieuDeBaiDang());
		if (baiDang != null) {
			return "redirect:/Manager/blog";
		}
		return "error-404";
	}

//	handle method delete element in table
	@PostMapping("/Manager/blog/delete/{idBlogs}")
	public String DeleteElementBlog(@PathVariable("idBlogs") Integer idBlogs, Model model) {
		BaiDang baiDangDelete = baiDangDAO.findByIdBaiDang(idBlogs);
		if (baiDangDelete != null) {
			baiDangDAO.delete(baiDangDelete);
			baiDang = new BaiDang();
		}
		return "redirect:/Manager/blog";
	}

// clear form	
	@PostMapping("/Manager/blog/clearform")
	public String ClearFormBlogs() {
		baiDang = new BaiDang();
		return "redirect:/Manager/blog";
	}

	
}
