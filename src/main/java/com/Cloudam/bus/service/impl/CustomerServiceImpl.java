package com.Cloudam.bus.service.impl;

import com.Cloudam.bus.entity.Customer;
import com.Cloudam.bus.dao.CustomerMapper;
import com.Cloudam.bus.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-25
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
