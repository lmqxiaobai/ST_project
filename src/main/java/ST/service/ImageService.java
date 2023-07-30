package ST.service;


import ST.dao.ImageMapper;
import ST.pojo.Image;
import ST.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class ImageService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    // 添加头像图片
    public void addImage(Image image){
        SqlSession sqlSession = factory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        imageMapper.addHeadImage(image);
        sqlSession.commit();
        sqlSession.close();
    }

    // 通过user_id查询image对象
    public Image selectHeadImage(int user_id){
        SqlSession sqlSession = factory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        Image image = imageMapper.selectHeadImage(user_id);
        sqlSession.close();
        return image;
    }

    // 修改图片路径
    public void updateImage(Image image){
        SqlSession sqlSession = factory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        imageMapper.updateImage(image);

        sqlSession.commit();
        sqlSession.close();
    }

    // 添加动态图片
    public void addPostImage(Image image){
        SqlSession sqlSession = factory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        imageMapper.addPostImage(image);
        sqlSession.commit();
        sqlSession.close();
    }

    // 通过post_id查询image对象
    public List<Image> selectPostImage(int post_id){
        SqlSession sqlSession = factory.openSession();
        ImageMapper imageMapper = sqlSession.getMapper(ImageMapper.class);
        List<Image> images = imageMapper.selectPostImage(post_id);
        sqlSession.close();
        return images;
    }
}
