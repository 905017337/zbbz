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
package vip.xiaonuo.biz.modular.maintainplanlog.controller;

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
import vip.xiaonuo.biz.modular.maintainplanlog.entity.ZbbzMaintainPlanLog;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogAddParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogEditParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogIdParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogPageParam;
import vip.xiaonuo.biz.modular.maintainplanlog.service.ZbbzMaintainPlanLogService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 *  维护作战任务日志控制器
 *
 * @author czh
 * @date  2023/06/03 21:40
 */
@Api(tags = " 维护作战任务日志控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzMaintainPlanLogController {

    @Resource
    private ZbbzMaintainPlanLogService zbbzMaintainPlanLogService;

    /**
     * 获取 维护作战任务日志分页
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取 维护作战任务日志分页")
    @SaCheckPermission("/biz/maintainplanlog/page")
    @GetMapping("/biz/maintainplanlog/page")
    public CommonResult<Page<ZbbzMaintainPlanLog>> page(ZbbzMaintainPlanLogPageParam zbbzMaintainPlanLogPageParam) {
        return CommonResult.data(zbbzMaintainPlanLogService.page(zbbzMaintainPlanLogPageParam));
    }

    /**
     * 添加 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加 维护作战任务日志")
    @CommonLog("添加 维护作战任务日志")
    @SaCheckPermission("/biz/maintainplanlog/add")
    @PostMapping("/biz/maintainplanlog/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzMaintainPlanLogAddParam zbbzMaintainPlanLogAddParam) {
        zbbzMaintainPlanLogService.add(zbbzMaintainPlanLogAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑 维护作战任务日志")
    @CommonLog("编辑 维护作战任务日志")
    @SaCheckPermission("/biz/maintainplanlog/edit")
    @PostMapping("/biz/maintainplanlog/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzMaintainPlanLogEditParam zbbzMaintainPlanLogEditParam) {
        zbbzMaintainPlanLogService.edit(zbbzMaintainPlanLogEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除 维护作战任务日志")
    @CommonLog("删除 维护作战任务日志")
    @SaCheckPermission("/biz/maintainplanlog/delete")
    @PostMapping("/biz/maintainplanlog/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzMaintainPlanLogIdParam> zbbzMaintainPlanLogIdParamList) {
        zbbzMaintainPlanLogService.delete(zbbzMaintainPlanLogIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取 维护作战任务日志详情
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取 维护作战任务日志详情")
    @SaCheckPermission("/biz/maintainplanlog/detail")
    @GetMapping("/biz/maintainplanlog/detail")
    public CommonResult<ZbbzMaintainPlanLog> detail(@Valid ZbbzMaintainPlanLogIdParam zbbzMaintainPlanLogIdParam) {
        return CommonResult.data(zbbzMaintainPlanLogService.detail(zbbzMaintainPlanLogIdParam));
    }
}
