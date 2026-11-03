package com.event.processing.common.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("RepoMetrics")
public class RepoMetrics {

    @Id
    private String id;

    private int totalFeaturePushes;

    private int totalPRsToMain;

    private int totalPRsOpen;

    private int totalPRsClosed;

    private int totalPRsMerged;

    private int cumulativeMergeTimeSeconds;

    private int mergedPRCount;

    private String lastUpdatedAt;
}
