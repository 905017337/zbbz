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
package vip.xiaonuo.biz.modular.planequ.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.planequ.entity.ZbbzPlanEqu;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquAddParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquEditParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquIdParam;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquPageParam;

import java.util.List;

/**
 * 任务装备配置Service接口
 *
 * @author czh
 * @date  2023/06/01 12:42
 **/
public interface ZbbzPlanEquService extends IService<ZbbzPlanEqu> {

    /**
     * 获取任务装备配置分页
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    Page<ZbbzPlanEqu> page(ZbbzPlanEquPageParam zbbzPlanEquPageParam);

    /**
     * 添加任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    void add(ZbbzPlanEquAddParam zbbzPlanEquAddParam);

    /**
     * 编辑任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    void edit(ZbbzPlanEquEditParam zbbzPlanEquEditParam);

    /**
     * 删除任务装备配置
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    void delete(List<ZbbzPlanEquIdParam> zbbzPlanEquIdParamList);

    /**
     * 获取任务装备配置详情
     *
     * @author czh
     * @date  2023/06/01 12:42
     */
    ZbbzPlanEqu detail(ZbbzPlanEquIdParam zbbzPlanEquIdParam);

    /**
     * 获取任务装备配置详情
     *
     * @author czh
     * @date  2023/06/01 12:42
     **/
    ZbbzPlanEqu queryEntity(String id);
}
