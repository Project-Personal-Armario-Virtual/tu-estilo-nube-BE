package dev.yeferson.tu_estilo_nube_BE.outfit;

import dev.yeferson.tu_estilo_nube_BE.image.Image;
import dev.yeferson.tu_estilo_nube_BE.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User user;

    @ManyToOne
    private Image top;

    @ManyToOne
    private Image bottom;

    @ManyToOne
    private Image shoes;

    @ManyToOne
    private Image accessory;

    private String occasion;
    private String season;
    private double score;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToMany
    @JoinTable(name = "outfit_images", joinColumns = @JoinColumn(name = "outfit_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getTop() {
        return top;
    }

    public void setTop(Image top) {
        this.top = top;
    }

    public Image getBottom() {
        return bottom;
    }

    public void setBottom(Image bottom) {
        this.bottom = bottom;
    }

    public Image getShoes() {
        return shoes;
    }

    public void setShoes(Image shoes) {
        this.shoes = shoes;
    }

    public Image getAccessory() {
        return accessory;
    }

    public void setAccessory(Image accessory) {
        this.accessory = accessory;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
