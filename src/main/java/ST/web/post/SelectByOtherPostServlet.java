package ST.web.post;

import ST.pojo.Image;
import ST.pojo.Post;
import ST.pojo.User;
import ST.service.FollowService;
import ST.service.ImageService;
import ST.service.PostService;
import ST.service.UserService;

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
* 点击用户名进入用户空间，查看其他人的个人动态
* 在调用转发的时候，最关键的是看你调用的对象里面是否存在该有的信息，这个要看你查询数据库表的时候是否返回了该信息，可以将查询语句在数据库里面运行一次
 */
@WebServlet("/selectByOtherPostServlet")
public class SelectByOtherPostServlet extends HttpServlet {
    private PostService postService = new PostService();
    private UserService userService = new UserService();
    private ImageService imageService = new ImageService();
    private FollowService followService = new FollowService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的post_id
        String post_id = req.getParameter("post_id");
        int postId = Integer.parseInt(post_id);

        // 通过post_id去查询对应的post对象
        Post post = postService.selectByIdPost(postId);
        // 然后获取对象里面的user_id
        int user_id = post.getUser_id();

        HttpSession session = req.getSession();
        User userD =(User) session.getAttribute("user");
        int user_id1 = userD.getUser_id();

        // 判断登录用户与其他用户的关系，是否关注
        int followStatus = followService.checkIfFollowed(user_id1,user_id);
        String followButtonText = (followStatus > 0) ? "取消关注" : "关注";

        // 根据user_id去查询相应的动态 调用postService中的selectUserP(user_id)方法，因为它能够查询并返回用户表里面的用户信息。
        // 这样post对象里面才有用户名信息，才能进行调用，查都没查到用户信息就别想调用了
        if(user_id1 == user_id){
            // 对user_id进行转换
            // 调用postService中的selectUserP(user_id)方法，因为它能够查询并返回用户表里面的用户信息。
            // 这样post对象里面才有用户名信息，才能进行调用，查都没查到用户信息就别想调用了
            List<Post> posts = postService.selectUserP(user_id1);
            for(Post post1:posts){
                // 在每个动态里面保存动态图片,通过查询得到数据库里面关于该动态的所有动态图片集合，然后将其设置到该动态中去显示出来
                int post_id1 = post1.getPost_id();
                List<Image> images = imageService.selectPostImage(post_id1);
                List<String> image_urls = new ArrayList<>();
                for(Image image1 : images){
                    String imageUrl = image1.getImage_url();
                    if(imageUrl != null && !imageUrl.endsWith("null")){
                        image_urls.add(imageUrl);
                    }
                }
                post1.setImage_urls(image_urls);
            }
            // 存在req域中，请求转发到个人主页
            req.setAttribute("posts", posts);
            req.getRequestDispatcher("/wb_PersonalPage.jsp").forward(req, resp);
        }else {
            List<Post> posts = postService.selectUserP(user_id);
            for(Post post2:posts){
                // 在每个动态里面保存动态图片,通过查询得到数据库里面关于该动态的所有动态图片集合，然后将其设置到该动态中去显示出来
                int post_id2 = post2.getPost_id();
                List<Image> images = imageService.selectPostImage(post_id2);
                List<String> image_urls = new ArrayList<>();
                for(Image image1 : images){
                    String imageUrl = image1.getImage_url();
                    if(imageUrl != null && !imageUrl.endsWith("null")){
                        image_urls.add(imageUrl);
                    }
                }
                post2.setImage_urls(image_urls);
            }

            // 为了能够动态的显示用户名，在post集合里面无法使用，就单独根据user_id查询对应的user对象，然后将这个对象请求转发到前端，供前端去查询用户名
            User user = userService.getUserById(user_id);
            Image image = imageService.selectHeadImage(user_id);
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
            req.setAttribute("followButtonText", followButtonText);
            req.setAttribute("user_id1",user_id1);
            req.getRequestDispatcher("/wb_OtherPage.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

}
