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
package vip.xiaonuo.biz.modular.equcategory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vip.xiaonuo.biz.modular.equcategory.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.equcategory.entity.ZbbzEquCategory;
import vip.xiaonuo.biz.modular.equcategory.service.ZbbzEquCategoryService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 装备分类控制器
 *
 * @author czh
 * @date  2023/06/01 12:49
 */
@Api(tags = "装备分类控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzEquCategoryController {

    @Resource
    private ZbbzEquCategoryService zbbzEquCategoryService;

    /**
     * 获取装备分类分页
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取装备分类分页")
    @SaCheckPermission("/biz/equcategory/page")
    @GetMapping("/biz/equcategory/page")
    public CommonResult<Page<ZbbzEquCategory>> page(ZbbzEquCategoryPageParam zbbzEquCategoryPageParam) {
        return CommonResult.data(zbbzEquCategoryService.page(zbbzEquCategoryPageParam));
    }

    /**
     * 添加装备分类
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加装备分类")
    @CommonLog("添加装备分类")
    @SaCheckPermission("/biz/equcategory/add")
    @PostMapping("/biz/equcategory/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzEquCategoryAddParam zbbzEquCategoryAddParam) {
        zbbzEquCategoryService.add(zbbzEquCategoryAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑装备分类
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑装备分类")
    @CommonLog("编辑装备分类")
    @SaCheckPermission("/biz/equcategory/edit")
    @PostMapping("/biz/equcategory/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzEquCategoryEditParam zbbzEquCategoryEditParam) {
        zbbzEquCategoryService.edit(zbbzEquCategoryEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除装备分类
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除装备分类")
    @CommonLog("删除装备分类")
    @SaCheckPermission("/biz/equcategory/delete")
    @PostMapping("/biz/equcategory/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzEquCategoryIdParam> zbbzEquCategoryIdParamList) {
        zbbzEquCategoryService.delete(zbbzEquCategoryIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取装备分类详情
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取装备分类详情")
    @SaCheckPermission("/biz/equcategory/detail")
    @GetMapping("/biz/equcategory/detail")
    public CommonResult<ZbbzEquCategory> detail(@Valid ZbbzEquCategoryIdParam zbbzEquCategoryIdParam) {
        return CommonResult.data(zbbzEquCategoryService.detail(zbbzEquCategoryIdParam));
    }
    @ApiOperationSupport(order = 6)
    @ApiOperation("获取装备树")
    @SaCheckPermission("/biz/equcategory/categoryTree")
    @GetMapping("/biz/equcategory/categoryTree")
    public CommonResult categoryTree(){
        return CommonResult.data(zbbzEquCategoryService.categoryTree());
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("获取装备树对应的武器")
    @SaCheckPermission("/biz/equcategory/findEquByCategory")
    @PostMapping("/biz/equcategory/findEquByCategory")
    public CommonResult findEquByCategory(@RequestBody equByIdsParam param){
        final List<String> ids = Arrays.stream(param.getIds()).collect(Collectors.toList());

        return CommonResult.data(zbbzEquCategoryService.findEquByCategory(ids));
    }


}
