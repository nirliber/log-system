package com.taptica.demo.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Document(collection = "report")
public class Report implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "Report's name must not be null")
    private String name;

    @Indexed
    @NotNull(message = "Report's hour must not be null")
    private Integer hour;

    private String body;

    private boolean sentToQueue;

    public Report() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSentToQueue() {
        return sentToQueue;
    }

    public void setSentToQueue(boolean sentToQueue) {
        this.sentToQueue = sentToQueue;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hour=" + hour +
                ", body='" + body + '\'' +
                ", sentToQueue=" + sentToQueue +
                '}';
    }
}
