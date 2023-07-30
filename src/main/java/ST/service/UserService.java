package ST.service;

import ST.dao.UserMapper;
import ST.pojo.User;
import ST.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

// service层，调用接口方法，返回结果
public class UserService {
    // 调用工具类，获取连接工厂
    SqlSessionFactory factory = SqlSessionFactoryUtils.getSqlSessionFactory();

    /*
     *查询对象并返回
     */
    public User login(String username, String password){
        // 创建连接对象操作映射文件mapper
        SqlSession sqlSession = factory.openSession();
        // 获取UserMapper接口的代理对象
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.select(username,password);
        // 释放资源
        sqlSession.close();
        return user;
    }

    /*
     *注册方法，返回结果
     */
    public boolean register(User user){
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        // 判断用户是否存在
        User us = mapper.selectByUsername(user.getUsername());
        if(us == null){
            // 用户名不存在，可以注册
            mapper.add(user);
            // 提交事务
            sqlSession.commit();
        }
        return us==null;
    }

    /*
    *判断用户对象是否存在
     */
    public boolean us(String username){
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User u = mapper.selectByUsername(username);
        sqlSession.close();

        return u != null;
    }

    /*
    *根据id查询用户对象
     */
    public User getUserById(int user_id){
        SqlSession sqlSession = factory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectById(user_id);
        sqlSession.close();
        return user;
    }
}
