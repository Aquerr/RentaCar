package io.github.aquerr.rentacar.domain.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    public static final String IMAGE_DIR_PATH = "src/web-ui/src/assets/images/";

    public void saveImage(MultipartFile multipartFile, String filePath) {
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(IMAGE_DIR_PATH + filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(IMAGE_DIR_PATH + filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
