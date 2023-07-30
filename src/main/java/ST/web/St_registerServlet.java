package ST.web;

import ST.pojo.User;
import ST.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/st_registerServlet")
public class St_registerServlet extends HttpServlet {
    private UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取浏览器提交的请求数据
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 封装对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);


        // 获取用户输入的验证码
        String checkCode = req.getParameter("checkCode");

        // 获取程序生成的验证码，从session获取，在获取程序验证码的servlet中设置了并保存了session的键值对，所以这里根据对应的键获取值
        HttpSession session = req.getSession();
        String checkCodeGen = (String) session.getAttribute("checkCodeGen");

        // 比对，因为验证码不区分大小写，所以采用equalsIgnoreCase
        if(!checkCodeGen.equalsIgnoreCase(checkCode)){
            // 用户输入和程序生成的不一样，验证码错误，将错误提示存入req请求域中
            req.setAttribute("register_msg2","验证码错误");
            // 因为涉及数据传递，所以用请求转发
            req.getRequestDispatcher("/st_register.jsp").forward(req,resp);
            // 不允许注册
            return;
        }
        // 调用service注册
        boolean flag = service.register(user);

        // 判断注册成功与否
        if(flag){
            // 注册成功跳转到登录页面
            req.setAttribute("register_msg","注册成功，请登录");
            // 转发
            req.getRequestDispatcher("/st_login.jsp").forward(req,resp);
        }else{
            // 注册失败，跳转到注册页面
            req.setAttribute("register_msg","用户名已存在");
            // 转发
            req.getRequestDispatcher("/st_register.jsp").forward(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
