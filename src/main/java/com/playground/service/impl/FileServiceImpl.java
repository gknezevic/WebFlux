package com.playground.service.impl;

import com.playground.Mp4Headers;
import com.playground.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Mono<?> handleFileParts(Flux<FilePart> multipart) {
        return multipart.flatMap(filePart -> {
            // log.info(filePart.name());
            return filePart.content();
        }).collect(InputStreamCollector::new, (t, dataBuffer)-> t.collectInputStream(dataBuffer.asInputStream()))
                .map(inputStream -> {
                    try {
                        byte[] bytes = IOUtils.toByteArray(inputStream.getInputStream());
                        byte[] subBytes = Arrays.copyOfRange(bytes, 4, 12);
                        return Mp4Headers.isMp4Header(new String(subBytes)) ? "Mp4 file" : "Not Mp4";
                    } catch(Exception e){
                        // log error
                        return new Error(e.getMessage());
                    }
                });
    }

    @Override
    public Mono<?> handleParts(Flux<Part> parts) {
        return handleFileParts(getFilePart(parts));
    }

    private Flux<FilePart> getFilePart(Flux<? extends Part> partsFlux) {
        return partsFlux.filter(part -> part instanceof FilePart)
                .cast(FilePart.class);
    }

    private Mono<String> partFluxDescription(Flux<? extends Part> partsFlux) {
        return partsFlux.collectList().map(this::partListDescription);
    }

    private String partListDescription(List<? extends Part> parts) {
        return parts.stream().map(this::partDescription)
                .collect(Collectors.joining(",", "[", "]"));
    }

    private String partDescription(Part part) {
        return part instanceof FilePart ? part.name() + ":" + ((FilePart) part).filename() : part.name();
    }

    private class InputStreamCollector {
        private InputStream inputStream;

        public void collectInputStream(InputStream inputStream) {
            if (this.inputStream == null) this.inputStream = inputStream;
            this.inputStream = new SequenceInputStream(this.inputStream, inputStream);
        }

        public InputStream getInputStream() {
            return this.inputStream;
        }
    }
}
