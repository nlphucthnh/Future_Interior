package com.spring.main.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.ChatLieuDAO;
import com.spring.main.dao.KhuyenMaiDAO;
import com.spring.main.dao.NhaSanXuatDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.entity.ChatLieu;
import com.spring.main.entity.KhuyenMai;
import com.spring.main.entity.NhaSanXuat;
import com.spring.main.entity.PhanNhomLoai;

import jakarta.validation.Valid;

@Controller
public class FilterController {

	@Autowired
	ChatLieuDAO chatLieuDAO;
	@Autowired
	PhanNhomLoaiDAO nhomLoaiDAO;
	@Autowired
	NhaSanXuatDAO nhaSanXuatDAO;
	@Autowired
	KhuyenMaiDAO khuyenMaiDAO;
	private boolean buttonUpdate = false;
	private int arryTab[] = { 0, 0, 0, 0 };
	private ChatLieu chatLieuMain = new ChatLieu();
	private NhaSanXuat nhaSanXuatMain = new NhaSanXuat();
	private PhanNhomLoai nhomLoaiMain = new PhanNhomLoai();
	private KhuyenMai khuyenMaiMain = new KhuyenMai();

	@GetMapping("/Manager/filter")
	public String getDataTable(Model model, @RequestParam("pageCL") Optional<Integer> pageCL,
			@RequestParam("pageNSX") Optional<Integer> pageNSXS, @RequestParam("pageNL") Optional<Integer> pageNL) {
		Pageable pageableCL = PageRequest.of(pageCL.orElse(0), 5);
		Pageable pageableNSX = PageRequest.of(pageNSXS.orElse(0), 5);
		Pageable pageableNL = PageRequest.of(pageNL.orElse(0), 5);

		if (arryTab[0] != pageableCL.getPageNumber()) {
			model.addAttribute("TabPin", "cl");
			arryTab[0] = pageableCL.getPageNumber();
		} else if (arryTab[1] != pageableNSX.getPageNumber()) {
			model.addAttribute("TabPin", "nsx");
			arryTab[1] = pageableNSX.getPageNumber();
		} else if (arryTab[2] != pageableNL.getPageNumber()) {
			model.addAttribute("TabPin", "nl");
			arryTab[2] = pageableNL.getPageNumber();
		}
		Page<ChatLieu> pageChatLieu = chatLieuDAO.findAll(pageableCL);
		Page<PhanNhomLoai> pageNhomLoai = nhomLoaiDAO.findAll(pageableNL);
		Page<NhaSanXuat> pageNSX = nhaSanXuatDAO.findAll(pageableNSX);
		List<KhuyenMai> ListKM = khuyenMaiDAO.findAll();
		model.addAttribute("ListKM", ListKM);
		model.addAttribute("buttonUpdate", buttonUpdate);
		model.addAttribute("khuyenMaiMain", khuyenMaiMain);
		model.addAttribute("pageChatLieu", pageChatLieu);
		model.addAttribute("pageNhomLoai", pageNhomLoai);
		model.addAttribute("pageNSX", pageNSX);
		return "Manager-filter-page";
	}

// method khuyen mai

	@PostMapping("/Manager/filter/khuyenMai/eidt/{id}")
	public String setFormKhuyenMai(@PathVariable("id") String idKhuyenMai) {
		KhuyenMai khuyenMai = khuyenMaiDAO.findByIdKhuyenMai(idKhuyenMai);
		if (khuyenMai != null) {
			buttonUpdate = true;
			khuyenMaiMain = khuyenMai;
		}
		return "redirect:/Manager/filter";
	}

	@PostMapping("/Manager/filter/khuyenMai/delete/{id}")
	public String deleteKhuyenMai(@PathVariable("id") String idKhuyenMai) {
		KhuyenMai khuyenMai = khuyenMaiDAO.findByIdKhuyenMai(idKhuyenMai);
		if (khuyenMai != null) {
			khuyenMaiDAO.delete(khuyenMai);
		}
		return "redirect:/Manager/filter";
	}

	public void fillData(Model model) {
		Pageable pageableCL = PageRequest.of(0, 5);
		Pageable pageableNSX = PageRequest.of(0, 5);
		Pageable pageableNL = PageRequest.of(0, 5);
		Page<ChatLieu> pageChatLieu = chatLieuDAO.findAll(pageableCL);
		Page<PhanNhomLoai> pageNhomLoai = nhomLoaiDAO.findAll(pageableNL);
		Page<NhaSanXuat> pageNSX = nhaSanXuatDAO.findAll(pageableNSX);
		List<KhuyenMai> ListKM = khuyenMaiDAO.findAll();
		model.addAttribute("ListKM", ListKM);
		model.addAttribute("pageChatLieu", pageChatLieu);
		model.addAttribute("pageNhomLoai", pageNhomLoai);
		model.addAttribute("pageNSX", pageNSX);
	}

