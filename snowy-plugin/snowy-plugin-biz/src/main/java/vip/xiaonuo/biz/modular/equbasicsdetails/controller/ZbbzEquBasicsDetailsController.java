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
package vip.xiaonuo.biz.modular.equbasicsdetails.controller;

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
import vip.xiaonuo.biz.modular.equbasicsdetails.dto.ZbbzEquBasicsDetailsDto;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsAddParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsEditParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsIdParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsPageParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.service.ZbbzEquBasicsDetailsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * 基础信息控制器
 *
 * @author czh
 * @date  2023/06/01 12:51
 */
@Api(tags = "基础信息控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzEquBasicsDetailsController {

    @Resource
    private ZbbzEquBasicsDetailsService zbbzEquBasicsDetailsService;

    /**
     * 获取基础信息分页
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取基础信息分页")
    @SaCheckPermission("/biz/equbasicsdetails/page")
    @GetMapping("/biz/equbasicsdetails/page")
    public CommonResult<Page<ZbbzEquBasicsDetailsDto>> page(ZbbzEquBasicsDetailsPageParam zbbzEquBasicsDetailsPageParam) {
        return CommonResult.data(zbbzEquBasicsDetailsService.page(zbbzEquBasicsDetailsPageParam));
    }

    /**
     * 添加基础信息
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加基础信息")
    @CommonLog("添加基础信息")
    @SaCheckPermission("/biz/equbasicsdetails/add")
    @PostMapping("/biz/equbasicsdetails/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzEquBasicsDetailsAddParam zbbzEquBasicsDetailsAddParam) {
        zbbzEquBasicsDetailsService.add(zbbzEquBasicsDetailsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑基础信息
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑基础信息")
    @CommonLog("编辑基础信息")
    @SaCheckPermission("/biz/equbasicsdetails/edit")
    @PostMapping("/biz/equbasicsdetails/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzEquBasicsDetailsEditParam zbbzEquBasicsDetailsEditParam) {
        zbbzEquBasicsDetailsService.edit(zbbzEquBasicsDetailsEditParam);
        return CommonResult.ok();
    }

    /**
     * 删除基础信息
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除基础信息")
    @CommonLog("删除基础信息")
    @SaCheckPermission("/biz/equbasicsdetails/delete")
    @PostMapping("/biz/equbasicsdetails/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzEquBasicsDetailsIdParam> zbbzEquBasicsDetailsIdParamList) {
        zbbzEquBasicsDetailsService.delete(zbbzEquBasicsDetailsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取基础信息详情
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取基础信息详情")
    @SaCheckPermission("/biz/equbasicsdetails/detail")
    @GetMapping("/biz/equbasicsdetails/detail")
    public CommonResult<ZbbzEquBasicsDetails> detail(@Valid ZbbzEquBasicsDetailsIdParam zbbzEquBasicsDetailsIdParam) {
        return CommonResult.data(zbbzEquBasicsDetailsService.detail(zbbzEquBasicsDetailsIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("下载模版")
    @CommonLog("下载模版")
    @GetMapping(value = "/biz/equbasicsdetails/equDownloadImportplanTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImporEquTemplate(HttpServletResponse response) throws IOException {
        zbbzEquBasicsDetailsService.downloadImporEquTemplate(response);
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("装备导入")
    @CommonLog("装备导入")
    @PostMapping("/biz/equbasicsdetails/import")
    public CommonResult<JSONObject> importEqu(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(zbbzEquBasicsDetailsService.importEqu(file));
    }
}
