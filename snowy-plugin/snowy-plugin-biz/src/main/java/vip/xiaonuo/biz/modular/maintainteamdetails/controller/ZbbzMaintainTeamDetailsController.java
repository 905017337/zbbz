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
package vip.xiaonuo.biz.modular.maintainteamdetails.controller;

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
import vip.xiaonuo.biz.modular.maintainteamdetails.entity.ZbbzMaintainTeamDetails;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsAddParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsEditParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsIdParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsPageParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.service.ZbbzMaintainTeamDetailsService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 基础信息控制器
 *
 * @author czh
 * @date  2023/06/01 12:43
 */
@Api(tags = "基础信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzMaintainTeamDetailsController {

    @Resource
    private ZbbzMaintainTeamDetailsService zbbzMaintainTeamDetailsService;

    /**
     * 获取基础信息分页
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取基础信息分页")
    @SaCheckPermission("/biz/maintainteamdetails/page")
    @GetMapping("/biz/maintainteamdetails/page")
    public CommonResult<Page<ZbbzMaintainTeamDetails>> page(ZbbzMaintainTeamDetailsPageParam zbbzMaintainTeamDetailsPageParam) {
        return CommonResult.data(zbbzMaintainTeamDetailsService.page(zbbzMaintainTeamDetailsPageParam));
    }

    /**
     * 添加基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加基础信息")
    @CommonLog("添加基础信息")
    @SaCheckPermission("/biz/maintainteamdetails/add")
    @PostMapping("/biz/maintainteamdetails/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzMaintainTeamDetailsAddParam zbbzMaintainTeamDetailsAddParam) {
        zbbzMaintainTeamDetailsService.add(zbbzMaintainTeamDetailsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑基础信息")
    @CommonLog("编辑基础信息")
    @SaCheckPermission("/biz/maintainteamdetails/edit")
    @PostMapping("/biz/maintainteamdetails/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzMaintainTeamDetailsEditParam zbbzMaintainTeamDetailsEditParam) {
        zbbzMaintainTeamDetailsService.edit(zbbzMaintainTeamDetailsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除基础信息")
    @CommonLog("删除基础信息")
    @SaCheckPermission("/biz/maintainteamdetails/delete")
    @PostMapping("/biz/maintainteamdetails/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzMaintainTeamDetailsIdParam> zbbzMaintainTeamDetailsIdParamList) {
        zbbzMaintainTeamDetailsService.delete(zbbzMaintainTeamDetailsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取基础信息详情
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取基础信息详情")
    @SaCheckPermission("/biz/maintainteamdetails/detail")
    @GetMapping("/biz/maintainteamdetails/detail")
    public CommonResult<ZbbzMaintainTeamDetails> detail(@Valid ZbbzMaintainTeamDetailsIdParam zbbzMaintainTeamDetailsIdParam) {
        return CommonResult.data(zbbzMaintainTeamDetailsService.detail(zbbzMaintainTeamDetailsIdParam));
    }
}
