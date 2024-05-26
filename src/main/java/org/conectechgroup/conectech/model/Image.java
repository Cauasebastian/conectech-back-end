package org.conectechgroup.conectech.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "images")
public class Image {

    @Id
    private String id;
    private byte[] data;
    private String contentType;
    private String hash;

    public Image() {
    }

    public Image(byte[] data, String contentType, String hash) {
        this.data = data;
        this.contentType = contentType;
        this.hash = hash;
    }

    // Getters and setters...


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) &&
                Objects.equals(hash, image.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hash);
    }
}

