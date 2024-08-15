package com.dkd.manage.mapper;

import java.util.List;
import com.dkd.manage.domain.Emp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 人员李彪Mapper接口
 * 
 * @author javadong
 * @date 2024-08-14
 */
public interface EmpMapper 
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
     * 删除人员李彪
     * 
     * @param id 人员李彪主键
     * @return 结果
     */
    public int deleteEmpById(Long id);

    /**
     * 批量删除人员李彪
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmpByIds(Long[] ids);


    /**
     * 根据regionId修改区域名称
     * @param name
     * @param id
     * @return
     */
    @Update("update tb_emp set region_name = #{name} where region_id = #{id}")
    public int updateByuRegionId(@Param("name") String name, @Param("id")Long id);
}
