package com.playground.service;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileService {

    Mono<?> handleFileParts(Flux<FilePart> multipart);

    Mono<?> handleParts(Flux<Part> parts);
}
