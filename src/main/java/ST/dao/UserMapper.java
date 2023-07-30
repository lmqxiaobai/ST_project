package ST.dao;

import ST.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    /*
     *根据用户名和密码查询用户对象
     */
    @Select("select * from st_user where username = #{username} and password = #{password}")
    User select(@Param("username") String username, @Param("password") String password);

    /*
     *根据查询用户对象
     */
    @Select("select * from st_user where username = #{username}")
    User selectByUsername(String username);

    /*
    *根据用户id查询用户对象
     */
    @Select("select * from st_user where user_id=#{user_id}")
    User selectById(int user_id);
    /*

     *添加用户
     */
    @Insert("insert into st_user(username,password) values(#{username},#{password})")
    void add(User user);

    /*
     *查询所有
     */
    @Select("select * from st_user ")
    List<User> selectAll();

    /*
    *修改用户信息
     */
    @Update("update st_user set username = #{username}, password = #{password}, email = #{email} where user_id = #{user_id}")
    void updateUser(@Param("username") String username,@Param("password") String password,@Param("email") String email);

    /*
    *注销用户
     */
    @Delete("delete from st_user where user_id = #{user_id}")
    void deleteUser(int user_id);
}
