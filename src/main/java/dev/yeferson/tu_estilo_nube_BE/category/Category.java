package dev.yeferson.tu_estilo_nube_BE.category;

import jakarta.persistence.*;
import java.util.List;
import dev.yeferson.tu_estilo_nube_BE.image.Image;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

   
    @OneToMany(mappedBy = "category")
    private List<Image> images;

   
    public Category() {}


    public Category(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}