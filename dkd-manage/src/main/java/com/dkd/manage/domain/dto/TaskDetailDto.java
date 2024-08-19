package com.dkd.manage.domain.dto;


import lombok.Data;

/**
 * @author: DongShaowei
 * @create: 2024-08-19 14:42
 * @description:
 */
@Data
public class TaskDetailDto {

    /**
     * 货道编码
     */
    private String channelCode;

    /**
     * 货道容量
     */
    private Long expectCapacity;


    /**
     * 商品编号
     */
    private String skuId;

    /**
     * 商品名称
     */
    private String skuName;


    /**
     * 商品图片
     */
    private String skuImage;
}
