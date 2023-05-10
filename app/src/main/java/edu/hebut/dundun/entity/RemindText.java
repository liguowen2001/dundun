package edu.hebut.dundun.entity;

import org.litepal.crud.LitePalSupport;

/**
 * 自定义提醒文案
 */
public class RemindText extends LitePalSupport {
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文案内容
     */
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
