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
package vip.xiaonuo.biz.modular.equcategory.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.mapper.ZbbzEquBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.equcategory.dto.ZbbzEquCategoryDto;
import vip.xiaonuo.biz.modular.equcategory.dto.ZbbzEquCategoryListDto;
import vip.xiaonuo.biz.modular.equcategory.param.*;
import vip.xiaonuo.biz.modular.equcomponentdetails.mapper.ZbbzEquComponentDetailsMapper;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.equcategory.entity.ZbbzEquCategory;
import vip.xiaonuo.biz.modular.equcategory.mapper.ZbbzEquCategoryMapper;
import vip.xiaonuo.biz.modular.equcategory.service.ZbbzEquCategoryService;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 装备分类Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:49
 **/
@Service
public class ZbbzEquCategoryServiceImpl extends ServiceImpl<ZbbzEquCategoryMapper, ZbbzEquCategory> implements ZbbzEquCategoryService {

    @Resource
    ZbbzEquCategoryMapper zbbzEquCategoryMapper;
    @Resource
    ZbbzEquBasicsDetailsMapper zbbzEquBasicsDetailsMapper;
    @Resource
    ZbbzEquComponentDetailsMapper zbbzEquComponentDetailsMapper;
    @Override
    public Page<ZbbzEquCategory> page(ZbbzEquCategoryPageParam zbbzEquCategoryPageParam) {
        QueryWrapper<ZbbzEquCategory> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquCategoryPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzEquCategory::getName, zbbzEquCategoryPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquCategoryPageParam.getSortField(), zbbzEquCategoryPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquCategoryPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquCategoryPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquCategoryPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquCategory::getId);
        }
        return this.page(CommonPageRequest.defaultPage(), queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzEquCategoryAddParam zbbzEquCategoryAddParam) {
        ZbbzEquCategory zbbzEquCategory = BeanUtil.toBean(zbbzEquCategoryAddParam, ZbbzEquCategory.class);
        this.save(zbbzEquCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquCategoryEditParam zbbzEquCategoryEditParam) {
        ZbbzEquCategory zbbzEquCategory = this.queryEntity(zbbzEquCategoryEditParam.getId());
        BeanUtil.copyProperties(zbbzEquCategoryEditParam, zbbzEquCategory);
        this.updateById(zbbzEquCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquCategoryIdParam> zbbzEquCategoryIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquCategoryIdParamList, ZbbzEquCategoryIdParam::getId));
    }

    @Override
    public ZbbzEquCategory detail(ZbbzEquCategoryIdParam zbbzEquCategoryIdParam) {
        return this.queryEntity(zbbzEquCategoryIdParam.getId());
    }

    @Override
    public ZbbzEquCategory queryEntity(String id) {
        ZbbzEquCategory zbbzEquCategory = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquCategory)) {
            throw new CommonException("装备分类不存在，id值为：{}", id);
        }
        return zbbzEquCategory;
    }

    @Override
    public ZbbzEquCategoryDto categoryTree() {
        List<ZbbzEquCategoryDto> catewayTree = new ArrayList<>();
        List<ZbbzEquCategory> list = this.list();
        for (ZbbzEquCategory zbbzEquCategory : list) {
            ZbbzEquCategoryDto dto = new ZbbzEquCategoryDto();
            BeanUtil.copyProperties(zbbzEquCategory,dto);
            dto.setTitle(zbbzEquCategory.getName());
            dto.setKey(zbbzEquCategory.getId());
            catewayTree.add(dto);
        }
        return catewayTree.stream().filter(tree -> "0".equals(tree.getParentId())).peek(
                //设置子节点信息
                tree -> tree.setChildren(getChildrenList(tree, catewayTree))
        ).findFirst().orElse(new ZbbzEquCategoryDto());
    }
    private List<ZbbzEquCategoryDto> getChildrenList(ZbbzEquCategoryDto root, List<ZbbzEquCategoryDto> trees) {
        List<ZbbzEquCategoryDto> list = trees.stream().filter(tree ->
                //筛选出下一节点元素
                Objects.equals(tree.getParentId(), root.getId())).map(tree -> {
            //递归set子节点
            List<ZbbzEquCategoryDto> childrenList = this.getChildrenList(tree, trees);
            tree.setChildren(childrenList);
            return tree;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<ZbbzEquBasicsDetails> findEquByCategory(equByIdsParam param) {
        List<String> ids = Arrays.stream(param.getIds()).collect(Collectors.toList());
        param.getStartDate();
        List<ZbbzEquBasicsDetails> arrayList = new ArrayList<>();
        ids.stream().forEach(e->{
            ZbbzEquCategoryParam categoryParam = new ZbbzEquCategoryParam();
            categoryParam.setCategoryId(e);
            categoryParam.setStatus("0");
            categoryParam.setPlanId(param.getPlanId());
            categoryParam.setPlanEndDate(param.getStartDate());
            categoryParam.setPlanStartDate(param.getStartDate());
            List<ZbbzEquBasicsDetails> detailsList = zbbzEquCategoryMapper.findEquByCategory(categoryParam);
            arrayList.addAll(detailsList);
        });
        return arrayList;
    }

    @Override
    public Object[] categoryTreeList() {
        List<ZbbzEquCategoryListDto> catewayTree = new ArrayList<>();
        List<ZbbzEquCategory> list = this.list();
        for (ZbbzEquCategory zbbzEquCategory : list) {
            ZbbzEquCategoryListDto dto = new ZbbzEquCategoryListDto();
            BeanUtil.copyProperties(zbbzEquCategory,dto);
            dto.setLabel(zbbzEquCategory.getName());
            dto.setValue(zbbzEquCategory.getId());
            catewayTree.add(dto);
        }
         ZbbzEquCategoryListDto oredElse = catewayTree.stream().filter(tree -> "0".equals(tree.getParentId())).peek(
                //设置子节点信息
                tree -> tree.setChildren(getChildrenList(tree, catewayTree))
        ).findFirst().orElse(new ZbbzEquCategoryListDto());
        Object[] s =  new Object[1];
        s[0] = oredElse;
        return s;
    }

    private Object[] getChildrenList(ZbbzEquCategoryListDto root, List<ZbbzEquCategoryListDto> trees) {
        List<ZbbzEquCategoryListDto> list = trees.stream().filter(tree ->
                //筛选出下一节点元素
                Objects.equals(tree.getParentId(), root.getId())).map(tree -> {
            //递归set子节点
            Object[] childrenList = this.getChildrenList(tree, trees);
            tree.setChildren(childrenList);
            return tree;
        }).collect(Collectors.toList());

        return list.toArray();
    }
}
