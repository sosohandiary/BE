package com.hanghae.sosohandiary.utils.page;

import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PageCustom<T> implements Serializable {

    private List<T> content;

    private PageableCustom pageableCustom;

    public PageCustom(List<T> content, Pageable pageable, long total) {
        this.content = content;
        this.pageableCustom = new PageableCustom(new PageImpl(content, pageable, total));
    }

    public PageCustom(List<T> content, Pageable pageable, boolean hasNext) {
        this.content = content;
        this.pageableCustom = new PageableCustom(new SliceImpl(content, pageable, hasNext));
    }

}
