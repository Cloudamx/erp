package com.Cloudam.bus.service;

import com.Cloudam.bus.entity.SysLeavebill;
import com.Cloudam.bus.vo.LeavebillVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KazuGin
 * @since 2020-08-26
 */
public interface SysLeavebillService extends IService<SysLeavebill> {

    IPage<SysLeavebill> findLeaveBillByPage(IPage<SysLeavebill> page,LeavebillVo leavebillVo) throws Exception;
}
