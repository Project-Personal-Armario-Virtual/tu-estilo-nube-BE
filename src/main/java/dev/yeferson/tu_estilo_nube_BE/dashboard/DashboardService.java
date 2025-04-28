package dev.yeferson.tu_estilo_nube_BE.dashboard;

import dev.yeferson.tu_estilo_nube_BE.category.CategoryCountDTO;
import dev.yeferson.tu_estilo_nube_BE.image.Image;
import dev.yeferson.tu_estilo_nube_BE.image.ImageRepository;
import dev.yeferson.tu_estilo_nube_BE.outfit.OutfitRepository; 
import dev.yeferson.tu_estilo_nube_BE.user.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final ImageRepository imageRepository;
    private final OutfitRepository outfitRepository; 

    public DashboardService(ImageRepository imageRepository, OutfitRepository outfitRepository) {
        this.imageRepository = imageRepository;
        this.outfitRepository = outfitRepository;
    }

    public DashboardStatsDTO getDashboardStats(User user) {
        List<Image> images = imageRepository.findByUser(user);

        int totalItems = images.size();

        int totalCategories = (int) images.stream()
                .filter(img -> img.getCategory() != null)
                .map(img -> img.getCategory().getName())
                .distinct()
                .count();

        String mostCommonColor = images.stream()
                .map(Image::getDominantColor)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(color -> color, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        int totalOutfits = outfitRepository.findByUser(user).size(); 
        return new DashboardStatsDTO(totalItems, totalCategories, mostCommonColor, totalOutfits);
    }

    public List<CategoryCountDTO> getCategoryCounts(User user) {
        return imageRepository.countImagesByCategory(user);
    }

    public List<CategorySummaryDTO> getCategorySummary(User user) {
        List<Image> userImages = imageRepository.findByUser(user);
        Map<String, Long> counts = new HashMap<>();

        for (Image image : userImages) {
            String category = image.getCategory() != null ? image.getCategory().getName() : "Uncategorized";
            counts.put(category, counts.getOrDefault(category, 0L) + 1);
        }

        List<CategorySummaryDTO> summary = new ArrayList<>();
        counts.forEach((name, count) -> summary.add(new CategorySummaryDTO(name, count)));

        return summary;
    }
}
