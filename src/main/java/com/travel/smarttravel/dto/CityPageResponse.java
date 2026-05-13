package com.travel.smarttravel.dto;

import java.util.List;

public class CityPageResponse {

    private List<CityDTO> content;
    private long totalElements;
    private int totalPages;
    private int page;

    public List<CityDTO> getContent() {
        return content;
    }

    public void setContent(List<CityDTO> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}