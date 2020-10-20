package com.xliic.sonar;

import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import static java.util.Arrays.asList;

public class AuditPlugin implements Plugin {
    public static final String REPO_NAME = "Security Audit";
    public static final String REPO_KEY = OpenApiLanguage.KEY + "-security-audit";

    public static final String EXCLUSIONS_KEY = "sonar.openapi.audit.exclusions";
    public static final String API_TOKEN_KEY = "sonar.openapi.audit.api.token";
    public static final String COLLECTION_NAME = "sonar.openapi.audit.collection.name";
    static final String CATEGORY = "OpenAPI";

    @Override
    public void define(Context context) {

        context.addExtensions(OpenApiLanguage.class, OpenApiQualityProfile.class);

        context.addExtensions(AuditMetrics.class, ComputeAuditScore.class, ComputeAuditSecurityScore.class,
                ComputeAuditDataScore.class, AuditRulesDefinition.class, AuditSensor.class,
                OpenApiExclusionsFileFilter.class);

        context.addExtensions(asList(

                PropertyDefinition.builder(API_TOKEN_KEY).name("API token").type(PropertyType.PASSWORD)
                        .description(
                                "The API token that the plugin uses to authenticate to API Contract Security Audit.")
                        .category(CATEGORY).build(),

                PropertyDefinition.builder(COLLECTION_NAME).name("Collection name").type(PropertyType.STRING)
                        .description("The API collection where the discovered OpenAPI definitions are stored.")
                        .category(CATEGORY).defaultValue("SonarQube").build(),

                PropertyDefinition.builder(EXCLUSIONS_KEY).multiValues(true).name("Excluded filepaths")
                        .description("A list of directories and file paths.").onQualifiers(Qualifiers.PROJECT)
                        .category(CATEGORY).defaultValue("**/node_modules/**,**/package.json,**/package-lock.json")
                        .build(),

                PropertyDefinition.builder(OpenApiLanguage.FILE_SUFFIXES_KEY)
                        .defaultValue(OpenApiLanguage.FILE_SUFFIXES_DEFVALUE).name("OpenAPI file suffixes")
                        .description("File types included in the analysis").category(CATEGORY).multiValues(true)
                        .onQualifiers(Qualifiers.PROJECT).build()

        ));

    }
}
