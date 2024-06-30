package com.project.recommend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "Recommend")
public class recommend {

    @Id
    @GeneratedValue
    Long recommendationId;
    //look up annotation for 1 to many relationship
    Long destId;
    String author;
    Long rate;
    String content;

    public recommend(){

    }
    public recommend(Long destId2, String author2, long rate2, String content2){
        destId = destId2;
        author = author2;
        rate = rate2;
        content = content2;
    }

    public void setDestId(Long destId) {
        this.destId = destId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getRecommendationId() {
        return recommendationId;
    }

    public Long getDestId() {
        return destId;
    }

    public String getAuthor() {
        return author;
    }

    public Long getRate() {
        return rate;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "recommend{" +
                "recommendationId=" + recommendationId +
                ", destId=" + destId +
                ", author='" + author + '\'' +
                ", rate=" + rate +
                ", content='" + content + '\'' +
                '}';
    }
}
