package com.spring.main.controller.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.main.dao.DonHangDAO;
import com.spring.main.dao.GioHangDAO;
import com.spring.main.dao.ThongTinGiaoHangDAO;
import com.spring.main.entity.DonHang;
import com.spring.main.entity.DonHangChiTiet;
import com.spring.main.entity.GioHang;
import com.spring.main.entity.TaiKhoan;
import com.spring.main.entity.ThongTinGiaoHang;
import com.spring.main.service.SessionService;

import jakarta.validation.Valid;

@Controller
public class AddressController {

    @Autowired
	SessionService session;

    @Autowired
    ThongTinGiaoHangDAO giaoHangDAO;

    @Autowired
    GioHangDAO gioHangDAO;

    @Autowired
    DonHangDAO donHangDAO;
    private ThongTinGiaoHang thongTinGiaoHang = new ThongTinGiaoHang();
    private boolean messageFlag = false;
    @GetMapping("/User/address")
    public String getAddressPage(Model model){
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        int idOrdering = (int) session.get("idOrdering");
        DonHang donHang = donHangDAO.findByIdDonHang(idOrdering);
		model.addAttribute("TaiKhoanUser", taiKhoan);
        model.addAttribute("Ordering", donHang);
        model.addAttribute("thongTinGiaoHang", thongTinGiaoHang);
        if(messageFlag){
           model.addAttribute("messageFlag", messageFlag);
        }
        return "User-adreess-page";
    }


    @PostMapping("/User/address")
    public String saveAddressData(@Valid @ModelAttribute("thongTinGiaoHang") ThongTinGiaoHang thongTinGiaoHang, BindingResult result , Model model){
        TaiKhoan taiKhoan = (TaiKhoan) session.get("TaiKhoanUser");
        int idOrdering = (int) session.get("idOrdering");
        DonHang donHang = donHangDAO.findByIdDonHang(idOrdering);
        System.out.println(thongTinGiaoHang.getTenNguoiMua());
        if(result.hasErrors()) {
            model.addAttribute("TaiKhoanUser", taiKhoan);
            model.addAttribute("Ordering", donHang);
            System.out.println("Đã qua đây 01");
            return "User-adreess-page";
        }else {
            thongTinGiaoHang.setDonHangTTGH(donHang);
            giaoHangDAO.save(thongTinGiaoHang);
            for (DonHangChiTiet chiTiet :donHang.getListDHCT()) {
                GioHang gioHang = gioHangDAO.findBySanPhamGHAndTaiKhoanGH(chiTiet.getSanPhamDHCT(), taiKhoan);
                if(gioHang != null) {
                    gioHangDAO.delete(gioHang);
                }
            }
            messageFlag = true;
        }
        return "redirect:/User/address";
    }

}
