package org.stackoverflowdata.loader.postgres;

public class FilePath {
    private final String fileName;

    public FilePath(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
