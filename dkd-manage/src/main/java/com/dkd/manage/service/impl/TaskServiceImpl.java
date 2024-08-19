package com.dkd.manage.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.dkd.common.constant.DkdContants;
import com.dkd.common.exception.ServiceException;
import com.dkd.common.utils.DateUtils;
import com.dkd.common.utils.bean.BeanUtils;
import com.dkd.manage.domain.Emp;
import com.dkd.manage.domain.TaskDetails;
import com.dkd.manage.domain.VendingMachine;
import com.dkd.manage.domain.dto.TaskDetailDto;
import com.dkd.manage.domain.dto.TaskDto;
import com.dkd.manage.domain.vo.TaskVo;
import com.dkd.manage.service.IEmpService;
import com.dkd.manage.service.ITaskDetailsService;
import com.dkd.manage.service.IVendingMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.dkd.manage.mapper.TaskMapper;
import com.dkd.manage.domain.Task;
import com.dkd.manage.service.ITaskService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 工单Service业务层处理
 * 
 * @author javadong
 * @date 2024-08-18
 */
@Service
public class TaskServiceImpl implements ITaskService 
{
    @Autowired
    private TaskMapper taskMapper;

    @Resource
    private IVendingMachineService vendingMachineService;

    @Resource
    private IEmpService empService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ITaskDetailsService taskDetailsService;

    /**
     * 查询工单
     * 
     * @param taskId 工单主键
     * @return 工单
     */
    @Override
    public Task selectTaskByTaskId(Long taskId)
    {
        return taskMapper.selectTaskByTaskId(taskId);
    }

    /**
     * 查询工单列表
     * 
     * @param task 工单
     * @return 工单
     */
    @Override
    public List<Task> selectTaskList(Task task)
    {
        return taskMapper.selectTaskList(task);
    }

    /**
     * 新增工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int insertTask(Task task)
    {
        task.setCreateTime(DateUtils.getNowDate());
        return taskMapper.insertTask(task);
    }

    /**
     * 修改工单
     * 
     * @param task 工单
     * @return 结果
     */
    @Override
    public int updateTask(Task task)
    {
        task.setUpdateTime(DateUtils.getNowDate());
        return taskMapper.updateTask(task);
    }

    /**
     * 批量删除工单
     * 
     * @param taskIds 需要删除的工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskIds(Long[] taskIds)
    {
        return taskMapper.deleteTaskByTaskIds(taskIds);
    }

    /**
     * 删除工单信息
     * 
     * @param taskId 工单主键
     * @return 结果
     */
    @Override
    public int deleteTaskByTaskId(Long taskId)
    {
        return taskMapper.deleteTaskByTaskId(taskId);
    }


    /**
     * 查询工单列表
     * @param task
     * @return
     */
    @Override
    public List<TaskVo> selectTaskVoList(Task task) {
        return taskMapper.selectTaskVoList(task);
    }

    /**
     * 新增运营和运维工单
     * @param taskDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertTaskDto(TaskDto taskDto) {

        // 查询售货机是否存在
        VendingMachine vm = vendingMachineService.selectVendingMachineByInnerCode(taskDto.getInnerCode());
        if (vm == null) {
            throw new ServiceException("设备不存在");
        }

        // 校验工单状态和机器运行状态是否一致
        checkVmStatus(vm.getVmStatus(), taskDto.getProductTypeId());

        // 检查是否有未完成的同类型工单
        hasTask(taskDto);

        // 查询并校验员工是否存在
        Emp emp = checkEmp(taskDto, vm);

        // 将Dto转换为Po对象，并保存数据
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task); // 属性赋值
        task.setTaskStatus(DkdContants.TASK_STATUS_CREATE); //  工单状态
        task.setUserName(emp.getUserName());
        task.setRegionId(vm.getRegionId());
        task.setAddr(vm.getAddr());
        task.setCreateTime(DateUtils.getNowDate());
        task.setTaskCode(generateTaskCode()); // 工单编号
        int rows = taskMapper.insertTask(task);

        // 判断是否为补货工单
        if (taskDto.getProductTypeId().equals(DkdContants.TASK_TYPE_SUPPLY)) {
            // 获取工单详情列表
            List<TaskDetailDto> detailsList = taskDto.getDetails();
            if (detailsList != null && detailsList.size() > 0) {
                // 将Dto转换为PO补充属性
                List<TaskDetails> details = detailsList.stream().map(dto -> {
                    TaskDetails taskDetails = BeanUtil.copyProperties(dto, TaskDetails.class);
                    taskDetails.setTaskId(task.getTaskId());
                    return taskDetails;
                }).collect(Collectors.toList());
                // 批量保存
                taskDetailsService.batchInsertTaskDetails(details);

            } else {
                throw new ServiceException("补货工单详情不能为空");
            }

        }

        return rows;
    }

    /**
     * 取消工单
     * @param task
     * @return
     */
    @Override
    public int cancelTask(Task task) {
        // 判断工单状态是否可以取消
        // 根据工单Id查询数据库
        Task taskDb = taskMapper.selectTaskByTaskId(task.getTaskId());
        // 判断工单状态是否为已取消，如果是，则抛出异常
        if (taskDb.getTaskStatus().equals(DkdContants.TASK_STATUS_CANCEL)) {
            throw new ServiceException("工单已取消，无法再次取消");
        }

        // 判断工单状态是否已经完成，如果是，则抛出异常
        if (taskDb.getTaskStatus().equals(DkdContants.TASK_STATUS_FINISH)) {
            throw new ServiceException("工单已完成，无法取消");
        }
        // 设置工单状态为 已取消
        task.setTaskStatus(DkdContants.TASK_STATUS_CANCEL);

        // 设置更新时间
        task.setUpdateTime(DateUtils.getNowDate());

        // 更新数据库
        return taskMapper.updateTask(task);

    }

