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
package vip.xiaonuo.biz.modular.equbasicsdetails.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基础信息添加参数
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
@Getter
@Setter
public class ZbbzEquBasicsDetailsAddParam {

    /** 装备名称 */
    @ApiModelProperty(value = "装备名称", position = 2)
    private String name;

    /** 型号 */
    @ApiModelProperty(value = "型号", position = 3)
    private String model;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 4)
    private String location;

    /** 剩余寿命 */
    @ApiModelProperty(value = "剩余寿命", position = 5)
    private String residueLifetime;

    /** 使用情况 */
    @ApiModelProperty(value = "使用情况", position = 6)
    private String status;

    /** 入库时间 */
    @ApiModelProperty(value = "入库时间", position = 7)
    private Date exportTime;

    /** 分类id */
    @ApiModelProperty(value = "分类id", position = 10)
    private String categoryId;

}
