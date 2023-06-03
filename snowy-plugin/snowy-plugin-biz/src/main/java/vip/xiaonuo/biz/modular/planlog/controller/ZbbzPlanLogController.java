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
package vip.xiaonuo.biz.modular.planlog.controller;

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
import vip.xiaonuo.biz.modular.planlog.entity.ZbbzPlanLog;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogAddParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogEditParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogIdParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogPageParam;
import vip.xiaonuo.biz.modular.planlog.service.ZbbzPlanLogService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 任务修改记录控制器
 *
 * @author czh
 * @date  2023/06/03 21:29
 */
@Api(tags = "任务修改记录控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzPlanLogController {

    @Resource
    private ZbbzPlanLogService zbbzPlanLogService;

    /**
     * 获取任务修改记录分页
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取任务修改记录分页")
    @SaCheckPermission("/biz/planlog/page")
    @GetMapping("/biz/planlog/page")
    public CommonResult<Page<ZbbzPlanLog>> page(ZbbzPlanLogPageParam zbbzPlanLogPageParam) {
        return CommonResult.data(zbbzPlanLogService.page(zbbzPlanLogPageParam));
    }

    /**
     * 添加任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加任务修改记录")
    @CommonLog("添加任务修改记录")
    @SaCheckPermission("/biz/planlog/add")
    @PostMapping("/biz/planlog/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzPlanLogAddParam zbbzPlanLogAddParam) {
        zbbzPlanLogService.add(zbbzPlanLogAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑任务修改记录")
    @CommonLog("编辑任务修改记录")
    @SaCheckPermission("/biz/planlog/edit")
    @PostMapping("/biz/planlog/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzPlanLogEditParam zbbzPlanLogEditParam) {
        zbbzPlanLogService.edit(zbbzPlanLogEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除任务修改记录")
    @CommonLog("删除任务修改记录")
    @SaCheckPermission("/biz/planlog/delete")
    @PostMapping("/biz/planlog/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzPlanLogIdParam> zbbzPlanLogIdParamList) {
        zbbzPlanLogService.delete(zbbzPlanLogIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务修改记录详情
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取任务修改记录详情")
    @SaCheckPermission("/biz/planlog/detail")
    @GetMapping("/biz/planlog/detail")
    public CommonResult<ZbbzPlanLog> detail(@Valid ZbbzPlanLogIdParam zbbzPlanLogIdParam) {
        return CommonResult.data(zbbzPlanLogService.detail(zbbzPlanLogIdParam));
    }
}