    /**
     * 生成并获取当天的工单编号
     */
    private String generateTaskCode() {
        // 获取当前日期并格式化为yyyyMMdd
        String date = DateUtils.getDate().replaceAll("-", "");

        // 根据日期生成redis的键
        String key = "task_code_" + date;
        // 判断Key是否存在
        if (!redisTemplate.hasKey(key)) {
            // 如果不存在，则设置初始值,并指定过期时间
            redisTemplate.opsForValue().set(key, 1, Duration.ofDays(1));
            return date + String.format("%04d", 1);
        } else {
            // 如果存在，则获取当前值，并自增,确保字符串长度为4位

            redisTemplate.opsForValue().increment(key, 1);
            Integer taskCode = (Integer) redisTemplate.opsForValue().get(key);
            return date + String.format("%04d", taskCode);
        }
    }

    /**
     * 校验员工是否存在
     * @param taskDto
     * @param vm
     */
    private Emp checkEmp(TaskDto taskDto, VendingMachine vm) {

        Emp emp = empService.selectEmpById(taskDto.getUserId());
        if (emp == null) {
            throw new ServiceException("员工不存在");
        }

        // 校验员工区域是否匹配
        if (!emp.getRegionId().equals(vm.getRegionId())) {
            throw new ServiceException("员工所在区域与设备所在区域不一致");
        }

        return emp;
    }

    /**
     *
     * @param taskDto
     */
    private void hasTask(TaskDto taskDto) {
        // 检查设备是否有未完成的同类型的工单
        // 创建对象并设置设备编号和工单类型，以及工单状态为运行中
        Task task = new Task();
        task.setInnerCode(taskDto.getInnerCode());
        task.setProductTypeId(taskDto.getProductTypeId());
        task.setTaskStatus(DkdContants.TASK_STATUS_PROGRESS);
        // 调用taskMapper查询数据库是否有符合条件的工单列表
        List<Task> taskList = taskMapper.selectTaskList(task);
        if (taskList != null && taskList.size() > 0) {
            throw new ServiceException("当前设备存在未完成的工单，不能重复进行创建");
        }
    }


    /**
     * 校验售货机状态和工单状态是否一致
     * @param vmStatus
     * @param productTypeId
     */
    public void checkVmStatus(Long vmStatus, Long productTypeId) {
        // 校验售货机状态是否与工单状态一致
        // 投放 -- 运行
        if (productTypeId.equals(DkdContants.TASK_TYPE_DEPLOY) && vmStatus.equals(DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("当前设备已经处于运营状态");
        }

        // 维修 -- 非运行
        if (productTypeId.equals(DkdContants.TASK_TYPE_REPAIR) && !vmStatus.equals(DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("当前设备在运营中，无法进行维修");
        }

        // 补货 -- 非运行
        if (productTypeId.equals(DkdContants.TASK_TYPE_SUPPLY) && !vmStatus.equals(DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("当前设备在运营中，无法进行补货");
        }

        // 撤机 -- 非运行
        if (productTypeId.equals(DkdContants.TASK_TYPE_REVOKE) && !vmStatus.equals(DkdContants.VM_STATUS_RUNNING)) {
            throw new ServiceException("当前设备不在运营中，无法进行撤机");
        }
    }
}
