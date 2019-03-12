package ru.alfastrah.account.sber.model;

public class TextLinkData {
    private Long id;
    private String text;
    private String source;

    public TextLinkData(Long id, String text, String source) {
        this.id = id;
        this.text = text;
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
