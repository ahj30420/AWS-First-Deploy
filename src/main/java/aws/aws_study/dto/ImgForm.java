package aws.aws_study.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImgForm {

    private String name;
    private MultipartFile imageFile;

}
