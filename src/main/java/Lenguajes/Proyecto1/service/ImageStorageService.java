package Lenguajes.Proyecto1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageStorageService {

    @Value("${app.media.dir}")
    private String mediaDir;

    public String saveImage(MultipartFile file, String subfolder) throws IOException {
        String nombreImagen = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(mediaDir).toAbsolutePath().resolve(subfolder);
        Files.createDirectories(uploadPath); // crea la carpeta si no existe

        Path ruta = uploadPath.resolve(nombreImagen);
        System.out.println("Guardando imagen en: " + ruta.toString());

        file.transferTo(ruta.toFile()); // guarda solo una vez

        // Devolvés solo la ruta relativa que se usará en la URL
        return Paths.get(subfolder, nombreImagen).toString().replace("\\", "/");
    }
}
