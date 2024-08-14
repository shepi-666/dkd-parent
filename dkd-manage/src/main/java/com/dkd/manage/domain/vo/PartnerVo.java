package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Partner;
import lombok.Data;

/**
 * @author: DongShaowei
 * @create: 2024-08-13 21:36
 * @description:
 */
@Data
public class PartnerVo extends Partner {
    // 点位数量
    private Integer nodeCount;
}
