package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Region;
import lombok.Data;

/**
 * @author: DongShaowei
 * @create: 2024-08-09 10:59
 * @description:
 */
@Data
public class RegionVo extends Region {

    // 节点数量
    private Integer nodeCount;
}
