package org.synrgy.setara.common.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class PagedResponse<T> {

  private Iterable<T> data;

  private int currentPage;

  private int totalPages;

  private int size;

  private long total;

  public static <T> PagedResponse<T> from(Page<T> page) {
    return PagedResponse.<T>builder()
        .data(page.getContent())
        .currentPage(page.getNumber() + 1)
        .totalPages(page.getTotalPages())
        .size(page.getSize())
        .total(page.getTotalElements())
        .build();
  }

}
