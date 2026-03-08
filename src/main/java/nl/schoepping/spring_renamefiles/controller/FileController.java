package nl.schoepping.spring_renamefiles.controller;

import lombok.extern.java.Log;
import nl.schoepping.spring_renamefiles.domain.ReadFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@Log
public class FileController {

    @GetMapping(path = "/files/root")
    public List<ReadFile> getFiles() {
        String regexMedia = "^(.*\\.MP4|.*\\.JPG|.*\\.ARW)$";
        File dir = new File("../Mediafiles");
        File[] files = dir.listFiles();
        List<ReadFile> fileList = new ArrayList<>();
        for (File file : files) {
            if (file.isFile() && file.getName().toUpperCase().matches(regexMedia)) {
                fileList.add(ReadFile.builder()
                        .fileName(file.getName())
                        .filePath(file.getPath())
                        .build()
                );
            }
        }
        return fileList;
    }

    @PostMapping(path = "/files")
    public ReadFile createFile(@RequestBody ReadFile readFile) {
        log.info("Creating file: " + readFile.getFileName());
        return readFile;
    }
}
