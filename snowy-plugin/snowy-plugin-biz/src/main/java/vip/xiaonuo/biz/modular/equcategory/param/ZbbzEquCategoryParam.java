package vip.xiaonuo.biz.modular.equcategory.param;

import lombok.Data;

import java.util.Date;

@Data
public class ZbbzEquCategoryParam {
    private String planId;
    private String categoryId;
    private Date planStartDate;
    private Date planEndDate;
    private String status;
}
