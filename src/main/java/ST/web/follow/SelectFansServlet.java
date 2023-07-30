package ST.web.follow;

import ST.pojo.Image;
import ST.pojo.User;
import ST.service.FollowService;
import ST.service.ImageService;
import ST.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*
* 查询我的粉丝
 */
@WebServlet("/selectFansServlet")
public class SelectFansServlet extends HttpServlet {
    private FollowService followService = new FollowService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收前端的参数
        String following_id = req.getParameter("following_id");

        // 对user_id进行转换
        if(following_id != null) {
            List<User> users = followService.getFansFollow(Integer.parseInt(following_id));

            for(User user : users){
                int user_id = user.getUser_id();
                Image image = imageService.selectHeadImage(user_id);
                // 检查图片路径是否为空,如果为空则默认图片
                if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
                    // 设置默认图片路径
                    image = new Image();
                    image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
                }
                user.setImage_url(image.getImage_url());
            }
            // 存在req域中，请求转发到个人主页
            req.setAttribute("users", users);
            req.getRequestDispatcher("/wb_FansFollow.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}

