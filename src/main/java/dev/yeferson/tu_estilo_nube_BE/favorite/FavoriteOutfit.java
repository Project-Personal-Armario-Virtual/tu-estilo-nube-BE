package dev.yeferson.tu_estilo_nube_BE.favorite;

import dev.yeferson.tu_estilo_nube_BE.profile.Profile;
import dev.yeferson.tu_estilo_nube_BE.outfit.Outfit;
import jakarta.persistence.*;

@Entity
public class FavoriteOutfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Outfit outfit;


    public FavoriteOutfit() {}

    public FavoriteOutfit(Profile profile, Outfit outfit) {
        this.profile = profile;
        this.outfit = outfit;
    }

    public Long getId() {
        return id;
    }

    public Profile getProfile() {
        return profile;
    }

    public Outfit getOutfit() {
        return outfit;
    }
}
