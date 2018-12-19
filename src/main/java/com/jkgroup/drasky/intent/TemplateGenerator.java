package com.jkgroup.drasky.intent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class TemplateGenerator {

    private TemplateEngine templateEngine;

    @Autowired
    public TemplateGenerator(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String parse(String templateName, Context context){
        String resourcePath;

        try{
            resourcePath = new ClassPathResource(templateName).getFile().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringWriter stringWriter = new StringWriter();
        templateEngine.process(templateName, context, stringWriter);

        return stringWriter.toString();
    }
}
