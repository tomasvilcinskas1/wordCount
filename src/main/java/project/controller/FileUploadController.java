package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import project.util.IStorageService;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class FileUploadController {
    private final IStorageService storageService;
    @Autowired
    public FileUploadController(IStorageService storageService) {
        this.storageService = storageService;
    }
@PostMapping("/")
public ResponseEntity<?> uploadFileMulti(
        @RequestParam("files") MultipartFile[] uploadfiles) {
    // Get file name
    String uploadedFileName = Arrays.stream(uploadfiles).map(MultipartFile::getOriginalFilename)
            .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));

    if (StringUtils.isEmpty(uploadedFileName)) {
        return new ResponseEntity("please select a file!", HttpStatus.OK);
    }
    storageService.init();
    storageService.store(uploadfiles);


    return new ResponseEntity("Successfully uploaded - "
            + uploadedFileName, HttpStatus.OK);

}
}
