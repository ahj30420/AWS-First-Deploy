package aws.aws_study.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public String getUploadFileName() {
        return uploadFileName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

}
