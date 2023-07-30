package ST.pojo;

import java.util.Date;

public class Follow {
    private Integer follow_id;
    // 关注者id
    private Integer follower_id;
    // 被关注者id
    private Integer following_id;
    private Date created_at;

    public Integer getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }

    public Integer getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(Integer follower_id) {
        this.follower_id = follower_id;
    }

    public Integer getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(Integer following_id) {
        this.following_id = following_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Follow{" +
                "follow_id=" + follow_id +
                ", follower_id=" + follower_id +
                ", following_id=" + following_id +
                ", created_at=" + created_at +
                '}';
    }
}
