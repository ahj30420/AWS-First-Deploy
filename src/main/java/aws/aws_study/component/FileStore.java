package aws.aws_study.component;

import aws.aws_study.domain.UploadFile;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileStore {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public UploadFile storeFile(MultipartFile mutiparFile) throws IOException {

        if(mutiparFile.isEmpty()){
            return null;
        }

        String originalFiename = mutiparFile.getOriginalFilename();
        String storeFilename = createStoreFileName(originalFiename);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(mutiparFile.getSize());
        metadata.setContentType(mutiparFile.getContentType());

        amazonS3.putObject(bucket, storeFilename, mutiparFile.getInputStream(), metadata);

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
