package dev.yeferson.tu_estilo_nube_BE.outfit;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutfitService {

    private final OutfitRepository outfitRepository;

    public OutfitService(OutfitRepository outfitRepository) {
        this.outfitRepository = outfitRepository;
    }

    public Outfit saveOutfit(Outfit outfit) {
        return outfitRepository.save(outfit);
    }

    public Optional<Outfit> getOutfitById(Long id) {
        return outfitRepository.findById(id);
    }

    public void deleteOutfit(Long id) {
        outfitRepository.deleteById(id);
    }

    public Outfit getById(Long outfitId) {
        return outfitRepository.findById(outfitId)
                .orElseThrow(() -> new RuntimeException("Outfit not found with ID: " + outfitId));
    }

    public List<Outfit> getOutfitsByUserId(Long userId) {
        return outfitRepository.findByUser_Id(userId);
    }

}
