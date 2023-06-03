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
package vip.xiaonuo.biz.modular.equlog.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.equlog.entity.ZbbzEquLog;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogAddParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogEditParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogIdParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogPageParam;
import vip.xiaonuo.biz.modular.equlog.service.ZbbzEquLogService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 装备使用日志控制器
 *
 * @author czh
 * @date  2023/06/03 21:43
 */
@Api(tags = "装备使用日志控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzEquLogController {

    @Resource
    private ZbbzEquLogService zbbzEquLogService;

    /**
     * 获取装备使用日志分页
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取装备使用日志分页")
    @SaCheckPermission("/biz/equlog/page")
    @GetMapping("/biz/equlog/page")
    public CommonResult<Page<ZbbzEquLog>> page(ZbbzEquLogPageParam zbbzEquLogPageParam) {
        return CommonResult.data(zbbzEquLogService.page(zbbzEquLogPageParam));
    }

    /**
     * 添加装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加装备使用日志")
    @CommonLog("添加装备使用日志")
    @SaCheckPermission("/biz/equlog/add")
    @PostMapping("/biz/equlog/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzEquLogAddParam zbbzEquLogAddParam) {
        zbbzEquLogService.add(zbbzEquLogAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑装备使用日志")
    @CommonLog("编辑装备使用日志")
    @SaCheckPermission("/biz/equlog/edit")
    @PostMapping("/biz/equlog/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzEquLogEditParam zbbzEquLogEditParam) {
        zbbzEquLogService.edit(zbbzEquLogEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除装备使用日志")
    @CommonLog("删除装备使用日志")
    @SaCheckPermission("/biz/equlog/delete")
    @PostMapping("/biz/equlog/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzEquLogIdParam> zbbzEquLogIdParamList) {
        zbbzEquLogService.delete(zbbzEquLogIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取装备使用日志详情
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取装备使用日志详情")
    @SaCheckPermission("/biz/equlog/detail")
    @GetMapping("/biz/equlog/detail")
    public CommonResult<ZbbzEquLog> detail(@Valid ZbbzEquLogIdParam zbbzEquLogIdParam) {
        return CommonResult.data(zbbzEquLogService.detail(zbbzEquLogIdParam));
    }
}
