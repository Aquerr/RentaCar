package io.github.aquerr.rentacar.application.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException
                    {
                        // Alternatywa dla pobierania obrazków.
                        // Dzięki temu nie musimy sami tworzyć RESTa do pobierania byte[] obrazków
                        // z zewnętrznego folderu.
                        //
                        // Komentarz dla potomnych. :)

//                        if (resourcePath.startsWith("images"))
//                        {
//                            return new FileUrlResource("assets/").createRelative(resourcePath);
//                        }

                        Resource requestedResource = location.createRelative(resourcePath);
                        return requestedResource.exists() && requestedResource.isReadable() ? requestedResource : new ClassPathResource("/static/index.html");
                    }
                });
    }
}
