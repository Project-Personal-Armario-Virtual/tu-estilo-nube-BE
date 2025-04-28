package dev.yeferson.tu_estilo_nube_BE.image;

public class ImageInUseException extends RuntimeException {
    public ImageInUseException(String message) {
        super(message);
    }
}
