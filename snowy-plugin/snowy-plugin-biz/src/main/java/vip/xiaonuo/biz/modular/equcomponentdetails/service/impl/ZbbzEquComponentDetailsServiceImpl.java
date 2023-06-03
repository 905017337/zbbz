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
package vip.xiaonuo.biz.modular.equcomponentdetails.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.mapper.ZbbzEquComponentDetailsMapper;
import vip.xiaonuo.biz.modular.equcomponentdetails.service.ZbbzEquComponentDetailsService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 装备零部件Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
@Service
public class ZbbzEquComponentDetailsServiceImpl extends ServiceImpl<ZbbzEquComponentDetailsMapper, ZbbzEquComponentDetails> implements ZbbzEquComponentDetailsService {

    @Resource
    ZbbzEquComponentDetailsMapper zbbzEquComponentDetailsMapper;
    @Override
    public Page<ZbbzEquComponentDetails> page(ZbbzEquComponentDetailsPageParam zbbzEquComponentDetailsPageParam) {
        QueryWrapper<ZbbzEquComponentDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquComponentDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzEquComponentDetails::getName, zbbzEquComponentDetailsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquComponentDetailsPageParam.getModel())) {
            queryWrapper.lambda().like(ZbbzEquComponentDetails::getModel, zbbzEquComponentDetailsPageParam.getModel());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquComponentDetailsPageParam.getSortField(), zbbzEquComponentDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquComponentDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquComponentDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquComponentDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquComponentDetails::getId);
        }
        Page<ZbbzEquComponentDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzEquComponentDetails> detailsList = detailsPage.getRecords();
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
    public void add(ZbbzEquComponentDetailsAddParam zbbzEquComponentDetailsAddParam) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = BeanUtil.toBean(zbbzEquComponentDetailsAddParam, ZbbzEquComponentDetails.class);
        this.save(zbbzEquComponentDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquComponentDetailsEditParam zbbzEquComponentDetailsEditParam) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = this.queryEntity(zbbzEquComponentDetailsEditParam.getId());
        BeanUtil.copyProperties(zbbzEquComponentDetailsEditParam, zbbzEquComponentDetails);
        this.updateById(zbbzEquComponentDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquComponentDetailsIdParam> zbbzEquComponentDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquComponentDetailsIdParamList, ZbbzEquComponentDetailsIdParam::getId));
    }

    @Override
    public ZbbzEquComponentDetails detail(ZbbzEquComponentDetailsIdParam zbbzEquComponentDetailsIdParam) {
        return this.queryEntity(zbbzEquComponentDetailsIdParam.getId());
    }

    @Override
    public ZbbzEquComponentDetails queryEntity(String id) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquComponentDetails)) {
            throw new CommonException("装备零部件不存在，id值为：{}", id);
        }
        return zbbzEquComponentDetails;
    }

    @Override
    public List<ZbbzEquComponentDetails> findComponentAll(ZbbzEquComponentDetailsNameParam zbbzEquComponentDetailsIdParam) {
        QueryWrapper<ZbbzEquComponentDetails> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(zbbzEquComponentDetailsIdParam.getName())){
            wrapper.lambda().eq(ZbbzEquComponentDetails::getName,zbbzEquComponentDetailsIdParam.getName());
        }
        return zbbzEquComponentDetailsMapper.selectList(wrapper);
    }
}
