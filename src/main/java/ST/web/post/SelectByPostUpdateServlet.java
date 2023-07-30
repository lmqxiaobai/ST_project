package ST.web.post;

import ST.pojo.Post;
import ST.pojo.User;
import ST.service.PostService;
import ST.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/*
 * 修改用户
 */
@WebServlet("/selectByPostUpdateServlet")
public class SelectByPostUpdateServlet extends HttpServlet {
    private PostService postService = new PostService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的post_id
        String post_id = req.getParameter("post_id");
        // 通过post_id去查询对应的post对象
        Post post = postService.selectByIdPost(Integer.parseInt(post_id));
        // 存在req域中，请求转发到个人主页
        req.setAttribute("post", post);
        req.getRequestDispatcher("/wb_UpdatePage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

}