package ST.service;

import ST.dao.PostMapper;
import ST.pojo.Post;
import ST.util.SqlSessionFactoryUtils;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class PostService {
    // 获取数据库连接工厂
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    /*
    *发布动态(添加)
     */
    public int addPost(Post post){
        SqlSession sqlSession = factory.openSession();
        try {
        PostMapper postMapper = sqlSession.getMapper(PostMapper.class);
        postMapper.add(post);
        int post_id = postMapper.getLastInsertId();
        sqlSession.commit();
        return post_id;
        } finally {
            sqlSession.close();
        }
    }

    /*
    *查询所有动态，将动态对象封装成集合，供servlet调用
     */
    public List<Post> selectAllP(){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        List<Post> posts = mapper.selectAll();
        sqlSession.close();
        return posts;
    }

    /*
    *通过用户id，查询该用户的所有动态
     */
    public List<Post> selectUserAllP(int user_id){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        List<Post> posts = mapper.selectByUserId(user_id);
        sqlSession.close();
        return posts;
    }

    /*
    *根据用户id可以查询到p.post_id, u.username, p.content,u.user_id
     */
    public List<Post> selectUserP(int user_id){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        List<Post> posts = mapper.selectByUId(user_id);
        sqlSession.close();
        return posts;
    }
    /*
    *通过帖子id查询对应动态对象
     */
    public Post selectByIdPost(int post_id){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        Post post = mapper.selectByIdPost(post_id);
        sqlSession.close();
        return post;
    }

    /*
    *修改帖子内容
     */
    public void updatePost(Post post){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        mapper.updateContent(post);
        // 一定记得要提交事务，不然数据就不会被修改
        sqlSession.commit();
        sqlSession.close();
    }
    /*
    *通过帖子id删除帖子
     */
    public void deletePost(int post_id){
        SqlSession sqlSession = factory.openSession();
        PostMapper mapper = sqlSession.getMapper(PostMapper.class);
        mapper.deletePost(post_id);
        sqlSession.commit();
        sqlSession.close();
    }
}
