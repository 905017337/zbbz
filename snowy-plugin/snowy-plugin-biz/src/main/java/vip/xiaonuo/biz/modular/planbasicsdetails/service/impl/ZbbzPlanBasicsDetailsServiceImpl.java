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
package vip.xiaonuo.biz.modular.planbasicsdetails.service.impl;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vip.xiaonuo.biz.modular.equbasicsdetails.entity.ZbbzEquBasicsDetails;
import vip.xiaonuo.biz.modular.equbasicsdetails.mapper.ZbbzEquBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.dto.ZbbzPlanBasicsDetailsDto;
import vip.xiaonuo.biz.modular.planbasicsdetails.param.*;
import vip.xiaonuo.biz.modular.planequ.dto.ZbbzPlanEquDto;
import vip.xiaonuo.biz.modular.planequ.entity.ZbbzPlanEqu;
import vip.xiaonuo.biz.modular.planequ.mapper.ZbbzPlanEquMapper;
import vip.xiaonuo.biz.modular.planequ.param.ZbbzPlanEquAddParam;
import vip.xiaonuo.biz.modular.planlog.entity.ZbbzPlanLog;
import vip.xiaonuo.biz.modular.planlog.service.ZbbzPlanLogService;
import vip.xiaonuo.common.enums.CommonSortOrderEnum;
import vip.xiaonuo.common.exception.CommonException;
import vip.xiaonuo.common.listener.CommonDataChangeEventCenter;
import vip.xiaonuo.common.page.CommonPageRequest;
import vip.xiaonuo.biz.modular.planbasicsdetails.entity.ZbbzPlanBasicsDetails;
import vip.xiaonuo.biz.modular.planbasicsdetails.mapper.ZbbzPlanBasicsDetailsMapper;
import vip.xiaonuo.biz.modular.planbasicsdetails.service.ZbbzPlanBasicsDetailsService;
import vip.xiaonuo.common.util.CommonDownloadUtil;
import vip.xiaonuo.common.util.CommonResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作战任务Service接口实现类
 *
 * @author czh
 * @date  2023/06/01 12:40
 **/
@Service
public class ZbbzPlanBasicsDetailsServiceImpl extends ServiceImpl<ZbbzPlanBasicsDetailsMapper, ZbbzPlanBasicsDetails> implements ZbbzPlanBasicsDetailsService {

