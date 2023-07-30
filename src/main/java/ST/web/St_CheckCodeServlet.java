package ST.web;

import ST.util.CheckCodeUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*
*验证码生成
 */
@WebServlet("/st_CheckCodeServlet")
public class St_CheckCodeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 生成验证码
        ServletOutputStream os = resp.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(100,50,os,4);

        // 存入Session，提供给注册的servlet验证
        HttpSession session = req.getSession();
        session.setAttribute("checkCodeGen",checkCode);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
