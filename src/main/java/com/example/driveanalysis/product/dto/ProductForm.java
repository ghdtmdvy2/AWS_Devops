package com.example.driveanalysis.product.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ProductForm {
    @NotEmpty(message = "제목은 필수항목입니다.")
    private String subject;
    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
    @NotNull(message = "가격은 필수항목입니다.")
    private int price;
    @NotNull(message = "재고는 필수항목입니다.")
    private int stock;
}
