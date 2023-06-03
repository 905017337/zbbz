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
package vip.xiaonuo.biz.modular.maintainplanlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.maintainplanlog.entity.ZbbzMaintainPlanLog;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogAddParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogEditParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogIdParam;
import vip.xiaonuo.biz.modular.maintainplanlog.param.ZbbzMaintainPlanLogPageParam;

import java.util.List;

/**
 *  维护作战任务日志Service接口
 *
 * @author czh
 * @date  2023/06/03 21:40
 **/
public interface ZbbzMaintainPlanLogService extends IService<ZbbzMaintainPlanLog> {

    /**
     * 获取 维护作战任务日志分页
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    Page<ZbbzMaintainPlanLog> page(ZbbzMaintainPlanLogPageParam zbbzMaintainPlanLogPageParam);

    /**
     * 添加 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    void add(ZbbzMaintainPlanLogAddParam zbbzMaintainPlanLogAddParam);

    /**
     * 编辑 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    void edit(ZbbzMaintainPlanLogEditParam zbbzMaintainPlanLogEditParam);

    /**
     * 删除 维护作战任务日志
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    void delete(List<ZbbzMaintainPlanLogIdParam> zbbzMaintainPlanLogIdParamList);

    /**
     * 获取 维护作战任务日志详情
     *
     * @author czh
     * @date  2023/06/03 21:40
     */
    ZbbzMaintainPlanLog detail(ZbbzMaintainPlanLogIdParam zbbzMaintainPlanLogIdParam);

    /**
     * 获取 维护作战任务日志详情
     *
     * @author czh
     * @date  2023/06/03 21:40
     **/
    ZbbzMaintainPlanLog queryEntity(String id);
}
