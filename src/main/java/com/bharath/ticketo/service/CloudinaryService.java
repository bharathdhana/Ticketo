package com.bharath.ticketo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(String file) throws IOException {
        Map result = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap("folder",
                        "ticketo/movies"
                )
        );
        return result.get("secure_url").toString();
    }
}
