package ST.web;



import ST.pojo.Image;
import ST.pojo.User;
import ST.service.ImageService;
import ST.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

/*
 *当前后两个页面有数据传递时，例如查询了数据需要在页面显示时，用请求转发（地址栏不发生变化）
 * 当前后两个页面没有数据传递，例如做了更新等操作跳转到其他页面，用重定向（地址栏发生变化）
 */
@WebServlet("/st_loginServlet")
public class St_loginServlet extends HttpServlet {
    // 创建service层的对象，方便调用方法
    private UserService service = new UserService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.接收客户端用户名和密码（使用request对象获取请求数据,先设置编码，避免中文乱码）
        req.setCharacterEncoding("UTF-8");
        // 获取客户端请求的对应数据内容（也就是你输入的用户名和密码）
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 从数据库中获取用户密码对象
        User user = service.login(username,password);
        // 获取"记住用户"
        String remember = req.getParameter("remember");

        // 判断数据库中是否存在该用户对象
        if(user != null){
            // 判断用户是否勾选记住我，为了预防空指针异常，所以remember写在括号里
            if("1".equals(remember)){
                // 勾选了，发送cookie（共享数据、会话跟踪，cookie保存在客户端）
                // 1.创建cookie对象，因为cookie不支持中文，因此要使用URL编码
                username = URLEncoder.encode(username,"utf-8");
                password = URLEncoder.encode(password,"utf-8");

                Cookie c_username = new Cookie("username",username);
                Cookie c_password = new Cookie("password",password);

                // 2.因为默认的cookie在客户端关闭后就会销毁，但记住用户名所想起到的作用就是下次打开客户端可以不用输入账号密码，因此这里要设置cookie存活的时间
                c_username.setMaxAge(60*60*24*7);
                c_password.setMaxAge(60*60*24*7);

                // 3.向客户端发送cookie（响应数据）
                resp.addCookie(c_username);
                resp.addCookie(c_password);
            }else {
                // 将时间设置为0，自动删除
                username = URLEncoder.encode(username,"utf-8");
                password = URLEncoder.encode(password,"utf-8");

                Cookie c_username = new Cookie("username",username);
                Cookie c_password = new Cookie("password",password);

                c_username.setMaxAge(0);
                c_password.setMaxAge(0);

                resp.addCookie(c_username);
                resp.addCookie(c_password);
            }

            // 因为是一次会话多次请求，所以要用到会话跟踪技术（将登录成功的user对象存储到session）
            // 获取session对象
            int user_id = user.getUser_id();
            Image image = imageService.selectHeadImage(user_id);
            if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
                // 设置默认图片路径
                image = new Image();
                image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
            }
            user.setImage_url(image.getImage_url());

            HttpSession session = req.getSession();
            // 存储数据到session中,供后面验证用户对象提供数据
            session.setAttribute("user",user);

            // 登录成功，跳转到主界面，因为前后两个页面没有数据传递，所以用重定向
            // 重定向
            String contextPath = req.getContextPath();        //动态访问虚拟目录
            resp.sendRedirect(contextPath+"/selectAllPostServlet");
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write("登录成功");
        }else {
            // 登录失败，请求转发
            // 存储数据到request域中,因为前后两个页面有数据传递，所以用请求转发跳转回登录页面
            req.setAttribute("login_msg","用户名或密码错误");
            req.getRequestDispatcher("/st_login.jsp").forward(req,resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
