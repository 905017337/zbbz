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
package vip.xiaonuo.biz.modular.maintainteamdetails.service.impl;

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
import vip.xiaonuo.biz.modular.maintainteamdetails.entity.ZbbzMaintainTeamDetails;
import vip.xiaonuo.biz.modular.maintainteamdetails.mapper.ZbbzMaintainTeamDetailsMapper;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsAddParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsEditParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsIdParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsPageParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.service.ZbbzMaintainTeamDetailsService;

import java.util.List;

/**
 * 基础信息Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:43
 **/
@Service
public class ZbbzMaintainTeamDetailsServiceImpl extends ServiceImpl<ZbbzMaintainTeamDetailsMapper, ZbbzMaintainTeamDetails> implements ZbbzMaintainTeamDetailsService {

    @Override
    public Page<ZbbzMaintainTeamDetails> page(ZbbzMaintainTeamDetailsPageParam zbbzMaintainTeamDetailsPageParam) {
        QueryWrapper<ZbbzMaintainTeamDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzMaintainTeamDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzMaintainTeamDetails::getName, zbbzMaintainTeamDetailsPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzMaintainTeamDetailsPageParam.getSortField(), zbbzMaintainTeamDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzMaintainTeamDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzMaintainTeamDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzMaintainTeamDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzMaintainTeamDetails::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzMaintainTeamDetailsAddParam zbbzMaintainTeamDetailsAddParam) {
        ZbbzMaintainTeamDetails zbbzMaintainTeamDetails = BeanUtil.toBean(zbbzMaintainTeamDetailsAddParam, ZbbzMaintainTeamDetails.class);
        this.save(zbbzMaintainTeamDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzMaintainTeamDetailsEditParam zbbzMaintainTeamDetailsEditParam) {
        ZbbzMaintainTeamDetails zbbzMaintainTeamDetails = this.queryEntity(zbbzMaintainTeamDetailsEditParam.getId());
        BeanUtil.copyProperties(zbbzMaintainTeamDetailsEditParam, zbbzMaintainTeamDetails);
        this.updateById(zbbzMaintainTeamDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzMaintainTeamDetailsIdParam> zbbzMaintainTeamDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzMaintainTeamDetailsIdParamList, ZbbzMaintainTeamDetailsIdParam::getId));
    }

    @Override
    public ZbbzMaintainTeamDetails detail(ZbbzMaintainTeamDetailsIdParam zbbzMaintainTeamDetailsIdParam) {
        return this.queryEntity(zbbzMaintainTeamDetailsIdParam.getId());
    }

    @Override
    public ZbbzMaintainTeamDetails queryEntity(String id) {
        ZbbzMaintainTeamDetails zbbzMaintainTeamDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzMaintainTeamDetails)) {
            throw new CommonException("基础信息不存在，id值为：{}", id);
        }
        return zbbzMaintainTeamDetails;
    }
}
