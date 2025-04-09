package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(String fileName, byte[] data, User user, List<String> labels) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setData(data);
        image.setUser(user);
        image.setLabels(labels);
        return imageRepository.save(image);
    }

    public Image saveImage(String fileName, byte[] data, User user) {
        return saveImage(fileName, data, user, null);
    }

    public List<ImageDTO> findByUser(User user) {
        return findImageDTOsByUser(user);
    }

    public List<ImageDTO> findImageDTOsByUser(User user) {
        List<Image> images = imageRepository.findByUser(user);
        return images.stream()
                .map(i -> new ImageDTO(i.getId(), i.getFileName(), i.getUser().getId(), i.getLabels()))
                .collect(Collectors.toList());
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }
}
