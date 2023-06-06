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
package vip.xiaonuo.biz.modular.maintainteamcapacity.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.maintainteamcapacity.entity.ZbbzMaintainTeamCapacity;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityAddParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityEditParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityIdParam;
import vip.xiaonuo.biz.modular.maintainteamcapacity.param.ZbbzMaintainTeamCapacityPageParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 零部件管理Service接口
 *
 * @author czh
 * @date  2023/06/01 12:49
 **/
public interface ZbbzMaintainTeamCapacityService extends IService<ZbbzMaintainTeamCapacity> {

    /**
     * 获取零部件管理分页
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    Page<ZbbzMaintainTeamCapacity> page(ZbbzMaintainTeamCapacityPageParam zbbzMaintainTeamCapacityPageParam);

    /**
     * 添加零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    void add(ZbbzMaintainTeamCapacityAddParam zbbzMaintainTeamCapacityAddParam);

    /**
     * 编辑零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    void edit(ZbbzMaintainTeamCapacityEditParam zbbzMaintainTeamCapacityEditParam);

    /**
     * 删除零部件管理
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    void delete(List<ZbbzMaintainTeamCapacityIdParam> zbbzMaintainTeamCapacityIdParamList);

    /**
     * 获取零部件管理详情
     *
     * @author czh
     * @date  2023/06/01 12:49
     */
    ZbbzMaintainTeamCapacity detail(ZbbzMaintainTeamCapacityIdParam zbbzMaintainTeamCapacityIdParam);

    /**
     * 获取零部件管理详情
     *
     * @author czh
     * @date  2023/06/01 12:49
     **/
    ZbbzMaintainTeamCapacity queryEntity(String id);

    void downloadImporEquTemplate(HttpServletResponse response) throws IOException;

    JSONObject importEqu(MultipartFile file);
}
