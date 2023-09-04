package com.noah.backend.post.service;

import com.noah.backend.image.domain.entity.Image;
import com.noah.backend.image.service.AwsS3Service;
import com.noah.backend.image.util.FileUtils;
import com.noah.backend.post.domain.entity.Post;
import com.noah.backend.post.domain.repository.ImageJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageFileUploadService {

    private final AwsS3Service awsS3Service;
    private final PostService postService;
    private final ImageJdbcRepository imageJdbcRepository;

    @Transactional
    public void upload(Long postId, List<MultipartFile> files) throws IOException {
        Post post = postService.findPostById(postId);
        upload(files, post);
    }

    private void upload(List<MultipartFile> files, Post post) throws IOException {
        List<Image> images = uploadImageToStorageServer(files, post);
        imageJdbcRepository.saveAll(images);
    }

    private List<Image> uploadImageToStorageServer(List<MultipartFile> files, Post post) throws IOException {
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            String filename = FileUtils.getRandomFilename();
            String filepath = awsS3Service.upload(file, filename);
            images.add(Image.builder()
                    .name(filename)
                    .url(filepath)
                    .post(post)
                    .build());
        }

        return images;
    }

}
