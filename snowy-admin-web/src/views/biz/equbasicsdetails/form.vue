<template>
	<xn-form-container
		:title="formData.id ? '编辑基础信息' : '增加基础信息'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
	<a-tabs>
			<a-tab-pane key="1" tab="基础信息">
				<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-row :gutter="16">
				<a-col :span="12">
					<a-form-item label="装备名称：" name="name">
						<a-input v-model:value="formData.name" placeholder="请输入装备名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="型号：" name="model">
						<a-input v-model:value="formData.model" placeholder="请输入型号" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="经纬度：" name="location">
						<a-input v-model:value="formData.location" placeholder="请输入经纬度" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="剩余寿命：" name="residueLifetime">
						<a-input v-model:value="formData.residueLifetime" placeholder="请输入剩余寿命" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="使用情况：" name="status">
						<a-input v-model:value="formData.status" placeholder="请输入使用情况" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="入库时间：" name="exportTime">
						<a-date-picker v-model:value="formData.exportTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择入库时间" style="width: 100%" />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="分类id：" name="categoryId">
						<a-input v-model:value="formData.categoryId" placeholder="请输入分类id" allow-clear />
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
			</a-tab-pane>
			<a-tab-pane key="2" tab="零部件配置" force-render>
				<a-row type="flex">
					<a-table 
					:row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"  
					rowKey="id"  
					:dataSource="dataSource"  
					:columns="columns">
					</a-table>
			</a-row>
			</a-tab-pane>
		</a-tabs>
		
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="zbbzEquBasicsDetailsForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
import zbbzEquBasicsDetailsApi from '@/api/biz/zbbzEquBasicsDetailsApi'
import zbbzEquComponentDetailsApi from '@/api/biz/zbbzEquComponentDetailsApi'
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)
	//table数据
	const dataSource = ref([])
	const selectedRowKeys = ref([])
const loadTableDate = () => { 
		console.log("123")
		 zbbzEquComponentDetailsApi.findComponentAll().then((res) => {
			 dataSource.value = res
		})
	}
loadTableDate();	
	//获取表格中选中的数据
const onSelectChange = (selectedRowKeysValue, selectedRows) => {
	selectedRowKeys.value = selectedRowKeysValue
	tableSelect.value = selectedRows
}
	// 打开抽屉
	const onOpen = (record) => {
		console.log(record)
		visible.value = true
		if (record) {
			let recordData = cloneDeep(record)
			formData.value = Object.assign({}, recordData)
		}
	}
	// 关闭抽屉
	const onClose = () => {
		formRef.value.resetFields()
		formData.value = {}
		visible.value = false
	}
	// 默认要校验的
	const formRules = {
	}
	// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		const formDataParam = cloneDeep(formData.value)
		zbbzEquBasicsDetailsApi
			.zbbzEquBasicsDetailsSubmitForm(formDataParam, !formDataParam.id)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
};

// table表头数据
const columns =[
	{
		title: '零部件名称',
		dataIndex: 'name',
		key: 'name',
		align: 'center',
	},
	{
		title: '型号',
		dataIndex: 'model',
		key: 'model',
		align: 'center',
		width: 100,
	},
	{
		title: '描述',
		dataIndex: 'equDesc',
		key: 'equDesc',
		align: 'center',
		width: 200,
	},
	{
		title: '子编号',
		dataIndex: 'iId',
		key: 'iId',
		align: 'center',
		width: 100,
	},
	{
		title: '剩余寿命',
		dataIndex: 'residueLifetime',
		key: 'residueLifetime',
		align: 'center',
	},

];
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
