package com.example.workflow.service;

import com.example.workflow.domain.Business;
import com.example.workflow.domain.SysUser;

public interface BusinessService {

    void add(Business business, SysUser sysUser);
}
