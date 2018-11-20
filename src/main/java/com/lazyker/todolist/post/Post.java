package com.lazyker.todolist.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String content;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Post(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    void initCreatedAt() {
        this.createdAt = new Date();
    }

    void setPrefixIdAtSubject() {
        if (!this.subject.contains(".")) {
            this.subject = String.format("%s. %s", this.id, this.subject);
        }
    }
}

