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
package vip.xiaonuo.biz.modular.planequ.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.planequ.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.planequ.entity.ZbbzPlanEqu;
import vip.xiaonuo.biz.modular.planequ.mapper.ZbbzPlanEquMapper;
import vip.xiaonuo.biz.modular.planequ.service.ZbbzPlanEquService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 任务装备配置Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:42
 **/
@Service
public class ZbbzPlanEquServiceImpl extends ServiceImpl<ZbbzPlanEquMapper, ZbbzPlanEqu> implements ZbbzPlanEquService {


    @Resource
    private ZbbzPlanEquMapper zbbzPlanEquMapper;
    @Override
    public Page<ZbbzPlanEqu> page(ZbbzPlanEquPageParam zbbzPlanEquPageParam) {
        QueryWrapper<ZbbzPlanEqu> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzPlanEquPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzPlanEqu::getName, zbbzPlanEquPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(zbbzPlanEquPageParam.getModel())) {
            queryWrapper.lambda().like(ZbbzPlanEqu::getModel, zbbzPlanEquPageParam.getModel());
        }
        if(ObjectUtil.isNotEmpty(zbbzPlanEquPageParam.getResidueLifetime())) {
            queryWrapper.lambda().eq(ZbbzPlanEqu::getResidueLifetime, zbbzPlanEquPageParam.getResidueLifetime());
        }
        if(ObjectUtil.isNotEmpty(zbbzPlanEquPageParam.getWeight())) {
            queryWrapper.lambda().eq(ZbbzPlanEqu::getWeight, zbbzPlanEquPageParam.getWeight());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzPlanEquPageParam.getSortField(), zbbzPlanEquPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzPlanEquPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzPlanEquPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzPlanEquPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzPlanEqu::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzPlanEquAddParam zbbzPlanEquAddParam) {
        ZbbzPlanEqu zbbzPlanEqu = BeanUtil.toBean(zbbzPlanEquAddParam, ZbbzPlanEqu.class);
        this.save(zbbzPlanEqu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzPlanEquEditParam zbbzPlanEquEditParam) {
        ZbbzPlanEqu zbbzPlanEqu = this.queryEntity(zbbzPlanEquEditParam.getId());
        BeanUtil.copyProperties(zbbzPlanEquEditParam, zbbzPlanEqu);
        this.updateById(zbbzPlanEqu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzPlanEquIdParam> zbbzPlanEquIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzPlanEquIdParamList, ZbbzPlanEquIdParam::getId));
    }

    @Override
    public ZbbzPlanEqu detail(ZbbzPlanEquIdParam zbbzPlanEquIdParam) {
        return this.queryEntity(zbbzPlanEquIdParam.getId());
    }

    @Override
    public ZbbzPlanEqu queryEntity(String id) {
        ZbbzPlanEqu zbbzPlanEqu = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzPlanEqu)) {
            throw new CommonException("任务装备配置不存在，id值为：{}", id);
        }
        return zbbzPlanEqu;
    }

    @Override
    public List<ZbbzPlanEqu> findeqyByPlanId(ZbbzEquByPlanIdParam zbbzEquByPlanIdParam) {
        QueryWrapper<ZbbzPlanEqu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZbbzPlanEqu::getPlanId,zbbzEquByPlanIdParam.getPlanId());
        return  zbbzPlanEquMapper.selectList(wrapper);
    }
}
