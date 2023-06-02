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
package vip.xiaonuo.biz.modular.maintainteamcapacity.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.maintainteamcapacity.entity.ZbbzMaintainTeamCapacity;
import vip.xiaonuo.biz.modular.maintainteamcapacity.mapper.ZbbzMaintainTeamCapacityMapper;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityAddParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityEditParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityIdParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityPageParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.service.ZbbzMaintainTeamCapacityService;

import java.util.List;

/**
 * 零部件管理Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:49
 **/
@Service
public class ZbbzMaintainTeamCapacityServiceImpl extends ServiceImpl<ZbbzMaintainTeamCapacityMapper, ZbbzMaintainTeamCapacity> implements ZbbzMaintainTeamCapacityService {

    @Override
    public Page<ZbbzMaintainTeamCapacity> page(ZbbzMaintainTeamCapacityPageParam zbbzMaintainTeamCapacityPageParam) {
        QueryWrapper<ZbbzMaintainTeamCapacity> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzMaintainTeamCapacityPageParam.getEquComponentName())) {
            queryWrapper.lambda().like(ZbbzMaintainTeamCapacity::getEquComponentName, zbbzMaintainTeamCapacityPageParam.getEquComponentName());
        }
        if(ObjectUtil.isNotEmpty(zbbzMaintainTeamCapacityPageParam.getCapacity())) {
            queryWrapper.lambda().like(ZbbzMaintainTeamCapacity::getCapacity, zbbzMaintainTeamCapacityPageParam.getCapacity());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzMaintainTeamCapacityPageParam.getSortField(), zbbzMaintainTeamCapacityPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzMaintainTeamCapacityPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzMaintainTeamCapacityPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzMaintainTeamCapacityPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzMaintainTeamCapacity::getId);
        }
        Page<ZbbzMaintainTeamCapacity> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzMaintainTeamCapacity> detailsList = detailsPage.getRecords();
        detailsList.stream().forEach(e->{
            String residueLifetime = e.getResidueLifetime();
            if("0".equals(residueLifetime)) e.setResidueLifetime("新品");
            if("1".equals(residueLifetime)) e.setResidueLifetime("堪用");
            if("2".equals(residueLifetime)) e.setResidueLifetime("待修");
            if("3".equals(residueLifetime)) e.setResidueLifetime("损毁");
        });
        return detailsPage.setRecords(detailsList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzMaintainTeamCapacityAddParam zbbzMaintainTeamCapacityAddParam) {
        ZbbzMaintainTeamCapacity zbbzMaintainTeamCapacity = BeanUtil.toBean(zbbzMaintainTeamCapacityAddParam, ZbbzMaintainTeamCapacity.class);
        this.save(zbbzMaintainTeamCapacity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzMaintainTeamCapacityEditParam zbbzMaintainTeamCapacityEditParam) {
        ZbbzMaintainTeamCapacity zbbzMaintainTeamCapacity = this.queryEntity(zbbzMaintainTeamCapacityEditParam.getId());
        BeanUtil.copyProperties(zbbzMaintainTeamCapacityEditParam, zbbzMaintainTeamCapacity);
        this.updateById(zbbzMaintainTeamCapacity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzMaintainTeamCapacityIdParam> zbbzMaintainTeamCapacityIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzMaintainTeamCapacityIdParamList, ZbbzMaintainTeamCapacityIdParam::getId));
    }

    @Override
    public ZbbzMaintainTeamCapacity detail(ZbbzMaintainTeamCapacityIdParam zbbzMaintainTeamCapacityIdParam) {
        return this.queryEntity(zbbzMaintainTeamCapacityIdParam.getId());
    }

    @Override
    public ZbbzMaintainTeamCapacity queryEntity(String id) {
        ZbbzMaintainTeamCapacity zbbzMaintainTeamCapacity = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzMaintainTeamCapacity)) {
            throw new CommonException("零部件管理不存在，id值为：{}", id);
        }
        return zbbzMaintainTeamCapacity;
    }
}
