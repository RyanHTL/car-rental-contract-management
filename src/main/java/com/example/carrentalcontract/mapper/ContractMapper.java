package com.example.carrentalcontract.mapper;


import com.example.carrentalcontract.common.DbMapper;
import com.example.carrentalcontract.entity.view.Contract;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * 合同表(Contract)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-27 22:11:53
 */
public interface ContractMapper extends DbMapper<Contract>{


    List<Contract> findall();

    Page<Contract> all();
}