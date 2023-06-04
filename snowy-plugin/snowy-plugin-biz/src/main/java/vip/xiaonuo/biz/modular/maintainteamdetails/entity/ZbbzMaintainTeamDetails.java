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
package vip.xiaonuo.biz.modular.maintainteamdetails.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基础信息实体
 *
 * @author czh
 * @date  2023/06/01 12:43
 **/
@Getter
@Setter
@TableName("zbbz_maintain_team_details")
public class ZbbzMaintainTeamDetails {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 维修团队名 */
    @ApiModelProperty(value = "维修团队名", position = 2)
    private String name;

    /** 经纬度 */
    @ApiModelProperty(value = "经纬度", position = 3)
    private String location;

    /** 维修效率 */
    @ApiModelProperty(value = "维修效率", position = 4)
    private String maintainCapacity;

    /** 移动效率 */
    @ApiModelProperty(value = "移动效率", position = 5)
    private String actionCapacity;

    /** 维修开始时间 */
    @ApiModelProperty(value = "维修开始时间",position = 6)
    private Date maintainStartDate;

    /** 维修结束时间 */
    @ApiModelProperty(value = "维修结束时间",position = 7)
    private Date maintainendDate;

    /** 维修占用状态 */
    @ApiModelProperty(value = "维修占用状态",position = 8)
    private int status;
}
