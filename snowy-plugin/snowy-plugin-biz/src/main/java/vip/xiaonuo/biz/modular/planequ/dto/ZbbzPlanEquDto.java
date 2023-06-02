package vip.xiaonuo.biz.modular.planequ.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ZbbzPlanEquDto {

    @ApiModelProperty(value = "主键", position = 1)
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

    @ApiModelProperty(value = "装备id",position = 9)
    private String equId;


}
