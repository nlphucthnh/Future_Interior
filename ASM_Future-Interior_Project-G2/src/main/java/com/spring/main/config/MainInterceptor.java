package com.spring.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.main.entity.TaiKhoan;
import com.spring.main.service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class MainInterceptor implements HandlerInterceptor {

    @Autowired
    SessionService session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        TaiKhoan taiKhoanAdmin = (TaiKhoan) session.get("Admin");
        // String uri = request.getRequestURI();
        if (taiKhoanAdmin == null) {
            response.sendRedirect("/Manager/login");
            return false;
        }else if(!taiKhoanAdmin.isVaiTro()){
            response.sendRedirect("/error-404");
            return false;
        }
        return true;
    }
}
