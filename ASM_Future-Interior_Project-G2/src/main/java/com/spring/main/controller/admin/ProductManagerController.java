package com.spring.main.controller.admin;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.main.dao.ChatLieuDAO;
import com.spring.main.dao.HinhAnhDAO;
import com.spring.main.dao.KhuyenMaiDAO;
import com.spring.main.dao.NhaSanXuatDAO;
import com.spring.main.dao.PhanNhomLoaiDAO;
import com.spring.main.dao.SanPhamChatLieuDAO;
import com.spring.main.dao.SanPhamDAO;
import com.spring.main.entity.ChatLieu;
import com.spring.main.entity.HinhAnh;
import com.spring.main.entity.KhuyenMai;
import com.spring.main.entity.NhaSanXuat;
import com.spring.main.entity.PhanNhomLoai;
import com.spring.main.entity.SanPham;
import com.spring.main.entity.SanPhamChatLieu;

import jakarta.validation.Valid;
import lombok.var;

@Controller
public class ProductManagerController {
    @Autowired
    SanPhamDAO sanPhamDAO;

    @Autowired
    PhanNhomLoaiDAO nhomLoaiDAO;

    @Autowired
    KhuyenMaiDAO khuyenMaiDAO;

    @Autowired
    ChatLieuDAO chatLieuDAO;

    @Autowired
    NhaSanXuatDAO nhaSanXuatDAO;

    @Autowired
    SanPhamChatLieuDAO sanPhamChatLieuDAO;

    @Autowired
    HinhAnhDAO hinhAnhDAO;

    private Sort sort = Sort.by(Direction.DESC, "tenSanPham");
    private SanPham sanPham = new SanPham();
    private List<ChatLieu> LISTCL_ADD = new ArrayList<>();
    private List<HinhAnh> LISTHA_ADD = new ArrayList<>();
    private String information[] = { "DESC", "tenSanPham", "" };// DESC,tenSanPham,search
    private boolean flag = false;

    // ----------------------HÀM XỬ LÝ TRANG----------------------//

    // Lấy dữ liệu và phân trang
    @GetMapping("/Manager/product")
    public String ManageProductPage(Model model, @RequestParam("page") Optional<Integer> pageSP) {
        Pageable pageableSP = PageRequest.of(pageSP.orElse(0), 5, sort);
        Page<SanPham> pageSanPham = sanPhamDAO.findByTenSanPham(information[2], pageableSP);
        model.addAttribute("pageSP", pageSanPham);
        // sanPham = sanPhamDAO.findByIdSanPham("SP002");
        model.addAttribute("SanPham", sanPham);
        model.addAttribute("flag", flag);
        model.addAttribute("inforSort", information);
        return "Manager/Manager-product-page";
    }

    // Xử lý tìm kiếm bằng tên sản phẩm
    @PostMapping("/Manager/product/search")
    public String searchProduct(@RequestParam(name = "searchText") String searchText) {
        information[2] = searchText;
        return "redirect:/Manager/product";
    }

    // Xử lý sắp xếp sản phẩm theo các thuộc tính
    @PostMapping("/Manager/product/sort")
    public String sortProduct(@RequestParam(name = "sortOrder") String sortOrder,
            @RequestParam(name = "sortField") String sortField) {
        sort = Sort.by(sortOrder.equals("DESC") ? Direction.DESC : Direction.ASC, sortField);
        information[0] = sortOrder;
        information[1] = sortField;
        return "redirect:/Manager/product";
    }

    // Xử lý xóa sản phẩm
    @RequestMapping("/Manager/product/delete")
    public String deleteProduct(@RequestParam(name = "idSanPham") String idSanPham) {
        SanPham sanPhamGet = sanPhamDAO.findByIdSanPham(idSanPham);
        if (sanPhamGet != null) {
            sanPhamDAO.delete(sanPhamGet);
        }
        return "redirect:/Manager/product";
    }

