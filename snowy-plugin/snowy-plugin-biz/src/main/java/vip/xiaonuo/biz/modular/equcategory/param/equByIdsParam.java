package vip.xiaonuo.biz.modular.equcategory.param;

import lombok.Data;

import java.util.Date;

/**
 * @author caozhenhao
 * @version 1.0
 * @date 2023/6/1 23:31
 */
@Data
public class equByIdsParam {

    private String[] ids;

    private Date startDate;

    private Date  endDate;

    private String planId;
}