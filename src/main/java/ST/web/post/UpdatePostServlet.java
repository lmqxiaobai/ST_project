package ST.web.post;

import ST.pojo.Post;
import ST.service.PostService;

import javax.print.DocFlavor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
动态编辑修改
 */
@WebServlet("/updatePostServlet")
public class UpdatePostServlet extends HttpServlet {
    private PostService postService = new PostService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String post_id = req.getParameter("post_id");
        String content = req.getParameter("content");
        System.out.println(content);
        Post post = new Post();
        post.setPost_id(Integer.parseInt(post_id));
        post.setContent(content);
        postService.updatePost(post);
        req.getRequestDispatcher("/selectByUserIdDeleteServlet").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