    // Xử lý hiển thị nội dung từ bảng sang form chi tiết hơn
    @PostMapping("/Manager/product/eidt")
    public String editProduct(@RequestParam(name = "idSanPham") String idSanPham, Model model) {
        SanPham sanPhamGet = sanPhamDAO.findByIdSanPham(idSanPham);
        System.out.println(sanPhamGet.getIdSanPham());
        if (sanPhamGet != null) {
            model.addAttribute("SanPham", sanPhamGet);
            flag = true;
        } else {
            model.addAttribute("SanPham", sanPham);
            flag = false;
        }
        Pageable pageableSP = PageRequest.of(0, 5, sort);
        Page<SanPham> pageSanPham = sanPhamDAO.findByTenSanPham(information[2], pageableSP);
        model.addAttribute("pageSP", pageSanPham);
        model.addAttribute("flag", flag);
        model.addAttribute("inforSort", information);
        return "Manager/Manager-product-page";
    }

    @RequestMapping("/Manager/product/create")
    public String createProduct(@ModelAttribute("SanPham") SanPham sanPham, Model model,
            @RequestParam(name = "length-product", defaultValue = "0.0") double length,
            @RequestParam(name = "width-product", defaultValue = "0.0") double width,
            @RequestParam(name = "height-product", defaultValue = "0.0") double height) {
        sanPham.setNhaSanXuat(nhaSanXuatDAO.findByIdNhaSanXuat(sanPham.getNhaSanXuat().getIdNhaSanXuat()));
        sanPham.setPhanNhomLoai(nhomLoaiDAO.findByIdPhanNhomLoai(sanPham.getPhanNhomLoai().getIdPhanNhomLoai()));
        if(sanPham.getKhuyenMai() != null){
            sanPham.setKhuyenMai(khuyenMaiDAO.findByIdKhuyenMai(sanPham.getKhuyenMai().getIdKhuyenMai()));
        }else {
            sanPham.setKhuyenMai(null);
        }
        SanPham sanPhamDataBase = sanPhamDAO.save(sanPham);
        if (LISTCL_ADD.size() != 0) {
            for (ChatLieu chatLieu : LISTCL_ADD) {
                SanPhamChatLieu sanPhamChatLieu = new SanPhamChatLieu();
                sanPhamChatLieu.setChatLieuSPCL(chatLieu);
                sanPhamChatLieu.setSanPhamSPCL(sanPhamDataBase);
                sanPhamChatLieuDAO.save(sanPhamChatLieu);
            }
        }
        if (LISTHA_ADD.size() != 0) {
            for (HinhAnh hinhAnh : LISTHA_ADD) {
                hinhAnh.setSanPhamHA(sanPhamDataBase);
                hinhAnhDAO.save(hinhAnh);
            }
        }
        LISTCL_ADD.clear();
        LISTHA_ADD.clear();
        flag = false;
        return "redirect:/Manager/product";
    }

    @RequestMapping("/Manager/product/update")
    public String updateProduct(@ModelAttribute("SanPham") SanPham sanPham, Model model,
            @RequestParam(name = "length-product", defaultValue = "0.0") double length,
            @RequestParam(name = "width-product", defaultValue = "0.0") double width,
            @RequestParam(name = "height-product", defaultValue = "0.0") double height) {
        sanPham.setNhaSanXuat(nhaSanXuatDAO.findByIdNhaSanXuat(sanPham.getNhaSanXuat().getIdNhaSanXuat()));
        sanPham.setPhanNhomLoai(nhomLoaiDAO.findByIdPhanNhomLoai(sanPham.getPhanNhomLoai().getIdPhanNhomLoai()));
        if(sanPham.getKhuyenMai() != null){
            sanPham.setKhuyenMai(khuyenMaiDAO.findByIdKhuyenMai(sanPham.getKhuyenMai().getIdKhuyenMai()));
        }else {
            sanPham.setKhuyenMai(null);
        }
        SanPham sanPhamDataBase = sanPhamDAO.save(sanPham);
        if (LISTCL_ADD.size() != 0) {
            for (ChatLieu chatLieu : LISTCL_ADD) {
                SanPhamChatLieu sanPhamChatLieu = new SanPhamChatLieu();
                sanPhamChatLieu.setChatLieuSPCL(chatLieu);
                sanPhamChatLieu.setSanPhamSPCL(sanPhamDataBase);
                sanPhamChatLieuDAO.save(sanPhamChatLieu);
            }
        }
        if (LISTHA_ADD.size() != 0) {
            for (HinhAnh hinhAnh : LISTHA_ADD) {
                hinhAnh.setSanPhamHA(sanPhamDataBase);
                hinhAnhDAO.save(hinhAnh);
            }
        }
        LISTCL_ADD.clear();
        LISTHA_ADD.clear();
        flag = false;
        return "redirect:/Manager/product";
    }

