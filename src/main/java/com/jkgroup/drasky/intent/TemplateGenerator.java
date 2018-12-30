package com.jkgroup.drasky.intent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.StringWriter;

@Component
public class TemplateGenerator {

    private TemplateEngine templateEngine;

    @Autowired
    public TemplateGenerator(TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
    }

    public String parse(String templateName, Context context){
        StringWriter stringWriter = new StringWriter();
        templateEngine.process(templateName, context, stringWriter);

        return stringWriter.toString();
    }
}
