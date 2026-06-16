package com.smart.dev.service;

import com.smart.dev.model.CloneModuleRequest;
import com.smart.dev.model.CloneModuleResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CloneService {

    public CloneModuleResponse cloneModule(CloneModuleRequest request) throws IOException {
        Path sourcePath = Paths.get(request.getSourcePath());
        Path destPath = Paths.get(request.getDestinationPath());

        if (Files.exists(destPath)) {
            throw new IllegalArgumentException("Destination path already exists. Cannot overwrite: " + destPath);
        }

        if (!Files.exists(sourcePath) || !Files.isDirectory(sourcePath)) {
            throw new IllegalArgumentException("Source path does not exist or is not a directory: " + sourcePath);
        }

        List<String> ignored = request.getIgnoredFilesOrFolders() != null ? request.getIgnoredFilesOrFolders() : List.of();
        List<String> allowedExt = request.getAllowedExtensions() != null && !request.getAllowedExtensions().isEmpty() 
                ? request.getAllowedExtensions() : List.of(".java");

        List<String> previewFiles = new ArrayList<>();
        int filesScanned = 0;
        int filesCopied = 0;
        int filesIgnored = 0;

        long startTime = System.currentTimeMillis();

        try (Stream<Path> stream = Files.walk(sourcePath)) {
            List<Path> allPaths = stream.toList();

            for (Path currentPath : allPaths) {
                if (currentPath.equals(sourcePath)) continue;

                String fileName = currentPath.getFileName().toString();

                boolean isIgnoredDescendant = false;
                for (int i = 0; i < currentPath.getNameCount(); i++) {
                    if (ignored.contains(currentPath.getName(i).toString())) {
                        isIgnoredDescendant = true;
                        break;
                    }
                }
                if (isIgnoredDescendant) {
                    continue;
                }

                if (Files.isDirectory(currentPath)) {
                    continue; 
                }

                filesScanned++;

                boolean hasAllowedExt = false;
                for (String ext : allowedExt) {
                    if (fileName.endsWith(ext)) {
                        hasAllowedExt = true;
                        break;
                    }
                }

                if (!hasAllowedExt) {
                    filesIgnored++;
                    continue;
                }

                String content = Files.readString(currentPath);
                
                Path relativePath = sourcePath.relativize(currentPath);
                String relativePathStr = relativePath.toString();

                if (request.getReplaceRules() != null) {
                    for (List<String> rule : request.getReplaceRules()) {
                        if (rule == null || rule.size() < 2) continue;
                        String target = rule.get(rule.size() - 1);
                        for (int i = 0; i < rule.size() - 1; i++) {
                            String source = rule.get(i);
                            if (source != null && !source.isEmpty()) {
                                content = content.replace(source, target);
                                relativePathStr = relativePathStr.replace(source, target);
                            }
                        }
                    }
                }

                Path targetFile = destPath.resolve(relativePathStr);
                previewFiles.add(targetFile.toString());
                filesCopied++;

                if (!request.isPreviewOnly()) {
                    Files.createDirectories(targetFile.getParent());
                    Files.writeString(targetFile, content);
                }
            }
        }

        long endTime = System.currentTimeMillis();

        CloneModuleResponse response = new CloneModuleResponse();
        response.setStatus("SUCCESS");
        response.setMessage(request.isPreviewOnly() ? "Dry run completed." : "Clone completed successfully.");
        
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("filesScanned", filesScanned);
        metrics.put("filesCopied", filesCopied);
        metrics.put("filesIgnored", filesIgnored);
        metrics.put("timeTakenMs", endTime - startTime);
        
        response.setMetrics(metrics);
        response.setPreviewFiles(previewFiles);

        return response;
    }
}