    @RequestMapping("/Manager/product/clear")
    public String clearForm(){
        sanPham = new SanPham();
        flag = false;
        return "redirect:/Manager/product";
    }

    // ----------------------HÀM LẤY DỮ LIỆU JSON----------------------//

    @ResponseBody
    @RequestMapping("/Manager/product/addChatLieu")
    public ChatLieu addChatLieuProduct(@RequestParam(name = "idChatLieu") int idChatLieu) {
        ChatLieu chatLieu = chatLieuDAO.findByIdChatLieu(idChatLieu);
        if (chatLieu != null) {
            LISTCL_ADD.add(chatLieu);
        }
        return chatLieu;
    }

    @ResponseBody
    @RequestMapping("/Manager/product/deleteChatLieu")
    public boolean deleteChatLieuProduct(@RequestParam(name = "idSanPhamChatLieu") int idSanPhamChatLieu) {
        System.out.println(idSanPhamChatLieu);
        SanPhamChatLieu sanPhamChatLieu = sanPhamChatLieuDAO.findByIdSanPhamChatLieu(idSanPhamChatLieu);
        if (sanPhamChatLieu != null) {
            sanPhamChatLieuDAO.delete(sanPhamChatLieu);
            return true;
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("/Manager/product/deleteHinhAnh")
    public boolean deleteHinhAnhProduct(@RequestParam(name = "tenHinhAnh") String tenHinhAnh) {
        System.out.println(tenHinhAnh);
        HinhAnh hinhAnhs = hinhAnhDAO.findByTenHinhAnh(tenHinhAnh);
        if (hinhAnhs != null) {
            hinhAnhDAO.delete(hinhAnhs);
            return true;
        }
        return false;
    }

    @ResponseBody
    @RequestMapping("/Manager/product/addHinhAnh")
    public HinhAnh addHinhAnhProduct(@RequestParam(name = "idHinhAnh") String idHinhAnh,
            @RequestParam(name = "size") int dungLuong, @RequestParam(name = "name") String tenHinhAnh) {
        HinhAnh hinhAnh = new HinhAnh();
        hinhAnh.setIdHinhAnh(idHinhAnh);
        hinhAnh.setDungLuongAnh(dungLuong);
        hinhAnh.setTenHinhAnh(tenHinhAnh);
        LISTHA_ADD.add(hinhAnh);
        return hinhAnh;
    }

    @ResponseBody
    @RequestMapping("/Manager/product/json")
    public boolean checkDuplicateId(@RequestParam(name = "idProduct") String idProduct) {
        SanPham sanPham = sanPhamDAO.findByIdSanPham(idProduct);
        if (sanPham == null) {
            return true;
        }
        return false;
    }

    // ---------------------HÀM LẤY DỮ LIỆU TỪ DATABASE-----------------------//

    // getData
    @ModelAttribute("NhaSanXuat")
    public List<NhaSanXuat> getDataNSX() {
        List<NhaSanXuat> ListNSX = nhaSanXuatDAO.findAll();
        return ListNSX;
    }

    @ModelAttribute("PhanNhomLoai")
    public List<PhanNhomLoai> getDataPNL() {
        List<PhanNhomLoai> ListNL = nhomLoaiDAO.findAll();
        return ListNL;
    }

    @ModelAttribute("KhuyenMai")
    public List<KhuyenMai> getDataKM() {
        List<KhuyenMai> ListKM = khuyenMaiDAO.findAll();
        return ListKM;
    }

    @ModelAttribute("ChatLieu")
    public List<ChatLieu> getDataCL() {
        List<ChatLieu> ListCL = chatLieuDAO.findAll();
        return ListCL;
    }

}
