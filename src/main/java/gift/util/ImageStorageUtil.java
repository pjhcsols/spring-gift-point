package gift.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Component
public class ImageStorageUtil {
    private static final Logger logger = LoggerFactory.getLogger(ImageStorageUtil.class);
    private static final String STORAGE_DIR = "src/main/resources/imageStorage/"; // 실제 저장 위치를 지정합니다.

    public static String saveImage(MultipartFile imageFile) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String imageName = "product" + "_" + timestamp + ".jpg";

        // ClassPathResource를 사용하여 리소스 경로를 얻습니다.
        ClassPathResource resource = new ClassPathResource(STORAGE_DIR);

        // 실제 파일 저장 경로를 지정합니다.
        File storageDir = new File(resource.getPath());

        if (!storageDir.exists()) {
            storageDir.mkdirs(); // 디렉토리가 존재하지 않으면 생성합니다.
        }

        File outputFile = new File(storageDir, imageName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(imageFile.getBytes());
        }

        String filePath = outputFile.getAbsolutePath();
        logger.info("Image saved successfully at path: {}", filePath);
        return filePath;
    }

    // URL-safe Base64 Encoder 사용
    public static String encodeImagePathToBase64(String imagePath) {
        return Base64.getUrlEncoder().encodeToString(imagePath.getBytes());
    }

    public static String decodeBase64ImagePath(String base64ImagePath) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(base64ImagePath);
        return new String(decodedBytes);
    }

    public static void deleteImage(String imagePath) {
        File imageFile = new File(imagePath);
        logger.info("Attempting to delete image at path: {}", imagePath);
        if (imageFile.exists()) {
            imageFile.delete();
            logger.info("Image deleted successfully at path: {}", imagePath);
        }
    }

    //chat
    // 채팅 이미지 저장 메서드
    public String saveChatImage(MultipartFile imageFile, String folderPath) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String imageName = "image_" + timestamp + ".jpg";

        File storageDir = new File(STORAGE_DIR + folderPath);
        logger.info("Attempting to save image at path: {}", storageDir.getAbsolutePath());
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File outputFile = new File(storageDir, imageName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(imageFile.getBytes());
        }

        String filePath = outputFile.getAbsolutePath();
        logger.info("Chat image saved successfully at path: {}", filePath);
        return filePath;
    }

    // Base64 데이터를 이미지 파일로 저장하는 공통 메서드
    public String saveBase64Image(String fileBase64, String folderPath) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(fileBase64);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        String imageName = "image_" + timestamp + ".jpg";

        File storageDir = new File(STORAGE_DIR + folderPath);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        File outputFile = new File(storageDir, imageName);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(decodedBytes);
        }

        return outputFile.getAbsolutePath();  // 파일 경로 반환
    }

    // 채팅방 이미지 폴더 삭제 메서드
    public void deleteChatFolder(String folderPath) {
        File folder = new File(STORAGE_DIR + folderPath);
        if (folder.exists()) {
            for (File file : folder.listFiles()) {
                file.delete();
            }
            folder.delete();
            logger.info("Chat folder deleted successfully: {}", folderPath);
        }
    }

}

