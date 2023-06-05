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
package vip.xiaonuo.biz.modular.equbasicsdetails.service.impl;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equbasicsdetails.dto.ZbbzEquBasicsDetailsDto;
import vip.xiaonuo.biz.modular.equbasicsdetails.param.*;
import vip.xiaonuo.biz.modular.equcategory.entity.ZbbzEquCategory;
import vip.xiaonuo.biz.modular.equcategory.mapper.ZbbzEquCategoryMapper;
import vip.xiaonuo.biz.modular.equcomponentdetails.dto.EquComponentDetailsEquDto;
import vip.xiaonuo.biz.modular.equcomponentdetails.entity.ZbbzEquComponentDetails;
import vip.xiaonuo.biz.modular.equcomponentdetails.mapper.ZbbzEquComponentDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.ZbbzPlanBasicsDetailsImportParam;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.mapper.ZbbzEquBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.equbasicsdetails.service.ZbbzEquBasicsDetailsService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 基础信息Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
@Service
public class ZbbzEquBasicsDetailsServiceImpl extends ServiceImpl<ZbbzEquBasicsDetailsMapper, ZbbzEquBasicsDetails> implements ZbbzEquBasicsDetailsService {

    @Resource
    ZbbzEquComponentDetailsMapper zbbzEquComponentDetailsMapper;
    @Resource
    ZbbzEquCategoryMapper zbbzEquCategoryMapper;
    @Override
    public Page<ZbbzEquBasicsDetailsDto> page(ZbbzEquBasicsDetailsPageParam zbbzEquBasicsDetailsPageParam) {
        QueryWrapper<ZbbzEquBasicsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzEquBasicsDetails::getName, zbbzEquBasicsDetailsPageParam.getName());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getModel())) {
            queryWrapper.lambda().like(ZbbzEquBasicsDetails::getModel, zbbzEquBasicsDetailsPageParam.getModel());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getResidueLifetime())) {
            queryWrapper.lambda().eq(ZbbzEquBasicsDetails::getResidueLifetime, zbbzEquBasicsDetailsPageParam.getResidueLifetime());
        }
        if(ObjectUtil.isNotEmpty(zbbzEquBasicsDetailsPageParam.getStatus())) {
            queryWrapper.lambda().eq(ZbbzEquBasicsDetails::getStatus, zbbzEquBasicsDetailsPageParam.getStatus());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzEquBasicsDetailsPageParam.getSortField(), zbbzEquBasicsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzEquBasicsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzEquBasicsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzEquBasicsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzEquBasicsDetails::getId);
        }
        Page<ZbbzEquBasicsDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzEquBasicsDetails> detailsList = detailsPage.getRecords();
        Page<ZbbzEquBasicsDetailsDto> result = new Page<>();
        ArrayList<ZbbzEquBasicsDetailsDto> arrayList = new ArrayList<>();
        List<ZbbzEquBasicsDetails> records = detailsPage.setRecords(detailsList).getRecords();
        records.stream().forEach(e->{
            ZbbzEquBasicsDetailsDto dto = new ZbbzEquBasicsDetailsDto();
            QueryWrapper<ZbbzEquComponentDetails> qw = new QueryWrapper<>();
            qw.lambda().eq(ZbbzEquComponentDetails::getEquId,e.getId());
            List<ZbbzEquComponentDetails> selectedList = zbbzEquComponentDetailsMapper.selectList(qw);
            BeanUtil.copyProperties(e,dto);
            List<EquComponentDetailsEquDto> objects = new ArrayList<>();
            List<String> categoryList = new ArrayList<>();
            zhaobaba(e.getCategoryId(),categoryList);
            Collections.reverse(categoryList);
            Object[] listArray = categoryList.toArray();
            dto.setCategoryId(listArray);
            selectedList.forEach(item->{
                EquComponentDetailsEquDto equDto = new EquComponentDetailsEquDto();
                BeanUtil.copyProperties(item,equDto);
                objects.add(equDto);
            });
            dto.setZbbzEquComponentDetailsList(objects);
            arrayList.add(dto);
        });
        BeanUtil.copyProperties(detailsPage,result);
        result.setRecords(arrayList);
        return result;
    }
    void zhaobaba(String id,List<String> categoryList){
        QueryWrapper<ZbbzEquCategory> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZbbzEquCategory::getId,id);
        ZbbzEquCategory selectedOne = zbbzEquCategoryMapper.selectOne(wrapper);
        if(StringUtils.isNotEmpty(selectedOne.getParentId()) ){
            final int length = selectedOne.getId().length();
            if(length == 1){
                categoryList.add(selectedOne.getId());
            }else {
                categoryList.add(selectedOne.getId());
                zhaobaba(selectedOne.getParentId(),categoryList);
            }


        }

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzEquBasicsDetailsAddParam zbbzEquBasicsDetailsAddParam) {
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = BeanUtil.toBean(zbbzEquBasicsDetailsAddParam, ZbbzEquBasicsDetails.class);
        this.save(zbbzEquBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzEquBasicsDetailsEditParam zbbzEquBasicsDetailsEditParam) {
        String[] categoryIdArrys = zbbzEquBasicsDetailsEditParam.getCategoryId();
        String categoryid= categoryIdArrys[categoryIdArrys.length-1];
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = this.queryEntity(zbbzEquBasicsDetailsEditParam.getId());
//        BeanUtil.copyProperties(zbbzEquBasicsDetailsEditParam, zbbzEquBasicsDetails);
        zbbzEquBasicsDetails.setCategoryId(categoryid);
        zbbzEquBasicsDetails.setId(zbbzEquBasicsDetails.getId());
        zbbzEquBasicsDetails.setModel(zbbzEquBasicsDetails.getModel());
        zbbzEquBasicsDetails.setName(zbbzEquBasicsDetails.getName());
        zbbzEquBasicsDetails.setStatus(zbbzEquBasicsDetails.getStatus());
        zbbzEquBasicsDetails.setLocation(zbbzEquBasicsDetails.getLocation());
        zbbzEquBasicsDetails.setExportDate(new Date());
        zbbzEquBasicsDetails.setResidueLifetime(zbbzEquBasicsDetails.getResidueLifetime());
        this.updateById(zbbzEquBasicsDetails);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzEquBasicsDetailsIdParam> zbbzEquBasicsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzEquBasicsDetailsIdParamList, ZbbzEquBasicsDetailsIdParam::getId));
    }

    @Override
    public ZbbzEquBasicsDetails detail(ZbbzEquBasicsDetailsIdParam zbbzEquBasicsDetailsIdParam) {
        return this.queryEntity(zbbzEquBasicsDetailsIdParam.getId());
    }

    @Override
    public ZbbzEquBasicsDetails queryEntity(String id) {
        ZbbzEquBasicsDetails zbbzEquBasicsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzEquBasicsDetails)) {
            throw new CommonException("基础信息不存在，id值为：{}", id);
        }
        return zbbzEquBasicsDetails;
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
    public JSONObject importEqu(MultipartFile file) {
        try {
            int successCount = 0;
            int errorCount = 0;
            JSONArray errorDetail = JSONUtil.createArray();
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(file.getBytes(), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "equImportTemplate.xlsx"));
            // 读取excel
            List<ZbbzEquBasicsDetailsImportParam> equImportParamList =  EasyExcel.read(tempFile).head(ZbbzEquBasicsDetailsImportParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            for (int i = 0; i < equImportParamList.size(); i++) {
                JSONObject jsonObject = this.doImport(equImportParamList.get(i), i);
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

    private JSONObject doImport(ZbbzEquBasicsDetailsImportParam zbbzEquBasicsDetailsImportParam, int i) {
        String planName = zbbzEquBasicsDetailsImportParam.getName();
        String location = zbbzEquBasicsDetailsImportParam.getLocation();
        // 校验必填参数
        if(ObjectUtil.hasEmpty(planName, location)) {
            return JSONUtil.createObj().set("index", i + 1).set("success", false).set("msg", "必填字段存在空值");
        } else {
            try {
                ZbbzEquBasicsDetails details = new ZbbzEquBasicsDetails();
                // 拷贝属性
                BeanUtil.copyProperties(zbbzEquBasicsDetailsImportParam, details);
                details.setExportDate(new Date());
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
