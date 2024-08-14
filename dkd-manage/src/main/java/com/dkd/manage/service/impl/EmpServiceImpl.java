package com.dkd.manage.service.impl;

import java.util.List;
import com.dkd.common.utils.DateUtils;
import com.dkd.manage.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.EmpMapper;
import com.dkd.manage.domain.Emp;
import com.dkd.manage.service.IEmpService;

import javax.annotation.Resource;

/**
 * 人列表Service业务层处理
 * 
 * @author javadong
 * @date 2024-08-14
 */
@Service
public class EmpServiceImpl implements IEmpService 
{
    @Autowired
    private EmpMapper empMapper;

    @Resource
    private RoleServiceImpl roleService;

    @Resource
    private RegionServiceImpl regionService;

    /**
     * 查询人列表
     * 
     * @param id 人列表主键
     * @return 人列表
     */
    @Override
    public Emp selectEmpById(Long id)
    {
        return empMapper.selectEmpById(id);
    }

    /**
     * 查询人列表列表
     * 
     * @param emp 人列表
     * @return 人列表
     */
    @Override
    public List<Emp> selectEmpList(Emp emp)
    {
        return empMapper.selectEmpList(emp);
    }

    /**
     * 新增人列表
     * 
     * @param emp 人列表
     * @return 结果
     */
    @Override
    public int insertEmp(Emp emp) {

        // 补充区域名称
        emp.setRegionName(regionService.selectRegionById(emp.getRegionId()).getName());
        // 补充角色信息
        Role role = roleService.selectRoleByRoleId(emp.getRoleId());
        emp.setRoleName(role.getRoleName());
        emp.setRoleCode(role.getRoleCode());

        emp.setCreateTime(DateUtils.getNowDate());
        return empMapper.insertEmp(emp);
    }

    /**
     * 修改人列表
     * 
     * @param emp 人列表
     * @return 结果
     */
    @Override
    public int updateEmp(Emp emp) {

        // 补充区域名称
        emp.setRegionName(regionService.selectRegionById(emp.getRegionId()).getName());
        // 补充角色信息
        Role role = roleService.selectRoleByRoleId(emp.getRoleId());
        emp.setRoleName(role.getRoleName());
        emp.setRoleCode(role.getRoleCode());
        emp.setUpdateTime(DateUtils.getNowDate());
        return empMapper.updateEmp(emp);
    }

    /**
     * 批量删除人列表
     * 
     * @param ids 需要删除的人列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpByIds(Long[] ids)
    {
        return empMapper.deleteEmpByIds(ids);
    }

    /**
     * 删除人列表信息
     * 
     * @param id 人列表主键
     * @return 结果
     */
    @Override
    public int deleteEmpById(Long id)
    {
        return empMapper.deleteEmpById(id);
    }
}
