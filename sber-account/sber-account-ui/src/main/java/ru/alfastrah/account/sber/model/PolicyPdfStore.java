package ru.alfastrah.account.sber.model;

import com.vaadin.server.StreamResource;

import java.io.ByteArrayInputStream;

public class PolicyPdfStore {
    private StreamResource policy;
    private StreamResource booklet;

    public StreamResource getPolicy() {
        return policy;
    }

    public void setPolicy(byte[] policy) {
        this.policy = new StreamResource((StreamResource.StreamSource) () ->
                new ByteArrayInputStream(policy), "policy.pdf");
    }

    public void setPolicy(StreamResource policy) {
        this.policy = policy;
    }

    public StreamResource getBooklet() {
        return booklet;
    }

    public void setBooklet(byte[] booklet) {
        this.booklet = new StreamResource((StreamResource.StreamSource) () ->
                new ByteArrayInputStream(booklet), "booklet.pdf");
    }

    public void setBooklet(StreamResource booklet) {
        this.booklet = booklet;
    }

    @Override
    public String toString() {
        return "PolicyPdfStore{" +
                "policy='" + policy + '\'' +
                ", booklet='" + booklet + '\'' +
                '}';
    }
}
