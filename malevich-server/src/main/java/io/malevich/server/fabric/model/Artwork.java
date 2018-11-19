package io.malevich.server.fabric.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Artwork {

    private String $class;

    private String artworkId;

    private String owner;

    private String value;


    public Artwork(String artworkId, String owner, String value) {
        this.$class = "io.malevich.network.Artwork";
        this.artworkId = artworkId;
        this.owner = owner;
        this.value = value;

    }

}
