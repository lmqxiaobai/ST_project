package ST.dao;

import ST.pojo.Image;
import ST.pojo.Post;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ImageMapper {
    /*
    * 增加头像图片
     */
    void addHeadImage(Image image);

    /*
    * 通过user_id查询image对象
     */
    Image selectHeadImage(int user_id);

    /*
    * 修改头像图片
     */
    @Update("update st_image set image_url=#{image_url} where image_id=#{image_id}")
    void updateImage(Image image);

    /*
    * 添加动态图片
     */
    void addPostImage(Image image);

    /*
    *通过post_id查询image集合
     */
    List<Image> selectPostImage(int post_id);

}
