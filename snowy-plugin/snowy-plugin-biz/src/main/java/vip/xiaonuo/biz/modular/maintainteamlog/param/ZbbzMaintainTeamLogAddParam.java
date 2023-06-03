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
package vip.xiaonuo.biz.modular.maintainteamlog.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 维修团队日志添加参数
 *
 * @author czh
 * @date  2023/06/03 21:38
 **/
@Getter
@Setter
public class ZbbzMaintainTeamLogAddParam {

    /** 维修团队id */
    @ApiModelProperty(value = "维修团队id", position = 2)
    private String maintainId;

    /** 维修开始时间 */
    @ApiModelProperty(value = "维修开始时间", position = 3)
    private Date startDate;

    /** 维修结束时间 */
    @ApiModelProperty(value = "维修结束时间", position = 4)
    private Date endDate;

    /** 维修地点 */
    @ApiModelProperty(value = "维修地点", position = 5)
    private String location;

    /** 维修装备详情 */
    @ApiModelProperty(value = "维修装备详情", position = 6)
    private String equId;

    /** 零件使用详情 */
    @ApiModelProperty(value = "零件使用详情", position = 7)
    private String eqiComId;

    /** 维修装备所在的任务ID */
    @ApiModelProperty(value = "维修装备所在的任务ID", position = 8)
    private String planId;

    /** 维修团队 */
    @ApiModelProperty(value = "维修团队", position = 9)
    private String maintainTeamName;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 10)
    private String planName;

}
