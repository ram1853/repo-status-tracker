package com.repo.registration.common.entity;

import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

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
 */

@DynamoDbBean
@RegisterReflectionForBinding(RegistrationDetail.class)
public class RegistrationDetail {

    private String owner;
    private String repo;
    private String eventUrl;
    private List<String> events;
    private String createdAt;

    @DynamoDbPartitionKey
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @DynamoDbSortKey
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
