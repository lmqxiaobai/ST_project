package ST.web.image;

import ST.pojo.Image;
import ST.service.ImageService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/st_selectImageUrlServlet")
public class St_selectImageUrlServlet extends HttpServlet {
    private ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_id = req.getParameter("user_id");
        Image image = imageService.selectHeadImage(Integer.parseInt(user_id));
        String image_url = image.getImage_url();
        // 设置响应类型为文本
        resp.setContentType("text/plain");
        // 将image_url作为响应返回给前端
        resp.getWriter().write(image_url);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
