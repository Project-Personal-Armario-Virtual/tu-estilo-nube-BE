package dev.yeferson.tu_estilo_nube_BE.image;

public class ImageDTO {
    private Long id;
    private String fileName;
    private Long userId;


    public ImageDTO(Long id, String fileName, Long userId) {
        this.id = id;
        this.fileName = fileName;
        this.userId = userId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
