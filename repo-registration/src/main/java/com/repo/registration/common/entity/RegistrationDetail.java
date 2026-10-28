package com.repo.registration.common.entity;

import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags;

import java.util.List;

/**
 *  Spring's own AOT/native support (via spring-boot-maven-plugin with the native profile) automatically scans all your @Component, @Service, @Controller, @Configuration etc. classes and
 *   registers them for reflection in the native image — it knows about them because they're part of the Spring component model.
 *
 *   RegistrationDetail is not a Spring-managed bean. It's a plain POJO annotated with @DynamoDbBean, which is an AWS SDK annotation, not a Spring annotation. The Spring AOT processor has no
 *   reason to look at it, so it never gets registered for reflection in the native image.
 *
 *   At runtime, the AWS Enhanced DynamoDB SDK's BeanTableSchema tries to instantiate it via Class.forName(...) and Constructor.newInstance() — pure Java reflection — and in a native image,
 *   that fails unless you've explicitly told GraalVM "keep this class accessible via reflection".
 *
 *   In short:
 *
 *   - Spring beans → Spring AOT registers them automatically
 *   - @DynamoDbBean POJOs → only the AWS SDK uses them via reflection, Spring AOT doesn't know about them, so you have to register them yourself
 *   - used reflect-config.json for this
 *
 *   -> But the above approach using reflect-config.json or the annotation way did not work.
 *   Hence, changed the entity definition from the DynamoDbBean way to the below static table
 *   schema way -> so all the methods are resolved at compile time itself (no reflection needed,
 *   and it works well with GraalVM)
 */


public class RegistrationDetail {

    private String owner;
    private String repo;
    private String eventUrl;
    private List<String> events;
    private String createdAt;

    public static final TableSchema<RegistrationDetail> REGISTRATION_DETAIL_TABLE_SCHEMA =
            TableSchema.builder(RegistrationDetail.class)
                    .newItemSupplier(RegistrationDetail::new)
                    .addAttribute(String.class, a -> a.name("owner")
                            .getter(RegistrationDetail::getOwner)
                            .setter(RegistrationDetail::setOwner)
                            .tags(StaticAttributeTags.primaryPartitionKey()))
                    .addAttribute(String.class, a -> a.name("repo")
                            .getter(RegistrationDetail::getRepo)
                            .setter(RegistrationDetail::setRepo)
                            .tags(StaticAttributeTags.primarySortKey()))
                    .addAttribute(String.class, a -> a.name("eventUrl")
                            .getter(RegistrationDetail::getEventUrl)
                            .setter(RegistrationDetail::setEventUrl))
                    .addAttribute(EnhancedType.listOf(String.class), a -> a.name("events")
                            .getter(RegistrationDetail::getEvents)
                            .setter(RegistrationDetail::setEvents))
                    .addAttribute(String.class, a -> a.name("createdAt")
                            .getter(RegistrationDetail::getCreatedAt)
                            .setter(RegistrationDetail::setCreatedAt))
                    .build();

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
