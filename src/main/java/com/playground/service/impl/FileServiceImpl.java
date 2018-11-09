package com.playground.service.impl;

import com.playground.Mp4Headers;
import com.playground.service.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public Mono<?> handleFile(Flux<FilePart> multipart) {
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
