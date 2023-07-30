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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
*查询所有动态，在页面上能够展示出帖子表里面所有用户的动态
 */
@WebServlet("/selectAllPostServlet")
public class SelectAllPostServlet extends HttpServlet {
    private PostService postService = new PostService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 调用PostService里面的selectAllP（）方法，查询数据库动态表里面所有的动态，封装集合，将它们保存在req域中请求转发到主页
        List<Post> posts = postService.selectAllP();

        for(Post post : posts){
            // 在每个post对象里面保存头像路径
            int user_id = post.getUser_id();
            Image image = imageService.selectHeadImage(user_id);
            if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
                // 设置默认图片路径
                image = new Image();
                image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
            }
            post.setImage_url(image.getImage_url());

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
        req.setAttribute("posts",posts);
        req.getRequestDispatcher("/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
