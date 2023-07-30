package ST.web.post;


import ST.pojo.Post;
import ST.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*用户删除动态，接收来自个人主页（wb_PersonalPage.jsp）的参数进行删除，删除后将其传递给/selectByUserIdDeleteServlet进行参数的剩余动态查询
 */
@WebServlet("/deletePostServlet")
public class DeletePostServlet extends HttpServlet {
    PostService postService = new PostService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String post_id = req.getParameter("post_id");

        postService.deletePost(Integer.parseInt(post_id));
        req.getRequestDispatcher("/selectByUserIdDeleteServlet").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
