package ST.web.post;

import ST.pojo.Image;
import ST.pojo.Post;
import ST.pojo.User;
import ST.service.ImageService;
import ST.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/*
 *deletePostServlet传递过来，利用用户id参数去查询用户的剩余动态
 */
@WebServlet("/selectByUserIdDeleteServlet")
public class SelectByUserIdDeleteServlet extends HttpServlet {
    private PostService postService = new PostService();
    private ImageService imageService = new ImageService();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的参数
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUser_id();
        Image image = imageService.selectHeadImage(user_id);
        if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
            // 设置默认图片路径
            image = new Image();
            image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
        }
        user.setImage_url(image.getImage_url());
        // 对user_id进行转换
        // 调用postService中的selectUserP(user_id)方法，因为它能够查询并返回用户表里面的用户信息。
        // 这样post对象里面才有用户名信息，才能进行调用，查都没查到用户信息就别想调用了
        List<Post> posts = postService.selectUserP(user_id);
        // 存在req域中，请求转发到个人主页

        req.setAttribute("posts", posts);
        req.setAttribute("user",user);
        req.getRequestDispatcher("/wb_PersonalPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
