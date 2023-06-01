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
package vip.xiaonuo.biz.modular.planequ.controller;

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
import vip.xiaonuo.biz.modular.planequ.entity.ZbbzPlanEqu;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquAddParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquEditParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquIdParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquPageParam;
import vip.xiaonuo.biz.modular.planequ.service.ZbbzPlanEquService;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * 任务装备配置控制器
 *
 * @author czh
 * @date  2023/06/01 12:42
 */
@Api(tags = "任务装备配置控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzPlanEquController {

    @Resource
    private ZbbzPlanEquService zbbzPlanEquService;

    /**
     * 获取任务装备配置分页
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取任务装备配置分页")
    @SaCheckPermission("/biz/planequ/page")
    @GetMapping("/biz/planequ/page")
    public CommonResult<Page<ZbbzPlanEqu>> page(ZbbzPlanEquPageParam zbbzPlanEquPageParam) {
        return CommonResult.data(zbbzPlanEquService.page(zbbzPlanEquPageParam));
    }

    /**
     * 添加任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加任务装备配置")
    @CommonLog("添加任务装备配置")
    @SaCheckPermission("/biz/planequ/add")
    @PostMapping("/biz/planequ/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzPlanEquAddParam zbbzPlanEquAddParam) {
        zbbzPlanEquService.add(zbbzPlanEquAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑任务装备配置")
    @CommonLog("编辑任务装备配置")
    @SaCheckPermission("/biz/planequ/edit")
    @PostMapping("/biz/planequ/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzPlanEquEditParam zbbzPlanEquEditParam) {
        zbbzPlanEquService.edit(zbbzPlanEquEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除任务装备配置")
    @CommonLog("删除任务装备配置")
    @SaCheckPermission("/biz/planequ/delete")
    @PostMapping("/biz/planequ/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzPlanEquIdParam> zbbzPlanEquIdParamList) {
        zbbzPlanEquService.delete(zbbzPlanEquIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取任务装备配置详情
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取任务装备配置详情")
    @SaCheckPermission("/biz/planequ/detail")
    @GetMapping("/biz/planequ/detail")
    public CommonResult<ZbbzPlanEqu> detail(@Valid ZbbzPlanEquIdParam zbbzPlanEquIdParam) {
        return CommonResult.data(zbbzPlanEquService.detail(zbbzPlanEquIdParam));
    }
//    @ApiOperationSupport(order = 6)
//    @ApiOperation("更新任务装备配置")
//    @SaCheckPermission("/biz/planequ/updatePlanEqu")
//    @GetMapping("/biz/planequ/updatePlanEqu")
//    public CommonResult updatePlanEqu(){
//        return CommonResult.data(zbbzPlanEquService.updatePlanEqu(zbbzPlanEquIdParam));
//    }
}
