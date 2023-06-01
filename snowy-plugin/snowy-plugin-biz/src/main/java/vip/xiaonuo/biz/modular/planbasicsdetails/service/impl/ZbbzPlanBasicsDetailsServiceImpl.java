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
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.mapper.ZbbzPlanBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsAddParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsEditParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsIdParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsPageParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.service.ZbbzPlanBasicsDetailsService;

import java.util.List;

/**
 * 作战任务Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:40
 **/
@Service
public class ZbbzPlanBasicsDetailsServiceImpl extends ServiceImpl<ZbbzPlanBasicsDetailsMapper, ZbbzPlanBasicsDetails> implements ZbbzPlanBasicsDetailsService {

    @Override
    public Page<ZbbzPlanBasicsDetails> page(ZbbzPlanBasicsDetailsPageParam zbbzPlanBasicsDetailsPageParam) {
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
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = BeanUtil.toBean(zbbzPlanBasicsDetailsAddParam, ZbbzPlanBasicsDetails.class);
        this.save(zbbzPlanBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzPlanBasicsDetailsEditParam zbbzPlanBasicsDetailsEditParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = this.queryEntity(zbbzPlanBasicsDetailsEditParam.getId());
        BeanUtil.copyProperties(zbbzPlanBasicsDetailsEditParam, zbbzPlanBasicsDetails);
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
        return zbbzPlanBasicsDetails;
    }
}
