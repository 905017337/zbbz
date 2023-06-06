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
package vip.xiaonuo.biz.modular.equcomponentdetails.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.ZbbzEquBasicsDetailsImportParam;
import vip.xiaonuo.biz.modular.equbasicsdetails.service.ZbbzEquBasicsDetailsService;
import vip.xiaonuo.biz.modular.equcategory.entity.ZbbzEquCategory;
import vip.xiaonuo.biz.modular.equcategory.service.ZbbzEquCategoryService;
import vip.xiaonuo.biz.modular.equcomponentdetails.dto.ZbbzEquComponentDetailsDto;
import vip.xiaonuo.biz.modular.equcomponentdetails.param.*;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.mapper.ZbbzEquComponentDetailsMapper;
import vip.xiaonuo.biz.modular.equcomponentdetails.service.ZbbzEquComponentDetailsService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 装备零部件Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
@Service
public class ZbbzEquComponentDetailsServiceImpl extends ServiceImpl<ZbbzEquComponentDetailsMapper, ZbbzEquComponentDetails> implements ZbbzEquComponentDetailsService {

    @Resource
    ZbbzEquComponentDetailsMapper zbbzEquComponentDetailsMapper;
    @Resource
    ZbbzEquBasicsDetailsService zbbzEquBasicsDetailsService;
    @Resource
    ZbbzEquCategoryService zbbzEquCategoryService;
    @Override
    public Page<ZbbzEquComponentDetails> page(ZbbzEquComponentDetailsPageParam zbbzEquComponentDetailsPageParam) {
        QueryWrapper<ZbbzEquComponentDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquComponentDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzEquComponentDetails::getName, zbbzEquComponentDetailsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquComponentDetailsPageParam.getModel())) {
            queryWrapper.lambda().like(ZbbzEquComponentDetails::getModel, zbbzEquComponentDetailsPageParam.getModel());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquComponentDetailsPageParam.getSortField(), zbbzEquComponentDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquComponentDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquComponentDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquComponentDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquComponentDetails::getId);
        }
        Page<ZbbzEquComponentDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzEquComponentDetails> detailsList = detailsPage.getRecords();
        detailsList.stream().forEach(e->{
//            //TODO  枚举待添加
            String residueLifetime = e.getResidueLifetime();
            if("4".equals(residueLifetime)) e.setResidueLifetime("新品");
            if("3".equals(residueLifetime)) e.setResidueLifetime("堪用");
            if("2".equals(residueLifetime)) e.setResidueLifetime("待修");
            if("1".equals(residueLifetime)) e.setResidueLifetime("损毁");
        });
        return detailsPage.setRecords(detailsList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzEquComponentDetailsAddParam zbbzEquComponentDetailsAddParam) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = BeanUtil.toBean(zbbzEquComponentDetailsAddParam, ZbbzEquComponentDetails.class);
        this.save(zbbzEquComponentDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquComponentDetailsEditParam zbbzEquComponentDetailsEditParam) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = this.queryEntity(zbbzEquComponentDetailsEditParam.getId());
        BeanUtil.copyProperties(zbbzEquComponentDetailsEditParam, zbbzEquComponentDetails);
        this.updateById(zbbzEquComponentDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquComponentDetailsIdParam> zbbzEquComponentDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquComponentDetailsIdParamList, ZbbzEquComponentDetailsIdParam::getId));
    }

    @Override
    public ZbbzEquComponentDetails detail(ZbbzEquComponentDetailsIdParam zbbzEquComponentDetailsIdParam) {
        return this.queryEntity(zbbzEquComponentDetailsIdParam.getId());
    }

    @Override
    public ZbbzEquComponentDetails queryEntity(String id) {
        ZbbzEquComponentDetails zbbzEquComponentDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquComponentDetails)) {
            throw new CommonException("装备零部件不存在，id值为：{}", id);
        }
        return zbbzEquComponentDetails;
    }

    @Override
    public List<ZbbzEquComponentDetails> findComponentAll(ZbbzEquComponentDetailsNameParam zbbzEquComponentDetailsIdParam) {
        QueryWrapper<ZbbzEquComponentDetails> wrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(zbbzEquComponentDetailsIdParam.getName())){
            wrapper.lambda().eq(ZbbzEquComponentDetails::getName,zbbzEquComponentDetailsIdParam.getName());
        }
        return zbbzEquComponentDetailsMapper.selectList(wrapper);
    }

    @Override
    public void addComponentForm(ZbbzEquComponentDetailsPlanParam zbbzEquComponentDetailsPlanParam) {
        ZbbzEquComponentDetails details = new ZbbzEquComponentDetails();
        details.setName(zbbzEquComponentDetailsPlanParam.getName());
        details.setModel(zbbzEquComponentDetailsPlanParam.getModel());
        details.setEquDesc(zbbzEquComponentDetailsPlanParam.getEquDesc());
        details.setEquId(zbbzEquComponentDetailsPlanParam.getEquId());
        //TODO 默认新配件为5 后续添加枚举
        details.setResidueLifetime("4");
        details.setIId(zbbzEquComponentDetailsPlanParam.getIId());
        zbbzEquComponentDetailsMapper.insert(details);
    }

    @Override
    public List<ZbbzEquComponentDetailsDto> findComponentByPlanId(String equId) {
        QueryWrapper<ZbbzEquComponentDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ZbbzEquComponentDetails::getEquId,equId);
        ArrayList<ZbbzEquComponentDetailsDto> list = new ArrayList<>();
        zbbzEquComponentDetailsMapper.selectList(queryWrapper).forEach(e->{
            final ZbbzEquComponentDetailsDto dto = new ZbbzEquComponentDetailsDto();
            dto.setEquId(e.getEquId());
            dto.setModel(e.getModel());
            dto.setEquDesc(e.getEquDesc());
            dto.setIId(e.getIId());
            dto.setName(e.getName());
            //TODO 添加枚举
            if(StringUtils.isNotEmpty(e.getResidueLifetime())&&"4".equals(e.getResidueLifetime())){
                dto.setResidueLifetime("新品");
            }
            if(StringUtils.isNotEmpty(e.getResidueLifetime())&&"3".equals(e.getResidueLifetime())){
                dto.setResidueLifetime("勘用");
            }
            if(StringUtils.isNotEmpty(e.getResidueLifetime())&&"2".equals(e.getResidueLifetime())){
                dto.setResidueLifetime("待修");
            }
            if(StringUtils.isNotEmpty(e.getResidueLifetime())&&"1".equals(e.getResidueLifetime())){
                dto.setResidueLifetime("报废");
            }

            list.add(dto);
        });
        return list;
    }

    @Override
    public void downloadImporEquTemplate(HttpServletResponse response) throws IOException {
        try {
            InputStream inputStream = POICacheManager.getFile("equExportTemplate.xlsx");
            byte[] bytes = IoUtil.readBytes(inputStream);
            CommonDownloadUtil.download("任务导入模板.xlsx", bytes, response);
        } catch (Exception e) {
            log.error(">>> 下载用户导入模板失败：", e);
            CommonResponseUtil.renderError(response, "下载用户导入模板失败");
        }
    }

    @Override
    @Transactional
    public JSONObject importEqu(MultipartFile file) {
        try {
            List<ZbbzEquBasicsDetails> detailsList = zbbzEquBasicsDetailsService.list();
            List<ZbbzEquCategory> categoryList = zbbzEquCategoryService.list();
            int successCount = 0;
            int errorCount = 0;
            JSONArray errorDetail = JSONUtil.createArray();
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(file.getBytes(), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "equImportTemplate.xlsx"));
            // 读取excel
            List<ZbbzEquComponentDetailsImportParam> equImportParamList =  EasyExcel.read(tempFile).head(ZbbzEquComponentDetailsImportParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            for (int i = 0; i < equImportParamList.size(); i++) {
                JSONObject jsonObject = this.doImport(equImportParamList.get(i), i,detailsList,categoryList);
                if(jsonObject.getBool("success")) {
                    successCount += 1;
                } else {
                    errorCount += 1;
                    errorDetail.add(jsonObject);
                }
            }
            return JSONUtil.createObj()
                    .set("totalCount", equImportParamList.size())
                    .set("successCount", successCount)
                    .set("errorCount", errorCount)
                    .set("errorDetail", errorDetail);
        } catch (Exception e) {
            log.error(">>> 任务导入失败：", e);
            throw new CommonException("任务导入失败");
        }
    }

    private JSONObject doImport(ZbbzEquComponentDetailsImportParam zbbzEquBasicsDetailsImportParam, int i,
                                List<ZbbzEquBasicsDetails> detailsList,List<ZbbzEquCategory> categoryList) {
        String componentName = zbbzEquBasicsDetailsImportParam.getName();
        String model = zbbzEquBasicsDetailsImportParam.getModel();
        String residueLifetime = zbbzEquBasicsDetailsImportParam.getResidueLifetime();
        String equName = zbbzEquBasicsDetailsImportParam.getEquName();
        ZbbzEquBasicsDetails basicsDetails = detailsList.stream().filter(e -> e.getName().equals(equName)).findFirst().get();
        ZbbzEquCategory category = new ZbbzEquCategory();
        if(StringUtils.isNotEmpty(zbbzEquBasicsDetailsImportParam.getCategoryName())){
            category = categoryList.stream().filter(e -> e.getName().equals(zbbzEquBasicsDetailsImportParam.getCategoryName())).findFirst().get();
        }
        // 校验必填参数
        if(ObjectUtil.hasEmpty(componentName, model,residueLifetime,equName)) {
            return JSONUtil.createObj().set("index", i + 1).set("success", false).set("msg", "必填字段存在空值");
        } else {
            try {
                ZbbzEquComponentDetails details = new ZbbzEquComponentDetails();
                BeanUtil.copyProperties(zbbzEquBasicsDetailsImportParam,details);
                if("新品".equals(zbbzEquBasicsDetailsImportParam.getResidueLifetime())){
                    details.setResidueLifetime("4");
                }
                if("滥用".equals(zbbzEquBasicsDetailsImportParam.getResidueLifetime())){
                    details.setResidueLifetime("3");
                }
                if("待修".equals(zbbzEquBasicsDetailsImportParam.getResidueLifetime())){
                    details.setResidueLifetime("2");
                }
                if("报废".equals(zbbzEquBasicsDetailsImportParam.getResidueLifetime())){
                    details.setResidueLifetime("1");
                }
                details.setName(componentName);
                details.setModel(model);
                details.setIId(zbbzEquBasicsDetailsImportParam.getIId());
                details.setCategoryId(zbbzEquBasicsDetailsImportParam.getCategoryName());
                details.setEquId(basicsDetails.getId());
                if(null !=category){
                    details.setCategoryId(category.getId());
                }
                details.setEquDesc(zbbzEquBasicsDetailsImportParam.getEquDesc());
                // 保存或更新
                this.saveOrUpdate(details);
                // 返回成功
                return JSONUtil.createObj().set("success", true);
            } catch (Exception e) {
                log.error(">>> 数据导入异常：", e);
                return JSONUtil.createObj().set("success", false).set("index", i + 1).set("msg", "数据导入异常");
            }
        }
    }
}
