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
package vip.xiaonuo.biz.modular.planlog.service.impl;

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
import vip.xiaonuo.biz.modular.planlog.entity.ZbbzPlanLog;
import vip.xiaonuo.biz.modular.planlog.mapper.ZbbzPlanLogMapper;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogAddParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogEditParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogIdParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogPageParam;
import vip.xiaonuo.biz.modular.planlog.service.ZbbzPlanLogService;

import java.util.List;

/**
 * 任务修改记录Service接口实现类
 *
 * @author czh
 * @date  2023/06/03 21:29
 **/
@Service
public class ZbbzPlanLogServiceImpl extends ServiceImpl<ZbbzPlanLogMapper, ZbbzPlanLog> implements ZbbzPlanLogService {

    @Override
    public Page<ZbbzPlanLog> page(ZbbzPlanLogPageParam zbbzPlanLogPageParam) {
        QueryWrapper<ZbbzPlanLog> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzPlanLogPageParam.getPlanName())) {
            queryWrapper.lambda().like(ZbbzPlanLog::getPlanName, zbbzPlanLogPageParam.getPlanName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzPlanLogPageParam.getSortField(), zbbzPlanLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzPlanLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzPlanLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzPlanLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzPlanLog::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzPlanLogAddParam zbbzPlanLogAddParam) {
        ZbbzPlanLog zbbzPlanLog = BeanUtil.toBean(zbbzPlanLogAddParam, ZbbzPlanLog.class);
        this.save(zbbzPlanLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzPlanLogEditParam zbbzPlanLogEditParam) {
        ZbbzPlanLog zbbzPlanLog = this.queryEntity(zbbzPlanLogEditParam.getId());
        BeanUtil.copyProperties(zbbzPlanLogEditParam, zbbzPlanLog);
        this.updateById(zbbzPlanLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzPlanLogIdParam> zbbzPlanLogIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzPlanLogIdParamList, ZbbzPlanLogIdParam::getId));
    }

    @Override
    public ZbbzPlanLog detail(ZbbzPlanLogIdParam zbbzPlanLogIdParam) {
        return this.queryEntity(zbbzPlanLogIdParam.getId());
    }

    @Override
    public ZbbzPlanLog queryEntity(String id) {
        ZbbzPlanLog zbbzPlanLog = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzPlanLog)) {
            throw new CommonException("任务修改记录不存在，id值为：{}", id);
        }
        return zbbzPlanLog;
    }
}
