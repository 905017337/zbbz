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
package vip.xiaonuo.biz.modular.planlog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.planlog.entity.ZbbzPlanLog;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogAddParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogEditParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogIdParam;
import vip.xiaonuo.biz.modular.planlog.param.ZbbzPlanLogPageParam;

import java.util.List;

/**
 * 任务修改记录Service接口
 *
 * @author czh
 * @date  2023/06/03 21:29
 **/
public interface ZbbzPlanLogService extends IService<ZbbzPlanLog> {

    /**
     * 获取任务修改记录分页
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    Page<ZbbzPlanLog> page(ZbbzPlanLogPageParam zbbzPlanLogPageParam);

    /**
     * 添加任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    void add(ZbbzPlanLogAddParam zbbzPlanLogAddParam);

    /**
     * 编辑任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    void edit(ZbbzPlanLogEditParam zbbzPlanLogEditParam);

    /**
     * 删除任务修改记录
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    void delete(List<ZbbzPlanLogIdParam> zbbzPlanLogIdParamList);

    /**
     * 获取任务修改记录详情
     *
     * @author czh
     * @date  2023/06/03 21:29
     */
    ZbbzPlanLog detail(ZbbzPlanLogIdParam zbbzPlanLogIdParam);

    /**
     * 获取任务修改记录详情
     *
     * @author czh
     * @date  2023/06/03 21:29
     **/
    ZbbzPlanLog queryEntity(String id);
}