    @Resource
    ZbbzPlanEquMapper zbbzPlanEquMapper;
    @Resource
    ZbbzEquBasicsDetailsMapper zbbzEquBasicsDetailsMapper;
    @Resource
    ZbbzPlanLogService zbbzPlanLogService;
    @Override
    public Page<ZbbzPlanBasicsDetailsDto> page(ZbbzPlanBasicsDetailsPageParam zbbzPlanBasicsDetailsPageParam) {
        QueryWrapper<ZbbzPlanBasicsDetails> queryWrapper = new QueryWrapper<>();
        if(ObjectUtil.isNotEmpty(zbbzPlanBasicsDetailsPageParam.getName())) {
            queryWrapper.lambda().like(ZbbzPlanBasicsDetails::getName, zbbzPlanBasicsDetailsPageParam.getName());
        }
        if(ObjectUtil.isAllNotEmpty(zbbzPlanBasicsDetailsPageParam.getSortField(), zbbzPlanBasicsDetailsPageParam.getSortOrder())) {
            CommonSortOrderEnum.validate(zbbzPlanBasicsDetailsPageParam.getSortOrder());
            queryWrapper.orderBy(true, zbbzPlanBasicsDetailsPageParam.getSortOrder().equals(CommonSortOrderEnum.ASC.getValue()),
                    StrUtil.toUnderlineCase(zbbzPlanBasicsDetailsPageParam.getSortField()));
        } else {
            queryWrapper.lambda().orderByAsc(ZbbzPlanBasicsDetails::getId);
        }
        Page<ZbbzPlanBasicsDetails> detailsPage = this.page(CommonPageRequest.defaultPage(), queryWrapper);
        List<ZbbzPlanBasicsDetails> records = detailsPage.getRecords();
        Page<ZbbzPlanBasicsDetailsDto> result = new Page<>();
        //任务选择的装备
        List<ZbbzPlanBasicsDetailsDto> arrayList1 = new ArrayList<>();
        //渲染装备分类树
        //TODO 后续拆分 获取单条数据的时候查询对应的选择项目
        records.stream().forEach(e->{
            Set<String> treeSelect = new HashSet<>();
            ZbbzPlanBasicsDetailsDto planDto = new ZbbzPlanBasicsDetailsDto();
            BeanUtil.copyProperties(e,planDto);
            QueryWrapper<ZbbzPlanEqu> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(ZbbzPlanEqu::getPlanId,e.getId());
            List<ZbbzPlanEqu> equList = zbbzPlanEquMapper.selectList(wrapper);
            ArrayList<ZbbzPlanEquDto> arrayList = new ArrayList<>();
            equList.stream().forEach(item->{
                QueryWrapper<ZbbzEquBasicsDetails> equBasicsDetailsQueryWrapper = new QueryWrapper<>();
                equBasicsDetailsQueryWrapper.lambda().eq(ZbbzEquBasicsDetails::getId,item.getEquId());
                ZbbzEquBasicsDetails selectedOne = zbbzEquBasicsDetailsMapper.selectOne(equBasicsDetailsQueryWrapper);
                treeSelect.add(selectedOne.getCategoryId());
                ZbbzPlanEquDto dto = new ZbbzPlanEquDto();
                BeanUtil.copyProperties(item,dto);
                arrayList.add(dto);
            });
            planDto.setZbbzEquBasicsDetailsParamList(arrayList);
            String[] arr = treeSelect.toArray(new String[treeSelect.size()]);
            planDto.setTreeSelect(arr);
            arrayList1.add(planDto);
        });
        BeanUtil.copyProperties(detailsPage,result);
        result.setRecords(arrayList1);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = BeanUtil.toBean(zbbzPlanBasicsDetailsAddParam, ZbbzPlanBasicsDetails.class);
        this.save(zbbzPlanBasicsDetails);
        zbbzPlanBasicsDetailsAddParam.setId(zbbzPlanBasicsDetails.getId());
        updatePlanEquByPlanId(zbbzPlanBasicsDetailsAddParam);
        //保存日志
//        savePlanLog(zbbzPlanBasicsDetailsAddParam,"add");
        //TODO 修改装备库中装备的占用
    }
    void savePlanLog(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam,String operation){
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = BeanUtil.toBean(zbbzPlanBasicsDetailsAddParam, ZbbzPlanBasicsDetails.class);
        ZbbzPlanLog log = new ZbbzPlanLog();
        log.setPlanId(zbbzPlanBasicsDetails.getId());
        log.setLocation(zbbzPlanBasicsDetails.getLocation());
        log.setStartDate(zbbzPlanBasicsDetails.getStartDate());
        log.setEndDate(zbbzPlanBasicsDetails.getEndDate());
        log.setPlanName(zbbzPlanBasicsDetails.getName());
        final List<ZbbzPlanEquAddParam> detailsParamList = zbbzPlanBasicsDetailsAddParam.getZbbzPlanEquAddParamList();
        if(!detailsParamList.isEmpty()){
            final List<String> collect = detailsParamList.stream().map(ZbbzPlanEquAddParam::getId)
                    .collect(Collectors.toList());
            final String ids = JSON.toJSONString(collect);
            log.setEquIds(ids);
        }
        log.setOperation(operation);
        zbbzPlanLogService.save(log);
    }
    public void updatePlanEquByPlanId(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        zbbzPlanBasicsDetailsAddParam.getZbbzPlanEquAddParamList().stream().forEach(e->{
            ZbbzPlanEqu planEqu = new ZbbzPlanEqu();
            planEqu.setPlanId(zbbzPlanBasicsDetailsAddParam.getId());
            planEqu.setName(e.getName());
            planEqu.setModel(e.getModel());
            planEqu.setStartDate(e.getStartDate());
            planEqu.setEndDate(e.getEndDate());
            planEqu.setEquId(e.getId());
            zbbzPlanEquMapper.insert(planEqu);
        });
        //修改日志
        savePlanLog(zbbzPlanBasicsDetailsAddParam,"edit");
        //TODO 修改装备库中装备的占用
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(ZbbzPlanBasicsDetailsAddParam zbbzPlanBasicsDetailsAddParam) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = this.queryEntity(zbbzPlanBasicsDetailsAddParam.getId());
        BeanUtil.copyProperties(zbbzPlanBasicsDetailsAddParam, zbbzPlanBasicsDetails);
        zbbzPlanBasicsDetails.setStartDate(zbbzPlanBasicsDetailsAddParam.getStartDate());  //开始时间
        zbbzPlanBasicsDetails.setEndDate(zbbzPlanBasicsDetailsAddParam.getEndDate());   //结束时间
        QueryWrapper<ZbbzPlanEqu> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ZbbzPlanEqu::getPlanId,zbbzPlanBasicsDetailsAddParam.getId());
        zbbzPlanEquMapper.delete(queryWrapper);              //删除之前存在的装备
        updatePlanEquByPlanId(zbbzPlanBasicsDetailsAddParam);  //添加修改后的装备
        this.updateById(zbbzPlanBasicsDetails);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<ZbbzPlanBasicsDetailsIdParam> zbbzPlanBasicsDetailsIdParamList) {
        // 执行删除
        this.removeByIds(CollStreamUtil.toList(zbbzPlanBasicsDetailsIdParamList, ZbbzPlanBasicsDetailsIdParam::getId));
        //删除任务装备详情
        zbbzPlanBasicsDetailsIdParamList.forEach(e->{
            QueryWrapper<ZbbzPlanEqu> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ZbbzPlanEqu::getPlanId,e.getId());
            zbbzPlanEquMapper.delete(queryWrapper);
        });
        //TODO 接触装备库中装备的占用
    }

    @Override
    public ZbbzPlanBasicsDetails detail(ZbbzPlanBasicsDetailsIdParam zbbzPlanBasicsDetailsIdParam) {
        return this.queryEntity(zbbzPlanBasicsDetailsIdParam.getId());
    }

    @Override
    public ZbbzPlanBasicsDetails queryEntity(String id) {
        ZbbzPlanBasicsDetails zbbzPlanBasicsDetails = this.getById(id);
        if(ObjectUtil.isEmpty(zbbzPlanBasicsDetails)) {
            throw new CommonException("作战任务不存在，id值为：{}", id);
        }
        QueryWrapper<ZbbzPlanEqu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(ZbbzPlanEqu::getPlanId,zbbzPlanBasicsDetails.getId());
        ZbbzPlanBasicsDetailsDto detailsDto = new ZbbzPlanBasicsDetailsDto();
        BeanUtil.copyProperties(zbbzPlanBasicsDetails,detailsDto);
        ArrayList<ZbbzPlanEquDto> list = new ArrayList<>();
        zbbzPlanEquMapper.selectList(wrapper).stream().forEach(e->{
            ZbbzPlanEquDto dto = new ZbbzPlanEquDto();
            BeanUtil.copyProperties(e,dto);
            list.add(dto);
        });
        detailsDto.setZbbzEquBasicsDetailsParamList(list);
        return zbbzPlanBasicsDetails;
    }

    @Override
    public JSONObject importUser(MultipartFile file) {
        try {
            int successCount = 0;
            int errorCount = 0;
            JSONArray errorDetail = JSONUtil.createArray();
            // 创建临时文件
            File tempFile = FileUtil.writeBytes(file.getBytes(), FileUtil.file(FileUtil.getTmpDir() +
                    FileUtil.FILE_SEPARATOR + "planImportTemplate.xlsx"));
            // 读取excel
            List<ZbbzPlanBasicsDetailsImportParam> planImportParamList =  EasyExcel.read(tempFile).head(ZbbzPlanBasicsDetailsImportParam.class).sheet()
                    .headRowNumber(2).doReadSync();
            for (int i = 0; i < planImportParamList.size(); i++) {
                JSONObject jsonObject = this.doImport(planImportParamList.get(i), i);
                if(jsonObject.getBool("success")) {
                    successCount += 1;
                } else {
                    errorCount += 1;
                    errorDetail.add(jsonObject);
                }
            }
            return JSONUtil.createObj()
                    .set("totalCount", planImportParamList.size())
                    .set("successCount", successCount)
                    .set("errorCount", errorCount)
                    .set("errorDetail", errorDetail);
        } catch (Exception e) {
            log.error(">>> 任务导入失败：", e);
            throw new CommonException("任务导入失败");
        }
    }

    private JSONObject doImport(ZbbzPlanBasicsDetailsImportParam zbbzPlanBasicsDetailsImportParam, int i) {
        String planName = zbbzPlanBasicsDetailsImportParam.getName();
        String location = zbbzPlanBasicsDetailsImportParam.getLocation();
        // 校验必填参数
        if(ObjectUtil.hasEmpty(planName, location)) {
            return JSONUtil.createObj().set("index", i + 1).set("success", false).set("msg", "必填字段存在空值");
        } else {
            try {

                ZbbzPlanBasicsDetails details = new ZbbzPlanBasicsDetails();
                // 拷贝属性
                BeanUtil.copyProperties(zbbzPlanBasicsDetailsImportParam, details);
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



    @Override
    public void downloadImporPlanTemplate(HttpServletResponse response) throws IOException {
        try {
            InputStream inputStream = POICacheManager.getFile("planImportTemplate.xlsx");
            byte[] bytes = IoUtil.readBytes(inputStream);
            CommonDownloadUtil.download("任务导入模板.xlsx", bytes, response);
        } catch (Exception e) {
            log.error(">>> 下载装备导入模板失败：", e);
            CommonResponseUtil.renderError(response, "下载用户导入模板失败");
        }
    }
}
