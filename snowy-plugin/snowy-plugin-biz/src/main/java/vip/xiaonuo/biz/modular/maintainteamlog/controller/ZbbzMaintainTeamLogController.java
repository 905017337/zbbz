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
package vip.xiaonuo.biz.modular.maintainteamlog.controller;

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
import vip.xiaonuo.biz.modular.maintainteamlog.entity.ZbbzMaintainTeamLog;
import vip.xiaonuo.biz.modular.maintainteamlog.param.ZbbzMaintainTeamLogAddParam;
import vip.xiaonuo.biz.modular.maintainteamlog.param.ZbbzMaintainTeamLogEditParam;
import vip.xiaonuo.biz.modular.maintainteamlog.param.ZbbzMaintainTeamLogIdParam;
import vip.xiaonuo.biz.modular.maintainteamlog.param.ZbbzMaintainTeamLogPageParam;
import vip.xiaonuo.biz.modular.maintainteamlog.service.ZbbzMaintainTeamLogService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 维修团队日志控制器
 *
 * @author czh
 * @date  2023/06/03 21:38
 */
@Api(tags = "维修团队日志控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzMaintainTeamLogController {

    @Resource
    private ZbbzMaintainTeamLogService zbbzMaintainTeamLogService;

    /**
     * 获取维修团队日志分页
     *
     * @author czh
     * @date  2023/06/03 21:38
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取维修团队日志分页")
    @SaCheckPermission("/biz/maintainteamlog/page")
    @GetMapping("/biz/maintainteamlog/page")
    public CommonResult<Page<ZbbzMaintainTeamLog>> page(ZbbzMaintainTeamLogPageParam zbbzMaintainTeamLogPageParam) {
        return CommonResult.data(zbbzMaintainTeamLogService.page(zbbzMaintainTeamLogPageParam));
    }

    /**
     * 添加维修团队日志
     *
     * @author czh
     * @date  2023/06/03 21:38
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加维修团队日志")
    @CommonLog("添加维修团队日志")
    @SaCheckPermission("/biz/maintainteamlog/add")
    @PostMapping("/biz/maintainteamlog/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzMaintainTeamLogAddParam zbbzMaintainTeamLogAddParam) {
        zbbzMaintainTeamLogService.add(zbbzMaintainTeamLogAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑维修团队日志
     *
     * @author czh
     * @date  2023/06/03 21:38
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑维修团队日志")
    @CommonLog("编辑维修团队日志")
    @SaCheckPermission("/biz/maintainteamlog/edit")
    @PostMapping("/biz/maintainteamlog/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzMaintainTeamLogEditParam zbbzMaintainTeamLogEditParam) {
        zbbzMaintainTeamLogService.edit(zbbzMaintainTeamLogEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除维修团队日志
     *
     * @author czh
     * @date  2023/06/03 21:38
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除维修团队日志")
    @CommonLog("删除维修团队日志")
    @SaCheckPermission("/biz/maintainteamlog/delete")
    @PostMapping("/biz/maintainteamlog/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzMaintainTeamLogIdParam> zbbzMaintainTeamLogIdParamList) {
        zbbzMaintainTeamLogService.delete(zbbzMaintainTeamLogIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取维修团队日志详情
     *
     * @author czh
     * @date  2023/06/03 21:38
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取维修团队日志详情")
    @SaCheckPermission("/biz/maintainteamlog/detail")
    @GetMapping("/biz/maintainteamlog/detail")
    public CommonResult<ZbbzMaintainTeamLog> detail(@Valid ZbbzMaintainTeamLogIdParam zbbzMaintainTeamLogIdParam) {
        return CommonResult.data(zbbzMaintainTeamLogService.detail(zbbzMaintainTeamLogIdParam));
    }
}
