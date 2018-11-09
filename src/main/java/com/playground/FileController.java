package com.playground;

import com.playground.model.dto.FileDto;
import com.playground.service.FileService;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController(value = "/file")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/dto")
    Mono<?> simplePost(@RequestBody FileDto fileDto) {

        return Mono.just("OK");
    }

    @PostMapping(value = "/bytes")
    Mono<?> bytesPost(@RequestPart(value = "bytes") Flux<FilePart> multipart) {
        return fileService.handleFileParts(multipart);
    }

    @PostMapping(value = "/mixed")
    Mono<?> mixedPost(@RequestPart(value = "bytes") Flux<FilePart> multipart,
                      @RequestPart(value = "fileName") String fileName,
                      @RequestPart(value = "owner") String fileOwner) {
        return fileService.handleFileParts(multipart);
    }

    @PostMapping(value = "/mixedWithBody")
    Mono<?> mixedWithBodyPost(@RequestBody Flux<Part> parts) {
        return fileService.handleParts(parts);
    }


}