	@PostMapping("/Manager/filter/khuyenMai/create")
	public String createKhuyenMai(@Valid @ModelAttribute("khuyenMaiMain") KhuyenMai khuyenMai, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			fillData(model);
			return "Manager-filter-page";
		}else {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String startDate = simpleDateFormat.format(khuyenMai.getNgayBatDau());
			String endDate = simpleDateFormat.format(khuyenMai.getNgayKetThuc());
			startDate = startDate.replace("/", "");
			endDate = endDate.replace("/", "");
			String maKhuyenMai = "KM-" + startDate + "-" + endDate + "-" + String.valueOf((int)khuyenMai.getPhamTramKhuyenMai());
			khuyenMai.setIdKhuyenMai(maKhuyenMai);
			System.out.println(maKhuyenMai);
			khuyenMaiDAO.save(khuyenMai);
			khuyenMaiMain = new KhuyenMai();
			return "redirect:/Manager/filter";
		}
		
	}
	
	@PostMapping("/Manager/filter/khuyenMai/update")
	public String updateKhuyenMai(@Valid @ModelAttribute("khuyenMaiMain") KhuyenMai khuyenMai, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			fillData(model);
			return "Manager-filter-page";
		}else {
			khuyenMaiDAO.save(khuyenMai);
			khuyenMaiMain = new KhuyenMai();
			return "redirect:/Manager/filter";
		}
		
	}
	
	@PostMapping("/Manager/filter/khuyenMai/clear")
	public String clearKhuyenMai() {
		buttonUpdate = false;
		khuyenMaiMain = new KhuyenMai();
		return "redirect:/Manager/filter";
	}

// method nhomloai

	@ResponseBody
	@RequestMapping("/Manager/filter/nhomLoai")
	public PhanNhomLoai getDataNhomLoai(@RequestParam("idnl") int idNhomLoai) {
		nhomLoaiMain = nhomLoaiDAO.findByIdPhanNhomLoai(idNhomLoai);
		return nhomLoaiMain;
	}

	@PostMapping("/Manager/filter/delete/nhomLoai/{idnhomloai}")
	public String deleteNhomLoai(@PathVariable("idnhomloai") int idNhomLoai) {
		PhanNhomLoai nhomLoai = nhomLoaiDAO.findByIdPhanNhomLoai(idNhomLoai);
		if (nhomLoai != null) {
			nhomLoaiDAO.delete(nhomLoai);
			arryTab[2] = -1;
		}
		return "redirect:/Manager/filter";
	}

	@ResponseBody
	@PostMapping("/Manager/filter/nhomLoai/create")
	public PhanNhomLoai createNhomLoai(@RequestBody PhanNhomLoai nhomLoai) {
		PhanNhomLoai nhomLoai2 = nhomLoaiDAO.save(nhomLoai);
		return nhomLoai2;
	}

	@ResponseBody
	@PostMapping("/Manager/filter/nhomLoai/update")
	public PhanNhomLoai updateNhomLoai(@RequestBody PhanNhomLoai nhomLoai) {
		nhomLoaiDAO.save(nhomLoai);
		return nhomLoai;
	}

//	Method form chất liệu

	@ResponseBody
	@RequestMapping("/Manager/filter/chatLieu")
	public ChatLieu getDataChatLieu(@RequestParam("idlc") int idChatLieu) {
		chatLieuMain = chatLieuDAO.findByIdChatLieu(idChatLieu);
		return chatLieuMain;
	}

	@PostMapping("/Manager/filter/delete/chatlieu/{idchatlieu}")
	public String deleteChatLieu(@PathVariable("idchatlieu") int idchatlieu) {
		ChatLieu chatLieu = chatLieuDAO.findByIdChatLieu(idchatlieu);
		if (chatLieu != null) {
			chatLieuDAO.delete(chatLieu);
			arryTab[0] = -1;
		}
		return "redirect:/Manager/filter";
	}

	@ResponseBody
	@PostMapping("/Manager/filter/chatLieu/create")
	public ChatLieu createChatLieu(@RequestBody ChatLieu chatLieu) {
		ChatLieu chatLieu2 = chatLieuDAO.save(chatLieu);
		return chatLieu2;
	}

	@ResponseBody
	@PostMapping("/Manager/filter/chatLieu/update")
	public ChatLieu updateChatLieu(@RequestBody ChatLieu chatLieu) {
		chatLieuDAO.save(chatLieu);
		return chatLieu;
	}

// method nhà sản xuất	
	@ResponseBody
	@RequestMapping("/Manager/filter/nhaSanXuat")
	public NhaSanXuat getDataNhaSanXuat(@RequestParam("idnsx") int idNhaSanXuat) {
		nhaSanXuatMain = nhaSanXuatDAO.findByIdNhaSanXuat(idNhaSanXuat);
		return nhaSanXuatMain;
	}

	@PostMapping("/Manager/filter/nhaSanXuat/delete/{idnhasanxuat}")
	public String deleteNhaSanXuat(@PathVariable("idnhasanxuat") int idNhaSanXuat) {
		NhaSanXuat nhaSanXuat = nhaSanXuatDAO.findByIdNhaSanXuat(idNhaSanXuat);
		if (nhaSanXuat != null) {
			nhaSanXuatDAO.delete(nhaSanXuat);
			arryTab[1] = -1;
		}
		return "redirect:/Manager/filter";
	}

	@ResponseBody
	@PostMapping("/Manager/filter/nhaSanXuat/create")
	public NhaSanXuat createNhaSanXuat(@RequestBody NhaSanXuat nhaSanXuat) {
		NhaSanXuat nhaSanXuat2 = nhaSanXuatDAO.save(nhaSanXuat);
		return nhaSanXuat2;
	}

	@ResponseBody
	@PostMapping("/Manager/filter/nhaSanXuat/update")
	public NhaSanXuat updateNhaSanXuat(@RequestBody NhaSanXuat nhaSanXuat) {
		System.out.println(nhaSanXuat.getIdNhaSanXuat());
		nhaSanXuatDAO.save(nhaSanXuat);
		return nhaSanXuat;
	}

}
