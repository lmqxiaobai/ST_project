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
import java.util.ArrayList;
import java.util.List;

/*
*用户个人动态，通过用户id查询该用户发布的所有动态,当用户点击个人主页时，显示出该用户的所有动态
 */
@WebServlet("/selectByUserIdServlet")
public class SelectByUserIdServlet extends HttpServlet {
    private PostService postService = new PostService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的参数
        String user_id = req.getParameter("user_id");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        // 对user_id进行转换
        if(user_id != null) {
            List<Post> posts = postService.selectUserP(Integer.parseInt(user_id));
            for(Post post:posts){
                // 在每个动态里面保存动态图片,通过查询得到数据库里面关于该动态的所有动态图片集合，然后将其设置到该动态中去显示出来
                int post_id1 = post.getPost_id();
                List<Image> images = imageService.selectPostImage(post_id1);
                List<String> image_urls = new ArrayList<>();
                for(Image image1 : images){
                    String imageUrl = image1.getImage_url();
                    if(imageUrl != null && !imageUrl.endsWith("null")){
                        image_urls.add(imageUrl);
                    }
                }
                post.setImage_urls(image_urls);
            }

            Image image = imageService.selectHeadImage(Integer.parseInt(user_id));
            if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
                // 设置默认图片路径
                image = new Image();
                image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
            }
            user.setImage_url(image.getImage_url());
            // 存在req域中，请求转发到个人主页
            req.setAttribute("posts", posts);
            req.setAttribute("user",user);
            req.getRequestDispatcher("/wb_PersonalPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
