package com.playground;

import com.playground.model.dto.FileDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController(value = "/file")
public class FileController {
` `
    @PostMapping
    Mono<?> simplePost(@RequestBody FileDto fileDto) {

        return Mono.just("OK");
    }
}
