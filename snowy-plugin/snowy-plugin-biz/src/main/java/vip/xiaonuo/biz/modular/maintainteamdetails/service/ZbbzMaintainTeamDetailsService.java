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
package vip.xiaonuo.biz.modular.maintainteamdetails.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import vip.xiaonuo.biz.modular.maintainteamdetails.entity.ZbbzMaintainTeamDetails;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsAddParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsEditParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsIdParam;
import vip.xiaonuo.biz.modular.maintainteamdetails.param.ZbbzMaintainTeamDetailsPageParam;

import java.util.List;

/**
 * 基础信息Service接口
 *
 * @author czh
 * @date  2023/06/01 12:43
 **/
public interface ZbbzMaintainTeamDetailsService extends IService<ZbbzMaintainTeamDetails> {

    /**
     * 获取基础信息分页
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    Page<ZbbzMaintainTeamDetails> page(ZbbzMaintainTeamDetailsPageParam zbbzMaintainTeamDetailsPageParam);

    /**
     * 添加基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    void add(ZbbzMaintainTeamDetailsAddParam zbbzMaintainTeamDetailsAddParam);

    /**
     * 编辑基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    void edit(ZbbzMaintainTeamDetailsEditParam zbbzMaintainTeamDetailsEditParam);

    /**
     * 删除基础信息
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    void delete(List<ZbbzMaintainTeamDetailsIdParam> zbbzMaintainTeamDetailsIdParamList);

    /**
     * 获取基础信息详情
     *
     * @author czh
     * @date  2023/06/01 12:43
     */
    ZbbzMaintainTeamDetails detail(ZbbzMaintainTeamDetailsIdParam zbbzMaintainTeamDetailsIdParam);

    /**
     * 获取基础信息详情
     *
     * @author czh
     * @date  2023/06/01 12:43
     **/
    ZbbzMaintainTeamDetails queryEntity(String id);
}
