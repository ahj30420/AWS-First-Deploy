package aws.aws_study.component;

import aws.aws_study.domain.UploadFile;
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

    public String getFullPath(String filename){
        return fileDir + filename;
    }

    public UploadFile storeFile(MultipartFile mutiparFile) throws IOException {

        if(mutiparFile.isEmpty()){
            return null;
        }

        String originalFiename = mutiparFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFiename);
        mutiparFile.transferTo(new File(getFullPath(storeFilename)));
        return new UploadFile(originalFiename, storeFilename);

    }

    private String createStoreFileName(String originalFiename) {
        String ext = extractExt(originalFiename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFiename) {
        int pos = originalFiename.lastIndexOf(".");
        return originalFiename.substring(pos + 1);
    }

}
