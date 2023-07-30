package ST.service;


import ST.dao.FollowMapper;

import ST.pojo.Follow;
import ST.pojo.User;
import ST.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class FollowService {
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();
    /*
    *添加关注关系
     */
    public void addFollow(Follow follow){
        SqlSession sqlSession = factory.openSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.addFollow(follow);
        sqlSession.commit();
        sqlSession.close();
    }
    /*
    *删除关注关系
     */
    public void deleteFollow(int follow_id, int following_id){
        SqlSession sqlSession = factory.openSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        followMapper.deleteFollow(follow_id,following_id);
        sqlSession.commit();
        sqlSession.close();
    }

    /*
     *查询当前用户是否关注了指定用户
     * 作用：返回0则是没有关注，大于0表示了关注，可以用来判断关注与未关注
     */
    public int checkIfFollowed(int follower_id,int following_id){
        SqlSession sqlSession = factory.openSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        int check = followMapper.checkIfFollowed(follower_id,following_id);
        sqlSession.close();
        return check;
    }

    /*
     *根据follower_id获取关注列表
     */
    public List<User> getInterestFollow(int follower_id){
        SqlSession sqlSession = factory.openSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<User> interests = followMapper.getInterestFollow(follower_id);
        sqlSession.close();
        return interests;
    }

    /*
     *根据following_id获取粉丝列表
     */
    public List<User> getFansFollow(int following_id){
        SqlSession sqlSession = factory.openSession();
        FollowMapper followMapper = sqlSession.getMapper(FollowMapper.class);
        List<User> fans = followMapper.getFansFollow(following_id);
        sqlSession.close();
        return fans;
    }
}
