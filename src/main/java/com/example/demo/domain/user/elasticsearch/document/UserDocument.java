package com.example.demo.domain.user.elasticsearch.document;

import com.example.demo.domain.user.entity.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "users")
public class UserDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String username;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String userRole;

    @Field(type = FieldType.Integer)
    private int followersCount;

    public UserDocument() {
    }

    public static UserDocument from(User user) {
        UserDocument doc = new UserDocument();
        doc.setId(String.valueOf(user.getId()));
        doc.setUsername(user.getUsername());
        doc.setEmail(user.getEmail());
        doc.setUserRole(user.getUserRole());

        if (user.getFollowers() != null) {
            doc.setFollowersCount(user.getFollowers().size());
        } else {
            doc.setFollowersCount(0);
        }

        return doc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }
}