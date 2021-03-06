package com.example.carrentalcontract.Schedule;

import com.example.carrentalcontract.entity.model.Contract;
import com.example.carrentalcontract.sercive.ContractService;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Configuration
@EnableScheduling
@CommonsLog
@Slf4j
public class FissionWantedTask {

    @Autowired
    private ContractService contractService;

    @Scheduled(cron = "0 15 0 * * ?")
    // @Scheduled(fixedDelay = 1000 * 10)
    public void taskUpdateContractStatus() {
        log.info("------------定时任务:开始-------------");
        //每天凌晨00:15触发
        List<Contract> contractList = contractService.selectPassAll().getData();
        if (!contractList.isEmpty() && contractList != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (Contract contract : contractList) {
                Date endDate = contract.getEndTime();
                Date startTime = contract.getStartTime();
                Date nowTime = new Date();
                if (startTime.before(nowTime)&&nowTime.before(endDate)&&!contract.getState().equals(3)){
                    System.out.println("合同="+contract.getContractName()+"==生效");
                    contract.setState(3);
                    contractService.update(contract);
                }
                if (endDate.before(new Date())&&!contract.getState().equals(2)){
                    // 合同到期，设置合同状态
                    System.out.println("合同="+contract.getContractName()+"==到期");
                    contract.setState(2);
                    contractService.update(contract);

                } else {
                    continue;
                }
            }
        }
        log.info("------------定时任务:结束-------------");

    }
}
