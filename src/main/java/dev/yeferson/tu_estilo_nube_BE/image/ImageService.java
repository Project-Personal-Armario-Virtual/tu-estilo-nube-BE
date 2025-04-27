package dev.yeferson.tu_estilo_nube_BE.image;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import dev.yeferson.tu_estilo_nube_BE.category.Category;
import dev.yeferson.tu_estilo_nube_BE.outfit.OutfitRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final OutfitRepository outfitRepository;

    public ImageService(ImageRepository imageRepository, OutfitRepository outfitRepository) {
        this.imageRepository = imageRepository;
        this.outfitRepository = outfitRepository;
    }

    public Image saveImage(String fileName, byte[] data, User user, List<String> labels, Category category,
            String dominantColor) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setData(data);
        image.setUser(user);
        image.setLabels(labels);
        image.setCategory(category);
        image.setDominantColor(dominantColor);
        return imageRepository.save(image);
    }

    public Image saveImage(String fileName, byte[] data, User user, List<String> labels) {
        return saveImage(fileName, data, user, labels, null, null);
    }

    public List<ImageDTO> findByUser(User user) {
        return findImageDTOsByUser(user);
    }

    public List<ImageDTO> findImageDTOsByUser(User user) {
        List<Image> images = imageRepository.findByUser(user);
        return images.stream().map(i -> {
            String categoryName = (i.getCategory() != null) ? i.getCategory().getName() : null;
            String dominantColor = i.getDominantColor();
            return new ImageDTO(i.getId(), i.getFileName(), i.getUser().getId(), i.getLabels(), categoryName,
                    dominantColor);
        }).collect(Collectors.toList());
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public void deleteImage(Long id, User user) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isPresent()) {
            Image image = imageOpt.get();
            if (!image.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Not authorized to delete this image");
            }

            if (outfitRepository.existsByImagesContaining(image)) {
                throw new ImageInUseException("Cannot delete image because it is used in an outfit.");
            }

            imageRepository.delete(image);
        } else {
            throw new RuntimeException("Image not found");
        }
    }

    public List<Image> findRecentImagesByUser(User user) {
        return imageRepository.findTop5ByUserOrderByCreatedAtDesc(user);
    }

    public Optional<Image> findByIdAndUser(Long id, User user) {
        return imageRepository.findByIdAndUser(id, user);
    }
}
