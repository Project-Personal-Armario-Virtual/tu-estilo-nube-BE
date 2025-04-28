package dev.yeferson.tu_estilo_nube_BE.profile;

import dev.yeferson.tu_estilo_nube_BE.user.User;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile createProfileForUser(User user) {
        Profile profile = new Profile();
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    public Profile findByUser(User user) {
        return profileRepository.findByUser(user).orElse(null);
    }

    public Profile updateProfile(User user, String displayName, String bio) {
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setDisplayName(displayName);
        profile.setBio(bio);

        return profileRepository.save(profile);
    }

    public Profile getByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for user ID: " + userId));
    }
}