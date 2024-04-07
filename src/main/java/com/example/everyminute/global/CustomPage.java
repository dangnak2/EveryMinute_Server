package com.example.everyminute.global;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@ApiModel
@Getter
@Setter
public class CustomPage {
    @ApiModelProperty(value = "페이지 번호(0...N)", example = "0")
    private Integer page;

    @ApiModelProperty(value = "페이지 크기", example = "20")
    private Integer size;

    @ApiModelProperty(value = "정렬")
    private List<String> sort;
}
