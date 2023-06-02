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
package vip.xiaonuo.biz.modular.planbasicsdetails.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.*;
import vip.xiaonuo.biz.modular.planequ.dto.ZbbzPlanEquDto;
import vip.xiaonuo.biz.modular.planequ.entity.ZbbzPlanEqu;
import vip.xiaonuo.biz.modular.planequ.mapper.ZbbzPlanEquMapper;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.mapper.ZbbzPlanBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.service.ZbbzPlanBasicsDetailsService;

import javax.annotation.Resource;
import java.util.*;

/**
 * 作战任务Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:40
 **/
@Service
public class ZbbzPlanBasicsDetailsServiceImpl extends ServiceImpl<ZbbzPlanBasicsDetailsMapper, ZbbzPlanBasicsDetails> implements ZbbzPlanBasicsDetailsService {

    @Resource
    ZbbzPlanEquMapper zbbzPlanEquMapper;
    @Override
    public Page<ZbbzPlanBasicsDetailsDto> page(ZbbzPlanBasicsDetailsPageParam zbbzPlanBasicsDetailsPageParam) {
        QueryWrapper<ZbbzPlanBasicsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzPlanBasicsDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzPlanBasicsDetails::getName, zbbzPlanBasicsDetailsPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzPlanBasicsDetailsPageParam.getSortField(), zbbzPlanBasicsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzPlanBasicsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzPlanBasicsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzPlanBasicsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzPlanBasicsDetails::getId);
        }
        Page<ZbbzPlanBasicsDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzPlanBasicsDetails> records = detailsPage.getRecords();
        Page<ZbbzPlanBasicsDetailsDto> result = new Page<>();
        //任务选择的装备
        List<ZbbzPlanBasicsDetailsDto> arrayList1 = new ArrayList<>();
        //渲染装备分类树

        records.stream().forEach(e->{
            Set<String> treeSelect = new HashSet<>();
            ZbbzPlanBasicsDetailsDto planDto = new ZbbzPlanBasicsDetailsDto();
            BeanUtil.copyProperties(e,planDto);
            QueryWrapper<ZbbzPlanEqu> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(ZbbzPlanEqu::getPlanId,e.getId());
            List<ZbbzPlanEqu> equList = zbbzPlanEquMapper.selectList(wrapper);
            ArrayList<ZbbzPlanEquDto> arrayList = new ArrayList<>();
            equList.stream().forEach(item->{
                treeSelect.add(item.getEquId());
                ZbbzPlanEquDto dto = new ZbbzPlanEquDto();
                BeanUtil.copyProperties(item,dto);
                arrayList.add(dto);
            });
            planDto.setZbbzEquBasicsDetailsParamList(arrayList);
            String[] arr = treeSelect.toArray(new String[treeSelect.size()]);
            planDto.setTreeSelect(arr);
            arrayList1.add(planDto);
        });
        BeanUtil.copyProperties(detailsPage,result);
        result.setRecords(arrayList1);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = BeanUtil.toBean(zbbzPlanBasicsDetailsAddParam, ZbbzPlanBasicsDetails.class);
        this.save(zbbzPlanBasicsDetails);
        updatePlanEquByPlanId(zbbzPlanBasicsDetailsAddParam);
    }

    public void updatePlanEquByPlanId(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam){
        zbbzPlanBasicsDetailsAddParam.getZbbzEquBasicsDetailsParamList().stream().forEach(e->{
            ZbbzPlanEqu planEqu = new ZbbzPlanEqu();
            planEqu.setPlanId(zbbzPlanBasicsDetailsAddParam.getId());
            planEqu.setName(e.getName());
            planEqu.setModel(e.getModel());
            zbbzPlanEquMapper.insert(planEqu);
        });
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = this.queryEntity(zbbzPlanBasicsDetailsAddParam.getId());
        BeanUtil.copyProperties(zbbzPlanBasicsDetailsAddParam, zbbzPlanBasicsDetails);
        QueryWrapper<ZbbzPlanEqu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ZbbzPlanEqu::getPlanId,zbbzPlanBasicsDetailsAddParam.getId());
        zbbzPlanEquMapper.delete(queryWrapper);
        updatePlanEquByPlanId(zbbzPlanBasicsDetailsAddParam);
        this.updateById(zbbzPlanBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzPlanBasicsDetailsIdParam> zbbzPlanBasicsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzPlanBasicsDetailsIdParamList, ZbbzPlanBasicsDetailsIdParam::getId));
    }

    @Override
    public ZbbzPlanBasicsDetails detail(ZbbzPlanBasicsDetailsIdParam zbbzPlanBasicsDetailsIdParam) {
        return this.queryEntity(zbbzPlanBasicsDetailsIdParam.getId());
    }

    @Override
    public ZbbzPlanBasicsDetails queryEntity(String id) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzPlanBasicsDetails)) {
            throw new CommonException("作战任务不存在，id值为：{}", id);
        }
        QueryWrapper<ZbbzPlanEqu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZbbzPlanEqu::getPlanId,zbbzPlanBasicsDetails.getId());
        ZbbzPlanBasicsDetailsDto detailsDto = new ZbbzPlanBasicsDetailsDto();
        BeanUtil.copyProperties(zbbzPlanBasicsDetails,detailsDto);
        ArrayList<ZbbzPlanEquDto> list = new ArrayList<>();
        zbbzPlanEquMapper.selectList(wrapper).stream().forEach(e->{
            ZbbzPlanEquDto dto = new ZbbzPlanEquDto();
            BeanUtil.copyProperties(e,dto);
            list.add(dto);
        });
        detailsDto.setZbbzEquBasicsDetailsParamList(list);
        return zbbzPlanBasicsDetails;
    }
}
