package ST.pojo;

import java.util.Date;
import java.util.List;

/*
* 发布动态表
 */
public class Post {
    // 标识每个动态id
    private Integer post_id;
    // 外键，表明发布动态的用户id
    private Integer user_id;
    // 文本框
    private String content;

    // 用于保存动态发布者的用户名
    private String username;
    // 头像路径
    private String image_url;
    // 图片路径集合
    private List<String> image_urls;
    // 创建时间
    private Date created_at;
    // 更新时间
    private Date updated_at;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public List<String> getImage_urls() {
        return image_urls;
    }

    public void setImage_urls(List<String> image_urls) {
        this.image_urls = image_urls;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", user_id=" + user_id +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", image_url='" + image_url + '\'' +
                ", image_urls=" + image_urls +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
