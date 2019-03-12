package ru.alfastrah.account.sber.backend.model.mail;

import java.io.File;

public class MailAttachment {
    private String filename;
    private File file;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "MailAttachment{" +
                "filename='" + filename + '\'' +
                ", file=" + file +
                '}';
    }
}
