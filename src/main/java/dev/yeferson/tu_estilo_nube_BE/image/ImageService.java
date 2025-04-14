package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.category.Category;
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

    public Image saveImage(String fileName, byte[] data, User user, List<String> labels, Category category) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setData(data);
        image.setUser(user);
        image.setLabels(labels);
        image.setCategory(category);
        return imageRepository.save(image);
    }

    public Image saveImage(String fileName, byte[] data, User user, List<String> labels) {
        return saveImage(fileName, data, user, labels, null);
    }

    // Mantiene compatibilidad del método findByUser
    public List<ImageDTO> findByUser(User user) {
        return findImageDTOsByUser(user);
    }

    // Actualizado para incluir dominantColor en el DTO (por ahora se asigna null)
    public List<ImageDTO> findImageDTOsByUser(User user) {
        List<Image> images = imageRepository.findByUser(user);
        return images.stream().map(i -> {
            String categoryName = (i.getCategory() != null) ? i.getCategory().getName() : null;
            // Actualmente, dominantColor no se almacena en la entidad, por lo que se asigna null.
            String dominantColor = null;
            return new ImageDTO(i.getId(), i.getFileName(), i.getUser().getId(), i.getLabels(), categoryName, dominantColor);
        }).collect(Collectors.toList());
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public void deleteImage(Long id, User user) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            if (image.getUser().getId().equals(user.getId())) {
                imageRepository.delete(image);
            } else {
                throw new RuntimeException("Not authorized to delete this image");
            }
        } else {
            throw new RuntimeException("Image not found");
        }
    }
}
