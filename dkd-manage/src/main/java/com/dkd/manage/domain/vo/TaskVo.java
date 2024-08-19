package com.dkd.manage.domain.vo;

import com.dkd.manage.domain.Task;
import com.dkd.manage.domain.TaskType;
import lombok.Data;

/**
 * @author: DongShaowei
 * @create: 2024-08-18 17:28
 * @description:
 */

@Data
public class TaskVo extends Task {

    // 工单类型
    private TaskType taskType;

}
