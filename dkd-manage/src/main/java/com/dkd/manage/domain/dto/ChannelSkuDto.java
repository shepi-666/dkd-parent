package com.dkd.manage.domain.dto;

import lombok.Data;

/**
 * @author: DongShaowei
 * @create: 2024-08-17 15:35
 * @description:
 */

@Data
public class ChannelSkuDto {
    // 售货机编号
    private String innerCode;

    // 货道编号
    private String channelCode;

    // 商品ID
    private Long skuId;
}
