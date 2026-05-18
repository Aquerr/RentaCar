package io.github.aquerr.rentacar.workflow.rentacar.domain.image.rest;

import lombok.Value;

@Value(staticConstructor = "of")
public class ImageUploadResponse
{
    String url;
}
