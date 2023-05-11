package EZ.nomargin.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {   //저장하는 로직
        if (multipartFile.isEmpty()) {
            return null;
        }

        if (!isImageFile(multipartFile)) {
            throw new IllegalArgumentException("올바른 이미지 파일 형식이 아닙니다.");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);//저장할 떄는 originalFilename 가 아닌 고유한 값을 저장. 확장자까지 저장함
        multipartFile.transferTo(new File(getFullPath(storeFileName))); // 고유한 파일명을 만들어줌. 서버에 이 이름으로 저장됨

        return new UploadFile(originalFilename, storeFileName); // 객체에 던져줌
    }

        public void updateFile(String originalStoreFileName, MultipartFile multipartFile) throws IOException {//저장하는 로직
            multipartFile.transferTo(new File(getFullPath(originalStoreFileName))); // 고유한 파일명을 만들어줌. 서버에 이 이름으로 저장됨
            // 객체에 던져줌
        }


        private String createStoreFileName(String originalFilename) {
            String ext = extractExt(originalFilename);
            String uuid = UUID.randomUUID().toString();
            return uuid + "." + ext;
        }

        private String extractExt(String originalFilename) {
            int pos = originalFilename.lastIndexOf(".");
            return originalFilename.substring(pos + 1);
        }
    private boolean isImageFile(MultipartFile multipartFile) {
        String mimeType = multipartFile.getContentType();
        return mimeType != null && mimeType.startsWith("image/");
    }
}
