package com.dkd.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.dkd.common.annotation.Excel;
import com.dkd.common.core.domain.BaseEntity;

/**
 * 点位管理对象 tb_node
 * 
 * @author javadong
 * @date 2024-08-08
 */
public class Node extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 点位ID */
    private Long id;

    /** 点位名称 */
    @Excel(name = "点位名称")
    private String name;

    /** 所属区域ID */
    @Excel(name = "所属区域ID")
    private Long regionId;

    /** 商圈类型 */
    @Excel(name = "商圈类型")
    private Long businessType;

    /** 合作商ID */
    @Excel(name = "合作商ID")
    private Long partnerId;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String address;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRegionId(Long regionId) 
    {
        this.regionId = regionId;
    }

    public Long getRegionId() 
    {
        return regionId;
    }
    public void setBusinessType(Long businessType) 
    {
        this.businessType = businessType;
    }

    public Long getBusinessType() 
    {
        return businessType;
    }
    public void setPartnerId(Long partnerId) 
    {
        this.partnerId = partnerId;
    }

    public Long getPartnerId() 
    {
        return partnerId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("regionId", getRegionId())
            .append("businessType", getBusinessType())
            .append("partnerId", getPartnerId())
            .append("address", getAddress())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
