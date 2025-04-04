package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<ImageDTO> findByUser(User user) {
        return imageRepository.findImageDTOsByUser(user);
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }
}