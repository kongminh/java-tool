package com.smart.dev.model;

import java.util.List;

public class CloneModuleRequest {
    private String sourcePath;
    private String destinationPath;
    private List<List<String>> replaceRules;
    private List<String> ignoredFilesOrFolders;
    private List<String> allowedExtensions = List.of(".java");
    private boolean previewOnly = true;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public List<List<String>> getReplaceRules() {
        return replaceRules;
    }

    public void setReplaceRules(List<List<String>> replaceRules) {
        this.replaceRules = replaceRules;
    }

    public List<String> getIgnoredFilesOrFolders() {
        return ignoredFilesOrFolders;
    }

    public void setIgnoredFilesOrFolders(List<String> ignoredFilesOrFolders) {
        this.ignoredFilesOrFolders = ignoredFilesOrFolders;
    }

    public List<String> getAllowedExtensions() {
        return allowedExtensions;
    }

    public void setAllowedExtensions(List<String> allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
    }

    public boolean isPreviewOnly() {
        return previewOnly;
    }

    public void setPreviewOnly(boolean previewOnly) {
        this.previewOnly = previewOnly;
    }
}
