package com.dkd.manage.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: DongShaowei
 * @create: 2024-08-17 15:39
 * @description:
 */
@Data
public class ChannelConfigDto {

    // 售货机编号
    private String innerCode;

    // 货道集合
    private List<ChannelSkuDto> channelList;
}
