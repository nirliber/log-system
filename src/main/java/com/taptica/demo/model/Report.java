package com.taptica.demo.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "report")
public class Report {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private Integer hour;

    private String body;

    public Report() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Integer getHour() {
        return hour;
    }

    public void setHour(@NonNull Integer hour) {
        this.hour = hour;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hour=" + hour +
                ", body='" + body + '\'' +
                '}';
    }
}
