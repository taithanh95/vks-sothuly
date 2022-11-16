package com.bitsco.vks.sothuly.model;


import java.util.List;

public class PageDTO<T> {
    private Long totalRecords;
    private Integer page;
    private Integer pageSize;
    private List<T> datas;

    public PageDTO(){

    }

    public PageDTO(Long totalRecords, Integer page, Integer pageSize, List<T> datas) {
        this.totalRecords = totalRecords;
        this.page = page;
        this.pageSize = pageSize;
        this.datas = datas;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}

