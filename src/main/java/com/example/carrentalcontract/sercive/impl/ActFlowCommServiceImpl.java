package com.example.carrentalcontract.sercive.impl;

import com.example.carrentalcontract.common.Result;
import com.example.carrentalcontract.entity.model.SysUser;
import com.example.carrentalcontract.sercive.ActFlowCommService;
import com.example.carrentalcontract.sercive.UsersService;
import lombok.extern.slf4j.Slf4j;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ActFlowCommServiceImpl implements ActFlowCommService {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UsersService usersService;

    @Override
    public Result saveNewDeploy() {
        return null;
    }

    @Override
    public ProcessInstance startProcess(String formKey, String beanName, String businessKey,
                                        Long id, Map<String, Object> variables) {
        // 修改业务状态

        variables.put("businessKey", businessKey);
        // 启动流程
        log.info("【启动流程】，formKey : {},businessKey : {}", formKey, businessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(formKey, businessKey, variables);

        String definitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】，成功,definitionId : {}", definitionId);
        return processInstance;
        /*
        【启动流程】，formKey : contract,businessKey : contract:1102010319513959

        【启动流程】，成功,definitionId : contract:1:5003
          processDefinitionId is contract:1:5003
         */
    }

    @Override
    public List<Map<String, Object>> myTaskList(String username) {
        /*
          根据负责人id 查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(username);

        List<Task> taskList = taskQuery.orderByTaskCreateTime().desc().list();
        // TaskQuery taskGroupQuery = taskService.createTaskQuery().taskCandidateUser(userId);

        // List<Task> groupTaskList = taskGroupQuery.orderByTaskCreateTime().desc().list();
        List<Map<String, Object>> listMap = new ArrayList<>();
        translateTaskList(taskList, listMap);
        // translateTaskList(groupTaskList, listMap);
        return listMap;
    }

    @Override
    public List<Map<String, Object>> myGTaskList(String username) {

        List<Task> list = taskService.createTaskQuery()
                // .processDefinitionKey("contract")
                .taskCandidateGroup(username)
                .list();
        List<Map<String, Object>> listMap = new ArrayList<>();

        translateTaskList(list, listMap);
        return listMap;
    }

    private void translateTaskList(List<Task> taskList, List<Map<String, Object>> listMap) {
        for (Task task : taskList) {
            Map<String, Object> map = new HashMap<>();
            map.put("taskId", task.getId());
            map.put("taskName", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("delegationState", task.getDelegationState());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());

            SysUser user = new SysUser();
            user.setUsername(task.getAssignee());
            List<SysUser> sysUser = usersService.select(user).getData();
            if (sysUser != null && sysUser.size() >0) {
                map.put("assigneeUser", sysUser.get(0).getUsername());
            }
            listMap.add(map);
        }
    }

    @Override
    public Result completeProcess(String remark, String taskId, String userId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee(userId)
                .singleResult();
        if (task == null) {
            log.error("completeProcess - task is null");
            return new Result(901, "任务不存在");
        }
        log.info("------完成任务操作  开始 ------");
        String processInstanceId = task.getProcessInstanceId();
        // 设置审批人的userID
        Authentication.setAuthenticatedUserId(userId);
        // 添加记录
        taskService.addComment(taskId, processInstanceId, remark);
        log.info("流程实例ID：{}", task.getProcessInstanceId());
        log.info("任务ID：{}", task.getId());
        log.info("任务责任人：{}", task.getAssignee());
        log.info("任务名称：{}", task.getName());
        taskService.complete(task.getId());
        log.info("------完成任务操作  接受 ------");
        return Result.success();
    }

    /**
     * 设置局部流程变量
     *
     * @param taskId    任务id
     * @param variables 变量
     */
    @Override
    public void setLocalVariables(String taskId, Map<String, Object> variables) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        if (task == null) {
            log.error("setLocalVariables - task is null");
            new Result(901, "任务不存在");
            return;
        }
        taskService.setVariablesLocal(taskId, variables);
        Result.success();
    }


}