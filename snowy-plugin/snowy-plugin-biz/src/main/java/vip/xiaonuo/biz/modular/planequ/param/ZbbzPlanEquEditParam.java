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
package vip.xiaonuo.biz.modular.planequ.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 任务装备配置编辑参数
 *
 * @author czh
 * @date  2023/06/01 12:42
 **/
@Getter
@Setter
public class ZbbzPlanEquEditParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 2)
    private String name;

    /** 型号 */
    @ApiModelProperty(value = "型号", position = 3)
    private String model;

    /** 剩余寿命 */
    @ApiModelProperty(value = "剩余寿命", position = 4)
    private String residueLifetime;

    /** 作战开始时间 */
    @ApiModelProperty(value = "作战开始时间", position = 5)
    private Date startTime;

    /** 作战结束时间 */
    @ApiModelProperty(value = "作战结束时间", position = 6)
    private Date endTime;

    /** 重要程度 */
    @ApiModelProperty(value = "重要程度", position = 7)
    private String weight;

    /** 计划id */
    @ApiModelProperty(value = "计划id", position = 8)
    private String planId;

}
