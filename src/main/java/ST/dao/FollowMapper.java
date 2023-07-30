package ST.dao;

import ST.pojo.Follow;
import ST.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowMapper {
    /*
    *添加关注关系
     */
    void addFollow(Follow follow);

    /*
    *删除关注关系
     */
    void deleteFollow(@Param("follower_id") int follower_id,@Param("following_id") int following_id);

    /*
    *查询当前用户是否关注了指定用户
    * 作用：返回0则是没有关注，大于0表示了关注，可以用来判断关注与未关注
     */
    int checkIfFollowed(@Param("follower_id") int follower_id, @Param("following_id") int following_id);

    /*
    *根据follower_id获取关注列表
     */
    List<User> getInterestFollow(int follower_id);

    /*
    *根据following_id获取粉丝列表
     */
    List<User> getFansFollow(int following_id);
}
