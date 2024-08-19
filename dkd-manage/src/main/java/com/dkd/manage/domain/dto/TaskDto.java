package com.dkd.manage.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: DongShaowei
 * @create: 2024-08-19 14:44
 * @description:
 */
@Data
public class TaskDto {

    /**
     * 创建类型：1：手动创建，0：自动创建
     */
    private Long createType;

    /**
     * 设备编号
     */
    private String innerCode;


    /**
     * 工单执行人Id
     */
    private Long userId;

    /**
     * 指派人Id
     */
    private Long assignorId;

    /**
     * 工单类型Id
     */
    private Long productTypeId;

    /**
     * 描述信息
     */
    private String desc;

    /**
     * 补货详情
     */
    private List<TaskDetailDto> details;

}
