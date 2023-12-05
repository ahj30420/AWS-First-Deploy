package aws.aws_study.repository;

import aws.aws_study.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<Img,Long> {

}
