package ST.web.follow;

import ST.pojo.Follow;
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
import java.util.Date;
import java.util.List;

/*
*用户关注关系的判断
 */
@WebServlet("/checkIfFollowedServlet")
public class CheckIfFollowedServlet extends HttpServlet {
    private FollowService followService = new FollowService();
    private PostService postService = new PostService();
    private UserService userService = new UserService();
    private ImageService imageService = new ImageService();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的following_id参数,获取被关注用户的id
        String followingId = req.getParameter("following_id");
        int following_id = Integer.parseInt(followingId);

        // 从session获取关注用户id
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int follower_id = user.getUser_id();

        // 通过checkIfFollowed()方法来判断是否关注（如果关注则大于0，没有关注等于0）
        int followStatus = followService.checkIfFollowed(follower_id,following_id);
        String followButtonText = (followStatus > 0) ? "取消关注" : "关注";

        // 点击关注按钮的处理方法
        if (followButtonText.equals("关注")) {
            Follow follow = new Follow();
            follow.setFollower_id(follower_id);
            follow.setFollowing_id(following_id);
            follow.setCreated_at(new Date());
            // 如果是已经关注了，那么按钮显示“取消关注”
            followService.addFollow(follow);
            followButtonText = "取消关注";
        } else {
            // 如果没有关注，按钮显示“关注”
            followService.deleteFollow(follower_id,following_id);
            followButtonText = "关注";
        }

        // 查询该用户的所有动态，根据被关注者id
        List<Post> posts = postService.selectUserP(following_id);
        // 添加图片
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
        User user1 = userService.getUserById(following_id);
        Image image = imageService.selectHeadImage(user1.getUser_id());
        // 检查图片路径是否为空
        if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
            // 设置默认图片路径
            image = new Image();
            image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
        }

        user1.setImage_url(image.getImage_url());
        // 存在req域中，请求转发到个人主页
        req.setAttribute("user",user1);
        req.setAttribute("posts", posts);
        req.setAttribute("followButtonText", followButtonText);
        req.setAttribute("following_id",following_id);
        req.setAttribute("user_id1",follower_id);
        req.getRequestDispatcher("/wb_OtherPage.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
