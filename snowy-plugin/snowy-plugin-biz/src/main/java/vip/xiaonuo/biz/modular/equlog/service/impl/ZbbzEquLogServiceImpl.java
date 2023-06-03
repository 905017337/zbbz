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
package vip.xiaonuo.biz.modular.equlog.service.impl;

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
import vip.xiaonuo.biz.modular.equlog.entity.ZbbzEquLog;
import vip.xiaonuo.biz.modular.equlog.mapper.ZbbzEquLogMapper;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogAddParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogEditParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogIdParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogPageParam;
import vip.xiaonuo.biz.modular.equlog.service.ZbbzEquLogService;

import java.util.List;

/**
 * 装备使用日志Service接口实现类
 *
 * @author czh
 * @date  2023/06/03 21:43
 **/
@Service
public class ZbbzEquLogServiceImpl extends ServiceImpl<ZbbzEquLogMapper, ZbbzEquLog> implements ZbbzEquLogService {

    @Override
    public Page<ZbbzEquLog> page(ZbbzEquLogPageParam zbbzEquLogPageParam) {
        QueryWrapper<ZbbzEquLog> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquLogPageParam.getEquName())) {
            queryWrapper.lambda().like(ZbbzEquLog::getEquName, zbbzEquLogPageParam.getEquName());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquLogPageParam.getPlanName())) {
            queryWrapper.lambda().like(ZbbzEquLog::getPlanName, zbbzEquLogPageParam.getPlanName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquLogPageParam.getSortField(), zbbzEquLogPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquLogPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquLogPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquLogPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquLog::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzEquLogAddParam zbbzEquLogAddParam) {
        ZbbzEquLog zbbzEquLog = BeanUtil.toBean(zbbzEquLogAddParam, ZbbzEquLog.class);
        this.save(zbbzEquLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquLogEditParam zbbzEquLogEditParam) {
        ZbbzEquLog zbbzEquLog = this.queryEntity(zbbzEquLogEditParam.getId());
        BeanUtil.copyProperties(zbbzEquLogEditParam, zbbzEquLog);
        this.updateById(zbbzEquLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquLogIdParam> zbbzEquLogIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquLogIdParamList, ZbbzEquLogIdParam::getId));
    }

    @Override
    public ZbbzEquLog detail(ZbbzEquLogIdParam zbbzEquLogIdParam) {
        return this.queryEntity(zbbzEquLogIdParam.getId());
    }

    @Override
    public ZbbzEquLog queryEntity(String id) {
        ZbbzEquLog zbbzEquLog = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquLog)) {
            throw new CommonException("装备使用日志不存在，id值为：{}", id);
        }
        return zbbzEquLog;
    }
}
