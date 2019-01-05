package com.cxf.sell.controller;

import com.cxf.sell.dataobject.base.Msg;
import com.cxf.sell.utils.DatePattern;
import com.cxf.sell.utils.XDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("upload")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    public static final String ROOT = "upload-dir";

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
       String path = Paths.get(ROOT, filename).toString();
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path ));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @RequestMapping(method = RequestMethod.POST, value = "/up")
    public Object handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException {
        String fileName="";
        if (!file.isEmpty()) {
            fileName= XDateUtils.dateToString(new Date(), DatePattern.DATE_TIME_FULL_NUM.getPattern())+file.getOriginalFilename();
            Files.copy(file.getInputStream(),  Paths.get(ROOT, fileName));
            return Msg.success().add("url","/upload/"+fileName);
        }
        return Msg.fail("上传的文件为空");
    }


}
