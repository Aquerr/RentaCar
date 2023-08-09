package io.github.aquerr.rentacar.web.rest.response;

import lombok.Value;

@Value(staticConstructor = "of")
public class ImageUploadResponse
{
    String url;
}
