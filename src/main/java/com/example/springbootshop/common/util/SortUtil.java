package com.example.springbootshop.common.util;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortUtil {

    // == 내림차순 :: 생성일자 ==
    public Sort getDescCreatedAtSort() {
        return new Sort(Sort.Direction.DESC, "createdAt");
    }
}
