package io.github.aquerr.rentacar.workflow.rentacar.domain.image.dto;

import lombok.Value;

import java.net.URI;

@Value(staticConstructor = "of")
public class ImageUri
{
    URI uri;
}
