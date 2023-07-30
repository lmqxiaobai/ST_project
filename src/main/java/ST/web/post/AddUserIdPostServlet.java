package ST.web.post;


import ST.pojo.Image;
import ST.pojo.Post;
import ST.pojo.User;
import ST.service.ImageService;
import ST.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
*用户发布动态，查询动态
 */
@WebServlet("/addUserIdPostServlet")
@MultipartConfig
public class AddUserIdPostServlet extends HttpServlet {
    private PostService postService = new PostService();
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取session对象，从session里面得到原先登录时存储的user对象
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUser_id();

        // 获取前端wb_HomePage.jsp提交上来的数据，对客户端传来的数据进行编码，避免中文乱码
        req.setCharacterEncoding("UTF-8");
        String content = req.getParameter("content");

        //封装对象
        Post post = new Post();
        post.setUser_id(user_id);
        post.setContent(content);
        post.setCreated_at(new Date());
        if(post.getContent()!=null){
            // 调用service方法，添加动态后返回当前动态的id，供后面添加动态图片用
            int post_id = postService.addPost(post);
            List<Part> imageParts = (List<Part>) req.getParts();

            // 保存上传的文件到用户目录
            for(Part filePart : imageParts){
                if(filePart != null  && filePart.getSize() > 0) {
                    String fileName = getFileName(filePart);
                    String filePath = req.getServletContext().getRealPath("/");
                    // 图片保存在imgs/past包下
                    String path = "imgs/past/" + fileName;
                    filePart.write(filePath + "/" + path);
                    // 设置生成图片对象，然后执行动态图片添加方法
                    Image image = new Image();
                    image.setPost_id(post_id);
                    image.setImage_url(path);
                    imageService.addPostImage(image);
                }
            }


            // 查询所有的动态，然后获取每个动态的用户user_id，然后以此查询对应的用户头像，如果没有头像则默认头像，然后添加到动态里面
            List<Post> posts = postService.selectAllP();
            for(Post post1 : posts){
                // 在每个post对象里面保存头像路径
                int user_id1 = post1.getUser_id();
                Image image = imageService.selectHeadImage(user_id1);
                if (image == null || image.getImage_url() == null || image.getImage_url().isEmpty()) {
                    // 设置默认图片路径
                    image = new Image();
                    image.setImage_url(req.getContextPath()+"/imgs/defaulthead.jpg"); // 设置默认图片路径
                }
                post1.setImage_url(image.getImage_url());

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
            // 最后返回所有动态，让前端能够查询到头像路径
            req.setAttribute("posts",posts);
            req.getRequestDispatcher("/wb_HomePage.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    // 文件路径
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] elements = contentDisposition.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                String name = element.substring(element.indexOf('=') + 1).trim().replace("\"", "");
                if(name.equals("")){
                    return null;
                }else {
                    return System.currentTimeMillis()+name;
                }
            }
        }
        return null;
    }
}
