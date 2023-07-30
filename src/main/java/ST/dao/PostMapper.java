package ST.dao;

import ST.pojo.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface PostMapper {
    /*
    *发布动态
     */
    @Insert("insert into st_post(user_id,content,created_at) values(#{user_id},#{content},#{created_at})")
    void add(Post post);

    @Select("SELECT LAST_INSERT_ID() AS post_id")
    int getLastInsertId();

    /*
    *根据动态id查询动态
    * 显示单个帖子的详细信息：
        当用户点击某个帖子或者需要展示某个帖子的详细内容时，可以使用根据帖子ID查询帖子对象的功能。
        通过获取帖子ID，你可以调用selectById()方法从数据库中获取对应的帖子对象，然后将其用于展示或者其他操作。
    * 帖子的编辑和更新：
        如果用户想要编辑某个帖子的内容或者进行更新操作，你可以使用根据帖子ID查询帖子对象的功能。通过获取帖子ID，
        你可以调用selectById()方法从数据库中获取该帖子对象，并提供给用户进行编辑和更新操作。
     */
    @Select("select * from st_post where post_id=#{post_id}")
    Post selectById(int post_id);

    @Select("select user_id from st_post where post_id=#{post_id}")
    int selectByUser(int post_id);

    /*
    *查询所有动态
    * 使用内连接连接两个表，on后面跟连接条件,能够查询到与user表对应的用户名
     */
    @Select("select p.post_id,u.username,p.content,p.created_at,u.user_id from st_post p JOIN st_user u on p.user_id = u.user_id" +
            "        order by p.created_at desc")
    List<Post> selectAll();

    /*
    *根据帖子id查询对应帖子动态
     */
    @Select("SELECT p.post_id, p.content, u.user_id FROM st_post p JOIN st_user u ON p.user_id = u.user_id WHERE p.post_id=#{post_id}")
    Post selectByIdPost(int post_id);

    /*
    *根据用户id查询用户所有动态,因为查询所有动态对象，所以用集合
    * 显示用户的帖子列表：
        当用户登录后，你可以使用用户ID来查询该用户发布的所有帖子。
        通过调用selectByUserId()方法并传入用户ID作为参数，可以获取与该用户相关联的所有帖子列表。然后，你可以将这些帖子列表展示给用户，供其查看自己发布的帖子。
    * 用户帖子的管理：
        如果你有一个管理界面或功能，允许用户管理自己的帖子，你可以根据用户ID查询其相关的帖子。
        例如，用户可能想要编辑或删除自己的帖子。通过使用用户ID调用selectByUserId()方法，你可以获取到该用户的所有帖子，并展示给用户进行相应的管理操作。
     * 查询不到user表中的内容，想查询的话就用下面那个
     */
    @Select("select * from st_post where user_id=#{user_id}")
    List<Post> selectByUserId(int user_id);

    /*
    *根据用户id可以查询到p.post_id, u.username, p.content,u.user_id
     */
    @Select("SELECT p.post_id, u.username, p.content, u.user_id, p.created_at FROM st_post p JOIN st_user u ON u.user_id = p.user_id WHERE u.user_id=#{user_id} " +
            "order by p.created_at desc")
    List<Post> selectByUId(int user_id);


    /*
    *修改帖子内容
     */
    @Update("update st_post set content=#{content} where post_id=#{post_id}")
    void updateContent(Post post);

    /*
    *删除帖子
     */
    @Delete("delete from st_post where post_id=#{post_id}")
    void deletePost(int post_id);
}
