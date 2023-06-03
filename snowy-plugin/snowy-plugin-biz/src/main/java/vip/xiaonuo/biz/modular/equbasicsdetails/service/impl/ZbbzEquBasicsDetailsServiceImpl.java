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
package vip.xiaonuo.biz.modular.equbasicsdetails.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.equbasicsdetails.dto.ZbbzEquBasicsDetailsDto;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.mapper.ZbbzEquComponentDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.mapper.ZbbzEquBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsAddParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsEditParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsIdParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsPageParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.service.ZbbzEquBasicsDetailsService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础信息Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
@Service
public class ZbbzEquBasicsDetailsServiceImpl extends ServiceImpl<ZbbzEquBasicsDetailsMapper, ZbbzEquBasicsDetails> implements ZbbzEquBasicsDetailsService {

    @Resource
    ZbbzEquComponentDetailsMapper zbbzEquComponentDetailsMapper;
    @Override
    public Page<ZbbzEquBasicsDetailsDto> page(ZbbzEquBasicsDetailsPageParam zbbzEquBasicsDetailsPageParam) {
        QueryWrapper<ZbbzEquBasicsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzEquBasicsDetails::getName, zbbzEquBasicsDetailsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getModel())) {
            queryWrapper.lambda().like(ZbbzEquBasicsDetails::getModel, zbbzEquBasicsDetailsPageParam.getModel());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getResidueLifetime())) {
            queryWrapper.lambda().eq(ZbbzEquBasicsDetails::getResidueLifetime, zbbzEquBasicsDetailsPageParam.getResidueLifetime());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getStatus())) {
            queryWrapper.lambda().eq(ZbbzEquBasicsDetails::getStatus, zbbzEquBasicsDetailsPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquBasicsDetailsPageParam.getSortField(), zbbzEquBasicsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquBasicsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquBasicsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquBasicsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquBasicsDetails::getId);
        }
        Page<ZbbzEquBasicsDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzEquBasicsDetails> detailsList = detailsPage.getRecords();
        detailsList.stream().forEach(e->{
            String residueLifetime = e.getResidueLifetime();
            if("0".equals(residueLifetime)) e.setResidueLifetime("新品");
            if("1".equals(residueLifetime)) e.setResidueLifetime("堪用");
            if("2".equals(residueLifetime)) e.setResidueLifetime("待修");
            if("3".equals(residueLifetime)) e.setResidueLifetime("损毁");
            String status = e.getStatus();
            if("0".equals(status)) e.setStatus("空闲");
            if("1".equals(status)) e.setStatus("占用");
        });
        Page<ZbbzEquBasicsDetailsDto> result = new Page<>();
        ArrayList<ZbbzEquBasicsDetailsDto> arrayList = new ArrayList<>();
        List<ZbbzEquBasicsDetails> records = detailsPage.setRecords(detailsList).getRecords();
        records.stream().forEach(e->{
            ZbbzEquBasicsDetailsDto dto = new ZbbzEquBasicsDetailsDto();
            QueryWrapper<ZbbzEquComponentDetails> qw = new QueryWrapper<>();
            qw.lambda().eq(ZbbzEquComponentDetails::getEquId,e.getId());
            List<ZbbzEquComponentDetails> selectedList = zbbzEquComponentDetailsMapper.selectList(qw);
            BeanUtil.copyProperties(e,dto);
            dto.setZbbzEquComponentDetailsList(selectedList);
            arrayList.add(dto);
        });
        BeanUtil.copyProperties(detailsPage,result);
        result.setRecords(arrayList);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzEquBasicsDetailsAddParam zbbzEquBasicsDetailsAddParam) {
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = BeanUtil.toBean(zbbzEquBasicsDetailsAddParam, ZbbzEquBasicsDetails.class);
        this.save(zbbzEquBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquBasicsDetailsEditParam zbbzEquBasicsDetailsEditParam) {
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = this.queryEntity(zbbzEquBasicsDetailsEditParam.getId());
        BeanUtil.copyProperties(zbbzEquBasicsDetailsEditParam, zbbzEquBasicsDetails);
        this.updateById(zbbzEquBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquBasicsDetailsIdParam> zbbzEquBasicsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquBasicsDetailsIdParamList, ZbbzEquBasicsDetailsIdParam::getId));
    }

    @Override
    public ZbbzEquBasicsDetails detail(ZbbzEquBasicsDetailsIdParam zbbzEquBasicsDetailsIdParam) {
        return this.queryEntity(zbbzEquBasicsDetailsIdParam.getId());
    }

    @Override
    public ZbbzEquBasicsDetails queryEntity(String id) {
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquBasicsDetails)) {
            throw new CommonException("基础信息不存在，id值为：{}", id);
        }
        return zbbzEquBasicsDetails;
    }
}
