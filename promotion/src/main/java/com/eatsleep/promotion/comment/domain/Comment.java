package com.eatsleep.promotion.comment.domain;

import com.eatsleep.promotion.common.DomainEntity;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@DomainEntity
public class Comment {
    private UUID idComment;
    private LocalDate dateComment;
    private String description;
    private String type;
    private Double rate;
    private UUID idProduct;
    private UUID user;
}
