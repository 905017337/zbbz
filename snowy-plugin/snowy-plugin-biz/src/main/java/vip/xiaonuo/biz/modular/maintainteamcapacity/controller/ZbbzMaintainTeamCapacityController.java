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
package vip.xiaonuo.biz.modular.maintainteamcapacity.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.maintainteamcapacity.entity.ZbbzMaintainTeamCapacity;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityAddParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityEditParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityIdParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityPageParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.service.ZbbzMaintainTeamCapacityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * 零部件管理控制器
 *
 * @author czh
 * @date  2023/06/01 12:49
 */
@Api(tags = "零部件管理控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzMaintainTeamCapacityController {

    @Resource
    private ZbbzMaintainTeamCapacityService zbbzMaintainTeamCapacityService;

    /**
     * 获取零部件管理分页
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取零部件管理分页")
    @SaCheckPermission("/biz/maintainteamcapacity/page")
    @GetMapping("/biz/maintainteamcapacity/page")
    public CommonResult<Page<ZbbzMaintainTeamCapacity>> page(ZbbzMaintainTeamCapacityPageParam zbbzMaintainTeamCapacityPageParam) {
        return CommonResult.data(zbbzMaintainTeamCapacityService.page(zbbzMaintainTeamCapacityPageParam));
    }

    /**
     * 添加零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加零部件管理")
    @CommonLog("添加零部件管理")
    @SaCheckPermission("/biz/maintainteamcapacity/add")
    @PostMapping("/biz/maintainteamcapacity/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzMaintainTeamCapacityAddParam zbbzMaintainTeamCapacityAddParam) {
        zbbzMaintainTeamCapacityService.add(zbbzMaintainTeamCapacityAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑零部件管理")
    @CommonLog("编辑零部件管理")
    @SaCheckPermission("/biz/maintainteamcapacity/edit")
    @PostMapping("/biz/maintainteamcapacity/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzMaintainTeamCapacityEditParam zbbzMaintainTeamCapacityEditParam) {
        zbbzMaintainTeamCapacityService.edit(zbbzMaintainTeamCapacityEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除零部件管理")
    @CommonLog("删除零部件管理")
    @SaCheckPermission("/biz/maintainteamcapacity/delete")
    @PostMapping("/biz/maintainteamcapacity/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzMaintainTeamCapacityIdParam> zbbzMaintainTeamCapacityIdParamList) {
        zbbzMaintainTeamCapacityService.delete(zbbzMaintainTeamCapacityIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取零部件管理详情
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取零部件管理详情")
    @SaCheckPermission("/biz/maintainteamcapacity/detail")
    @GetMapping("/biz/maintainteamcapacity/detail")
    public CommonResult<ZbbzMaintainTeamCapacity> detail(@Valid ZbbzMaintainTeamCapacityIdParam zbbzMaintainTeamCapacityIdParam) {
        return CommonResult.data(zbbzMaintainTeamCapacityService.detail(zbbzMaintainTeamCapacityIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("下载模版")
    @CommonLog("下载模版")
    @GetMapping(value = "/biz/maintainteamcapacity/equDownloadImportMaintainTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImporEquTemplate(HttpServletResponse response) throws IOException {
        zbbzMaintainTeamCapacityService.downloadImporEquTemplate(response);
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("装备导入")
    @CommonLog("装备导入")
    @PostMapping("/biz/maintainteamcapacity/import")
    public CommonResult<JSONObject> importEqu(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(zbbzMaintainTeamCapacityService.importEqu(file));
    }
}
