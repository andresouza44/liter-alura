package br.com.andre.literalura.dto;

import java.util.List;

public class LivroDto {


    String title;
    Integer download_count;
    List<String> languages;
    List<AutorDto> authors;

    public LivroDto() {
    }

    public LivroDto(String title, Integer download_count, List<String> languages, List<AutorDto> authors) {
        this.title = title;
        this.download_count = download_count;
        this.languages = languages;
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<AutorDto> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AutorDto> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "LivroDto{" +
                "title='" + title + '\'' +
                ", download_count=" + download_count +
                ", languages=" + languages +
                ", authors=" + authors +
                '}';
    }
}