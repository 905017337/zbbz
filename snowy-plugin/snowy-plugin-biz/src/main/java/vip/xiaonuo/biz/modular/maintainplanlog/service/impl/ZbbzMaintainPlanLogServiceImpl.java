/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.maintainplanlog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.maintainplanlog.entity.ZbbzMaintainPlanLog;
import vip.xiaonuo.biz.modular.maintainplanlog.mapper.ZbbzMaintainPlanLogMapper;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogAddParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogEditParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogIdParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogPageParam;
import vip.xiaonuo.biz.modular.maintainplanlog.service.ZbbzMaintainPlanLogService;

import java.util.List;

/**
 *  维护作战任务日志Service接口实现类
 *
 * @author czh
 * @date  2023/06/03 21:40
 **/
@Service
public class ZbbzMaintainPlanLogServiceImpl extends ServiceImpl<ZbbzMaintainPlanLogMapper, ZbbzMaintainPlanLog> implements ZbbzMaintainPlanLogService {

    @Override
    public Page<ZbbzMaintainPlanLog> page(ZbbzMaintainPlanLogPageParam zbbzMaintainPlanLogPageParam) {
        QueryWrapper<ZbbzMaintainPlanLog> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzMaintainPlanLogPageParam.getPlanName())) {
            queryWrapper.lambda().like(ZbbzMaintainPlanLog::getPlanName, zbbzMaintainPlanLogPageParam.getPlanName());
        }
        if(ObjectUtil.isNotEmpty(zbbzMaintainPlanLogPageParam.getMaintainTeamName())) {
            queryWrapper.lambda().like(ZbbzMaintainPlanLog::getMaintainTeamName, zbbzMaintainPlanLogPageParam.getMaintainTeamName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzMaintainPlanLogPageParam.getSortField(), zbbzMaintainPlanLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzMaintainPlanLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzMaintainPlanLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzMaintainPlanLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzMaintainPlanLog::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzMaintainPlanLogAddParam zbbzMaintainPlanLogAddParam) {
        ZbbzMaintainPlanLog zbbzMaintainPlanLog = BeanUtil.toBean(zbbzMaintainPlanLogAddParam, ZbbzMaintainPlanLog.class);
        this.save(zbbzMaintainPlanLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzMaintainPlanLogEditParam zbbzMaintainPlanLogEditParam) {
        ZbbzMaintainPlanLog zbbzMaintainPlanLog = this.queryEntity(zbbzMaintainPlanLogEditParam.getId());
        BeanUtil.copyProperties(zbbzMaintainPlanLogEditParam, zbbzMaintainPlanLog);
        this.updateById(zbbzMaintainPlanLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzMaintainPlanLogIdParam> zbbzMaintainPlanLogIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzMaintainPlanLogIdParamList, ZbbzMaintainPlanLogIdParam::getId));
    }

    @Override
    public ZbbzMaintainPlanLog detail(ZbbzMaintainPlanLogIdParam zbbzMaintainPlanLogIdParam) {
        return this.queryEntity(zbbzMaintainPlanLogIdParam.getId());
    }

    @Override
    public ZbbzMaintainPlanLog queryEntity(String id) {
        ZbbzMaintainPlanLog zbbzMaintainPlanLog = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzMaintainPlanLog)) {
            throw new CommonException(" 维护作战任务日志不存在，id值为：{}", id);
        }
        return zbbzMaintainPlanLog;
    }
}
