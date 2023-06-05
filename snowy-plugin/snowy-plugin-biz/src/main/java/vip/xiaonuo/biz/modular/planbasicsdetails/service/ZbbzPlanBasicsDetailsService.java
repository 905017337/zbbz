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
package vip.xiaonuo.biz.modular.planbasicsdetails.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsAddParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsEditParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsIdParam;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsPageParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * 作战任务Service接口
 *
 * @author czh
 * @date  2023/06/01 12:40
 **/
public interface ZbbzPlanBasicsDetailsService extends IService<ZbbzPlanBasicsDetails> {

    /**
     * 获取作战任务分页
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    Page<ZbbzPlanBasicsDetailsDto> page(ZbbzPlanBasicsDetailsPageParam zbbzPlanBasicsDetailsPageParam);

    /**
     * 添加作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    void add(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam);

    /**
     * 编辑作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    void edit(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) ;

    /**
     * 删除作战任务
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    void delete(List<ZbbzPlanBasicsDetailsIdParam> zbbzPlanBasicsDetailsIdParamList);

    /**
     * 获取作战任务详情
     *
     * @author czh
     * @date  2023/06/01 12:40
     */
    ZbbzPlanBasicsDetails detail(ZbbzPlanBasicsDetailsIdParam zbbzPlanBasicsDetailsIdParam);

    /**
     * 获取作战任务详情
     *
     * @author czh
     * @date  2023/06/01 12:40
     **/
    ZbbzPlanBasicsDetails queryEntity(String id);

    JSONObject importUser(MultipartFile file);



    void downloadImporPlanTemplate(HttpServletResponse response) throws IOException;
}
