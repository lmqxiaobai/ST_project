package ST.web.image;

import ST.pojo.Image;
import ST.service.ImageService;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;

// 上传头像
@WebServlet("/st_addHeadImageServlet")
@MultipartConfig
public class St_addHeadImageServlet extends HttpServlet {
    private ImageService imageService = new ImageService();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String user_id = req.getParameter("user_id");

        // 保存上传的文件到用户目录
        Part filePart = req.getPart("image");
        String fileName = getFileName(filePart);
        if(fileName != null){
            String filePath = req.getServletContext().getRealPath("/");
            String path = "imgs/" + fileName;
            filePart.write(filePath + "/" + path);

            // 检查数据库中是否已存在图片
            Image existingImage = imageService.selectHeadImage(Integer.parseInt(user_id));
            if (existingImage != null) {
                // 如果已存在图片，则修改图片路径
                existingImage.setImage_url(path);
                imageService.updateImage(existingImage);
            } else {
                // 如果不存在图片，则添加图片
                Image image = new Image();
                image.setUser_id(Integer.parseInt(user_id));
                image.setImage_url(path);
                imageService.addImage(image);
            }
        }
        req.getRequestDispatcher("/selectByUserIdDeleteServlet").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
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

