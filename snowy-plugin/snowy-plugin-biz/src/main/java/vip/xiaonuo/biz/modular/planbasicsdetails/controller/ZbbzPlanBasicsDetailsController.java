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
package vip.xiaonuo.biz.modular.planbasicsdetails.controller;

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
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.common.annotation.CommonLog;
import vip.xiaonuo.common.pojo.CommonResult;
import vip.xiaonuo.common.pojo.CommonValidList;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsAddParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsIdParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsPageParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.service.ZbbzPlanBasicsDetailsService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * 作战任务控制器
 *
 * @author czh
 * @date  2023/06/01 12:40
 */
@Api(tags = "作战任务控制器")
@ApiSupport(author = "SNOWY_TEAM", order = 1)
@RestController
@Validated
public class ZbbzPlanBasicsDetailsController {

    @Resource
    private ZbbzPlanBasicsDetailsService zbbzPlanBasicsDetailsService;

    /**
     * 获取作战任务分页
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    @ApiOperationSupport(order = 1)
    @ApiOperation("获取作战任务分页")
    @SaCheckPermission("/biz/planbasicsdetails/page")
    @GetMapping("/biz/planbasicsdetails/page")
    public CommonResult<Page<ZbbzPlanBasicsDetailsDto>> page(ZbbzPlanBasicsDetailsPageParam zbbzPlanBasicsDetailsPageParam) {
        return CommonResult.data(zbbzPlanBasicsDetailsService.page(zbbzPlanBasicsDetailsPageParam));
    }

    /**
     * 添加作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    @ApiOperationSupport(order = 2)
    @ApiOperation("添加作战任务")
    @CommonLog("添加作战任务")
    @SaCheckPermission("/biz/planbasicsdetails/add")
    @PostMapping("/biz/planbasicsdetails/add")
    public CommonResult<String> add(@RequestBody @Valid ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        zbbzPlanBasicsDetailsService.add(zbbzPlanBasicsDetailsAddParam);
        return CommonResult.ok();
    }

    /**
     * 编辑作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    @ApiOperationSupport(order = 3)
    @ApiOperation("编辑作战任务")
    @CommonLog("编辑作战任务")
    @SaCheckPermission("/biz/planbasicsdetails/edit")
    @PostMapping("/biz/planbasicsdetails/edit")
    public CommonResult<String> edit(@RequestBody @Valid ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        zbbzPlanBasicsDetailsService.edit(zbbzPlanBasicsDetailsAddParam);
        return CommonResult.ok();
    }

    /**
     * 删除作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    @ApiOperationSupport(order = 4)
    @ApiOperation("删除作战任务")
    @CommonLog("删除作战任务")
    @SaCheckPermission("/biz/planbasicsdetails/delete")
    @PostMapping("/biz/planbasicsdetails/delete")
    public CommonResult<String> delete(@RequestBody @Valid @NotEmpty(message = "集合不能为空")
                                                   CommonValidList<ZbbzPlanBasicsDetailsIdParam> zbbzPlanBasicsDetailsIdParamList) {
        zbbzPlanBasicsDetailsService.delete(zbbzPlanBasicsDetailsIdParamList);
        return CommonResult.ok();
    }

    /**
     * 获取作战任务详情
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    @ApiOperationSupport(order = 5)
    @ApiOperation("获取作战任务详情")
    @SaCheckPermission("/biz/planbasicsdetails/detail")
    @GetMapping("/biz/planbasicsdetails/detail")
    public CommonResult<ZbbzPlanBasicsDetails> detail(@Valid ZbbzPlanBasicsDetailsIdParam zbbzPlanBasicsDetailsIdParam) {
        return CommonResult.data(zbbzPlanBasicsDetailsService.detail(zbbzPlanBasicsDetailsIdParam));
    }

    @ApiOperationSupport(order = 6)
    @ApiOperation("任务导入")
    @CommonLog("任务导入")
    @PostMapping("/biz/planbasicsdetails/import")
    public CommonResult<JSONObject> importUser(@RequestPart("file") @ApiParam(value="文件", required = true) MultipartFile file) {
        return CommonResult.data(zbbzPlanBasicsDetailsService.importUser(file));
    }



    @ApiOperationSupport(order = 7)
    @ApiOperation("下载模版")
    @CommonLog("下载模版")
    @GetMapping(value = "/biz/planbasicsdetails/planDownloadImportplanTemplate", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void downloadImporPlanTemplate(HttpServletResponse response) throws IOException {
        zbbzPlanBasicsDetailsService.downloadImporPlanTemplate(response);
    }


}
