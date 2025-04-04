package dev.yeferson.tu_estilo_nube_BE.image;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.yeferson.tu_estilo_nube_BE.user.User;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(String fileName, byte[] data, User user) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setData(data);
        image.setUser(user);
        return imageRepository.save(image);
    }

    public List<Image> findByUser(User user) {
        return imageRepository.findByUser(user);
    }

}
