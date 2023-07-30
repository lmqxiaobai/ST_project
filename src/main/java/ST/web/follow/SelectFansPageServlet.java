package ST.web.follow;

import ST.pojo.Image;
import ST.pojo.Post;
import ST.pojo.User;
import ST.service.ImageService;
import ST.service.PostService;
import ST.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* 点击我粉丝用户名，查询粉丝的个人动态，接收从wb_FansFollow.jsp传递过来的参数，然后返回显示参数用户的个人动态
 */
@WebServlet("/selectFansPageServlet")
public class SelectFansPageServlet extends HttpServlet {
    private PostService postService = new PostService();
    private UserService userService = new UserService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的post_id
        String user1_id = req.getParameter("user1_id");

        // 根据user_id去查询相应的动态 调用postService中的selectUserP(user_id)方法，因为它能够查询并返回用户表里面的用户信息。
        // 这样post对象里面才有用户名信息，才能进行调用，查都没查到用户信息就别想调用了
        List<Post> posts = postService.selectUserP(Integer.parseInt(user1_id));
        for(Post post:posts) {
            // 在每个动态里面保存动态图片,通过查询得到数据库里面关于该动态的所有动态图片集合，然后将其设置到该动态中去显示出来
            int post_id1 = post.getPost_id();
            List<Image> images = imageService.selectPostImage(post_id1);
            List<String> image_urls = new ArrayList<>();
            for (Image image1 : images) {
                String imageUrl = image1.getImage_url();
                if (imageUrl != null && !imageUrl.endsWith("null")) {
                    image_urls.add(imageUrl);
                }
            }
            post.setImage_urls(image_urls);
        }
        // 为了能够动态的显示用户名，在post集合里面无法使用，就单独根据user_id查询对应的user对象，然后将这个对象请求转发到前端，供前端去查询用户名
        User user = userService.getUserById(Integer.parseInt(user1_id));
        Image image = imageService.selectHeadImage(user.getUser_id());
        // 检查图片路径是否为空
        if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
            // 设置默认图片路径
            image = new Image();
            image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
        }

        user.setImage_url(image.getImage_url());
        // 存在req域中，请求转发到个人主页
        req.setAttribute("user", user);
        req.setAttribute("posts", posts);
        req.getRequestDispatcher("/wb_OtherPage.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
