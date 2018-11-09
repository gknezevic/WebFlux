package com.playground.service;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileService {

    Mono<?> handleFile(Flux<FilePart> multipart);
}
