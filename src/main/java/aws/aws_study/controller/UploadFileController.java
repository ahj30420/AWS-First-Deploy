package aws.aws_study.controller;

import aws.aws_study.component.FileStore;
import aws.aws_study.domain.Img;
import aws.aws_study.domain.UploadFile;
import aws.aws_study.dto.ImgForm;
import aws.aws_study.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UploadFileController {

    private final FileStore fileStore;
    private final UploadFileRepository uploadFileRepository;

    @PostMapping("/img/new")
    public String saveImg(@ModelAttribute ImgForm form, RedirectAttributes redirectAttributes) throws IOException {

        UploadFile uploadFile = fileStore.storeFile(form.getImageFile());

        Img img = new Img(form.getName(), uploadFile);
        uploadFileRepository.save(img);

        Long id = img.getId();

        redirectAttributes.addAttribute("imgId", id);

        return "redirect:/img/{imgId}";
    }

    @GetMapping("/img/{imgId}")
    public String ViewImg(@PathVariable(("imgId")) Long imgId, Model model){
        Optional<Img> img = uploadFileRepository.findById(imgId);
        log.info("Storefilename={}",img.get().getImg().getStoreFileName());
        model.addAttribute("filename", img.get().getImg().getStoreFileName());
        return "img-view";
    }

    @ResponseBody
    @GetMapping("/img/downloadImage/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException{
        log.info("파일 경로={}",fileStore.getFullPath(filename));
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

}
