package com.example.Destination_Microservice.models;

//import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.IOException;

@Entity
public class Destination {

    //Member Variables
    @Id
    private Long destId;
    private String place;
    private String country;
    private Double latitude;
    private Double longitude;
    private String info;
    //How do I store an image?
//    private BufferedImage image; // /main/images/ParkGuell.jpg


    //Methods
    public Destination() {

    }

    public Destination(Long destId, String place, String country, Double latitude, Double longitude, String info) {
        this.destId = destId;
        this.place = place;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.info = info;
    }

    public Long getDestId() {
        return destId;
    }

    public void setDestId(Long destId) {
        this.destId = destId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "destId=" + destId +
                ", place='" + place + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", info='" + info + '\'' +
                '}';
    }
}
