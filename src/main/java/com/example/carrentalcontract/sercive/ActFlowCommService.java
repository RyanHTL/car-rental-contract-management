package com.example.carrentalcontract.sercive;

import com.example.carrentalcontract.common.Result;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;
import java.util.Map;

public interface ActFlowCommService {

    // 流程部署
    Result saveNewDeploy();

    /**
     * 启动流程
     *
     * @param formKey
     * @param beanName
     * @param businessKey
     * @param id
     * @return
     */
    ProcessInstance startProcess(String formKey, String beanName, String businessKey, Long id, Map<String, Object> variables);

    /**
     * 我的任务列表
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> myTaskList(String userId);

    /**
     * 完成任务
     *
     * @param remark
     * @param taskId
     * @param userId
     */
    Result completeProcess(String remark, String taskId, String userId);

    /**
     * 设置局部流程变量
     * @param taskId 任务id
     * @param variables 变量
     */
    Result setLocalVariables(String taskId, Map<String, Object> variables);
}
