package com.xliic.sonar;

import org.sonar.api.Plugin;
import org.sonar.api.config.PropertyDefinition;

import static java.util.Arrays.asList;

public class AuditPlugin implements Plugin {

    @Override
    public void define(Context context) {

        context.addExtensions(OpenApiLanguage.class, OpenApiQualityProfile.class);

        context.addExtensions(AuditRulesDefinition.class, AuditSensor.class);

        context.addExtensions(asList(PropertyDefinition.builder("sonar.foo.file.suffixes").multiValues(true)
                .name("OpenAPI file extensions").description("Comma-separated list of suffixes for files to analyze.")
                .category("OpenAPI").defaultValue(".json,.yaml,.yml").build()));

    }
}