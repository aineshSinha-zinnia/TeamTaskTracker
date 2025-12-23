package com.ainesh.TeamTaskTracker.utils;

import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageableUtil {
  
  public static Pageable enforceSortWhiteList(Pageable pageable, Set<String> allowedSorts){
    Sort safeSort = Sort.unsorted();

    for(Sort.Order order: pageable.getSort()){
      if(allowedSorts.contains(order.getProperty())){
        safeSort = safeSort.and(Sort.by(order));
      }
    }

    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), safeSort);
  }

}
