package com.dkd.manage.service;

import java.util.List;
import com.dkd.manage.domain.Emp;

/**
 * 人员李彪Service接口
 * 
 * @author javadong
 * @date 2024-08-14
 */
public interface IEmpService 
{
    /**
     * 查询人员李彪
     * 
     * @param id 人员李彪主键
     * @return 人员李彪
     */
    public Emp selectEmpById(Long id);

    /**
     * 查询人员李彪列表
     * 
     * @param emp 人员李彪
     * @return 人员李彪集合
     */
    public List<Emp> selectEmpList(Emp emp);

    /**
     * 新增人员李彪
     * 
     * @param emp 人员李彪
     * @return 结果
     */
    public int insertEmp(Emp emp);

    /**
     * 修改人员李彪
     * 
     * @param emp 人员李彪
     * @return 结果
     */
    public int updateEmp(Emp emp);

    /**
     * 批量删除人员李彪
     * 
     * @param ids 需要删除的人员李彪主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Long[] ids);

    /**
     * 删除人员李彪信息
     * 
     * @param id 人员李彪主键
     * @return 结果
     */
    public int deleteEmpById(Long id);
}
