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
package vip.xiaonuo.biz.modular.equlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.equlog.entity.ZbbzEquLog;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogAddParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogEditParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogIdParam;
import vip.xiaonuo.biz.modular.equlog.param.ZbbzEquLogPageParam;

import java.util.List;

/**
 * 装备使用日志Service接口
 *
 * @author czh
 * @date  2023/06/03 21:43
 **/
public interface ZbbzEquLogService extends IService<ZbbzEquLog> {

    /**
     * 获取装备使用日志分页
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    Page<ZbbzEquLog> page(ZbbzEquLogPageParam zbbzEquLogPageParam);

    /**
     * 添加装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    void add(ZbbzEquLogAddParam zbbzEquLogAddParam);

    /**
     * 编辑装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    void edit(ZbbzEquLogEditParam zbbzEquLogEditParam);

    /**
     * 删除装备使用日志
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    void delete(List<ZbbzEquLogIdParam> zbbzEquLogIdParamList);

    /**
     * 获取装备使用日志详情
     *
     * @author czh
     * @date  2023/06/03 21:43
     */
    ZbbzEquLog detail(ZbbzEquLogIdParam zbbzEquLogIdParam);

    /**
     * 获取装备使用日志详情
     *
     * @author czh
     * @date  2023/06/03 21:43
     **/
    ZbbzEquLog queryEntity(String id);
}
