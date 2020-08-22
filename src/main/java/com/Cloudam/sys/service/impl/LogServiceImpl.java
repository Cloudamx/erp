package com.Cloudam.sys.service.impl;

import com.Cloudam.sys.entity.Log;
import com.Cloudam.sys.dao.LogMapper;
import com.Cloudam.sys.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-16
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
