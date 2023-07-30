package ST.pojo;

import java.util.Date;

public class Image {
    private Integer image_id;
    private Integer post_id;
    private String image_url;
    private Date created_at;
    private Integer user_id;

    public Integer getImage_id() {
        return image_id;
    }

    public void setImage_id(Integer image_id) {
        this.image_id = image_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image_id=" + image_id +
                ", post_id=" + post_id +
                ", image_url='" + image_url + '\'' +
                ", created_at=" + created_at +
                ", user_id=" + user_id +
                '}';
    }
}
