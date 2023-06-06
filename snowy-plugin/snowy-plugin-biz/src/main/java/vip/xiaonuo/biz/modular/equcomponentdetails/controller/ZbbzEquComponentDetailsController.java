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
package vip.xiaonuo.biz.modular.equcomponentdetails.controller;

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
import vip.xiaonuo.biz.modular.equcomponentdetails.param.*;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.service.ZbbzEquComponentDetailsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;

/**
 * 装备零部件控制器
 *
 * @author czh
 * @date  2023/06/01 12:51
 */
@Api(tags = "装备零部件控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzEquComponentDetailsController {

    @Resource
    private ZbbzEquComponentDetailsService zbbzEquComponentDetailsService;

    /**
     * 获取装备零部件分页
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取装备零部件分页")
    @SaCheckPermission("/biz/equcomponentdetails/page")
    @GetMapping("/biz/equcomponentdetails/page")
    public CommonResult<Page<ZbbzEquComponentDetails>> page(ZbbzEquComponentDetailsPageParam zbbzEquComponentDetailsPageParam) {
        return CommonResult.data(zbbzEquComponentDetailsService.page(zbbzEquComponentDetailsPageParam));
    }

    /**
     * 添加装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加装备零部件")
    @CommonLog("添加装备零部件")
    @SaCheckPermission("/biz/equcomponentdetails/add")
    @PostMapping("/biz/equcomponentdetails/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzEquComponentDetailsAddParam zbbzEquComponentDetailsAddParam) {
        zbbzEquComponentDetailsService.add(zbbzEquComponentDetailsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑装备零部件")
    @CommonLog("编辑装备零部件")
    @SaCheckPermission("/biz/equcomponentdetails/edit")
    @PostMapping("/biz/equcomponentdetails/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzEquComponentDetailsEditParam zbbzEquComponentDetailsEditParam) {
        zbbzEquComponentDetailsService.edit(zbbzEquComponentDetailsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除装备零部件")
    @CommonLog("删除装备零部件")
    @SaCheckPermission("/biz/equcomponentdetails/delete")
    @PostMapping("/biz/equcomponentdetails/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzEquComponentDetailsIdParam> zbbzEquComponentDetailsIdParamList) {
        zbbzEquComponentDetailsService.delete(zbbzEquComponentDetailsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取装备零部件详情
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取装备零部件详情")
    @SaCheckPermission("/biz/equcomponentdetails/detail")
    @GetMapping("/biz/equcomponentdetails/detail")
    public CommonResult<ZbbzEquComponentDetails> detail(@Valid ZbbzEquComponentDetailsIdParam zbbzEquComponentDetailsIdParam) {
        return CommonResult.data(zbbzEquComponentDetailsService.detail(zbbzEquComponentDetailsIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("获取零部件列表")
    @SaCheckPermission("/biz/equcomponentdetails/findComponentAll")
    @GetMapping("/biz/equcomponentdetails/findComponentAll")
    public CommonResult<List<ZbbzEquComponentDetails>> findComponentAll(ZbbzEquComponentDetailsNameParam zbbzEquComponentDetailsIdParam) {
        return CommonResult.data(zbbzEquComponentDetailsService.findComponentAll(zbbzEquComponentDetailsIdParam));
    }
    @ApiOperationSupport(order = 7)
    @ApiOperation("装备页面添加零部件信息")
    @SaCheckPermission("/biz/equcomponentdetails/addComponentForm")
    @PostMapping("/biz/equcomponentdetails/addComponentForm")
    public CommonResult addComponentForm(@RequestBody ZbbzEquComponentDetailsPlanParam zbbzEquComponentDetailsPlanParam) {
        zbbzEquComponentDetailsService.addComponentForm(zbbzEquComponentDetailsPlanParam);
        return CommonResult.ok();
    }
    @ApiOperationSupport(order = 7)
    @ApiOperation("获取装备对应的零部件信息")
    @SaCheckPermission("/biz/equcomponentdetails/findComponentByPlanId")
    @GetMapping("/biz/equcomponentdetails/findComponentByPlanId")
    public CommonResult findComponentByPlanId(@RequestParam(value = "equId") String equId){
        return CommonResult.data(zbbzEquComponentDetailsService.findComponentByPlanId(equId));
    }
    @ApiOperationSupport(order = 8)
    @ApiOperation("下载模版")
    @CommonLog("下载模版")
    @GetMapping(value = "/biz/equcomponentdetails/DownloadImportcomponentTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImporEquTemplate(HttpServletResponse response) throws IOException {
        zbbzEquComponentDetailsService.downloadImporEquTemplate(response);
    }

    @ApiOperationSupport(order = 9)
    @ApiOperation("装备导入")
    @CommonLog("装备导入")
    @PostMapping("/biz/equcomponentdetails/import")
    public CommonResult<JSONObject> importEqu(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(zbbzEquComponentDetailsService.importEqu(file));
    }
}
