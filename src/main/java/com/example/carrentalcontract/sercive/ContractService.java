package com.example.carrentalcontract.sercive;


import com.example.carrentalcontract.common.DbService;
import com.example.carrentalcontract.common.Result;
import com.example.carrentalcontract.entity.view.Contract;
import com.github.pagehelper.PageInfo;

/**
 * 合同表(Contract)表服务接口
 *
 * @author makejava
 * @since 2020-12-27 22:11:54
 */
public interface ContractService extends DbService<Contract> {

    Result<PageInfo<Contract>> findPage(Contract contract);
}