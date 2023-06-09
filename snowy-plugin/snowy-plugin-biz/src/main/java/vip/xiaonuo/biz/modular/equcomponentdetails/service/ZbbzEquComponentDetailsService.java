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
package vip.xiaonuo.biz.modular.equcomponentdetails.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equcomponentdetails.dto.ZbbzEquComponentDetailsDto;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.param.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 装备零部件Service接口
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
public interface ZbbzEquComponentDetailsService extends IService<ZbbzEquComponentDetails> {

    /**
     * 获取装备零部件分页
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    Page<ZbbzEquComponentDetails> page(ZbbzEquComponentDetailsPageParam zbbzEquComponentDetailsPageParam);

    /**
     * 添加装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    void add(ZbbzEquComponentDetailsAddParam zbbzEquComponentDetailsAddParam);

    /**
     * 编辑装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    void edit(ZbbzEquComponentDetailsEditParam zbbzEquComponentDetailsEditParam);

    /**
     * 删除装备零部件
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    void delete(List<ZbbzEquComponentDetailsIdParam> zbbzEquComponentDetailsIdParamList);

    /**
     * 获取装备零部件详情
     *
     * @author czh
     * @date  2023/06/01 12:51
     */
    ZbbzEquComponentDetails detail(ZbbzEquComponentDetailsIdParam zbbzEquComponentDetailsIdParam);

    /**
     * 获取装备零部件详情
     *
     * @author czh
     * @date  2023/06/01 12:51
     **/
    ZbbzEquComponentDetails queryEntity(String id);

    List<ZbbzEquComponentDetails> findComponentAll(ZbbzEquComponentDetailsNameParam zbbzEquComponentDetailsIdParam);

    void addComponentForm(ZbbzEquComponentDetailsPlanParam zbbzEquComponentDetailsPlanParam);

    List<ZbbzEquComponentDetailsDto> findComponentByPlanId(String equId);

    JSONObject importEqu(MultipartFile file);

    void downloadImporEquTemplate(HttpServletResponse response) throws IOException;
}
