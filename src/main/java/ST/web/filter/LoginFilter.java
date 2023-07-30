package ST.web.filter;

import org.apache.ibatis.annotations.Param;

import javax.servlet.*;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;


/*
 *登录验证的过滤器
 */
// 实现Filter接口，重写里面的三个方法
// 注解拦截资源路径，/*表示全部拦截
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 对request进行一个强转
        /*
         *ServletRequest与HttpServletRequest都是接口，HttpServletRequest继承自ServletRequest
         * 而获取session的方法存在于HttpServletRequest这个子类中，父类无法调用子类的方法，因此需要将父类定义的对象强转为子类，以此去调用获取session的方法
         */
        HttpServletRequest req = (HttpServletRequest) request;

        // 为了让登录注册不被过滤，所以要判断与登录注册是否相关,然后将这些请求排除在外
        String[] urls = {"index.jsp","/st_login.jsp","/imgs/","/css/","/st_loginServlet", "/st_register.jsp","/st_registerServlet","/st_CheckCodeServlet","/st_selectUserServlet","/register.html"};
        // 获取当前访问的路径资源
        String url = req.getRequestURI().toString();
        // 循环判断
        for (String u : urls){
            if(url.contains(u)){
                // 属于登录注册相关路径，放行
                chain.doFilter(request, response);
                // 找到后如果不结束这个方法，还会返回来继续执行循环，如果用break，只是结束循环，还会继续执行下面的代码
                return;
            }
        }

        // 1.获取在登录界面保存的session值，用户对象user
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        // 2.判断user是否存在，以此来判断用户是否登录
        if(user != null){
            // 用户登录过
            // 放行
            chain.doFilter(request, response);
        }else{
            // 用户没有登录，存储提示信息，跳转到登录界面（因为涉及数据传递，所以用请求转发）
            req.setAttribute("login_msg","您尚为登录");
            req.getRequestDispatcher("/st_login.jsp").forward(req,response);
        }

    }

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
}
