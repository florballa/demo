package com.example.demo.model;

public class Pagination {

    private String type;
    private Integer pageNo;
    private Integer pageSize;
    private String sortBy;
    private String sortType;

    public Pagination(){}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "type='" + type + '\'' +
                ", pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", sortType='" + sortType + '\'' +
                '}';
    }
}
