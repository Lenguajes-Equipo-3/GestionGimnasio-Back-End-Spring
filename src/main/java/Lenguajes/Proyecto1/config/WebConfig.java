package Lenguajes.Proyecto1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.media.dir}")
    private String mediaDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Convertir la ruta a absoluta y URI para servir correctamente los recursos
        Path mediaPath = Paths.get(mediaDir).toAbsolutePath().normalize();
        String mediaUri = mediaPath.toUri().toString();  // ejemplo: file:///C:/.../media/

        registry.addResourceHandler("/media/**")
                .addResourceLocations(mediaUri);
    }
}
