<template>
	<xn-form-container :title="formData.id ? '编辑基础信息' : '增加基础信息'" :width="700" :visible="visible" :destroy-on-close="true"
		@close="onClose">
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
								<a-select
									ref="select"
									v-model:value="formData.status"
									@change="handleChange"
									>
									<a-select-option value="0">空闲</a-select-option>
									<a-select-option value="1">占用</a-select-option>
								</a-select>
							</a-form-item>
						</a-col>
						<!-- <a-col :span="12">
							<a-form-item label="入库时间：" name="exportDate">
								<a-date-picker v-model:value="formData.exportDate" value-format="YYYY-MM-DD HH:mm:ss"
									 placeholder="请选择入库时间" style="width: 100%" />
							</a-form-item>
						</a-col> -->
						<a-col :span="12">
							<!-- :field-names="{label: 'title', value: 'key', }" -->
							<a-form-item label="分类id：" name="categoryId">
								<a-cascader v-model:value="formData.categoryId"
								:options="categoryOptions" placeholder="请选择分类" />
							</a-form-item>
							
						</a-col>
					</a-row>
				</a-form>
			</a-tab-pane>
			<a-tab-pane key="2" tab="零部件配置" force-render>
				<a-row type="flex">
					<a-button type="primary" @click="EquComponent" style="margin: 5px"><template
							#icon><plus-outlined /></template> 新增</a-button>
					<a-table :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" rowKey="id"
						:dataSource="dataSource" :columns="columns">
					</a-table>
				</a-row>
			</a-tab-pane>
		</a-tabs>

		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
	<a-modal v-model:visible="EquComponentModal" title="添加零部件信息" @ok="addEquComponent">
		<a-form ref="formRef" :model="component" :rules="formRules" layout="vertical">
			<a-row :gutter="16">
				<a-col :span="12">
					<a-form-item label="名称" name="name">
						<a-input v-model:value="component.name" placeholder="请输入零部件名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="型号" name="model">
						<a-input v-model:value="component.model" placeholder="请输入零部件型号" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="子编号" name="iId">
						<a-input v-model:value="component.iId" placeholder="请输入零部件子编号" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="描述" name="equDesc">
						<a-input v-model:value="component.equDesc" placeholder="请输入零部件描述信息" allow-clear />
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
	</a-modal>
</template>

<script setup name="zbbzEquBasicsDetailsForm">
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import zbbzEquBasicsDetailsApi from '@/api/biz/zbbzEquBasicsDetailsApi'
import zbbzEquComponentDetailsApi from '@/api/biz/zbbzEquComponentDetailsApi'
import zbbzEquCategoryApi from '@/api/biz/zbbzEquCategoryApi'
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
//装备分类
const loadTableDate = () => {
	console.log("123")

}

loadTableDate();
const EquComponentModal = ref(false)
// 对话框
const EquComponent = () => {
	EquComponentModal.value = true;
};
//获取表格中选中的数据
const onSelectChange = (selectedRowKeysValue, selectedRows) => {
	selectedRowKeys.value = selectedRowKeysValue
	tableSelect.value = selectedRows
}
const handleChange=(value)=>{
	console.log(value)
}
const component = ref({})
const addEquComponent = () => {
	component.value.equId = equId
	console.log(component)
	EquComponentModal.value = false
	console.log(component)
	const formDataParam = cloneDeep(component.value)
	console.log(formDataParam)
	zbbzEquComponentDetailsApi
		.addComponentForm(formDataParam)
		.then(() => {
			let componentParm = {
				equId: equId.value
			}
			zbbzEquComponentDetailsApi.findComponentByPlanId(componentParm).then((res) => {
				dataSource.value = res
			})
			onClose()
			emit('successful')

		})
		.finally(() => {
			submitLoading.value = false
		})
}
const equId = ref();
// 打开抽屉
const onOpen = (record) => {
	console.log(record)
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		equId.value = recordData.id
		console.log(equId.value)
		formData.value = Object.assign({}, recordData)
		console.log(formData.value)
		let componentParm = {
			equId: equId.value
		}
		console.log(record)
		zbbzEquComponentDetailsApi.findComponentByPlanId(componentParm).then((res) => {
			dataSource.value = res
		})
	}
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
const categoryOptions = ref()
//格式化后端返回的树形数据
const dealTreeData = (treeData) => {
	let data = Array.from(treeData);
	console.log(treeData)
	const temp = data.map(item => ({
		title: item.title,
		key: item.id,
		children: (item.child && item.child.length) ? dealTreeData(Array.from(item.child)) : null
	}))
	console.log(temp)
	return temp
}

const category = () => {
	zbbzEquCategoryApi.categoryTreeList().then((res) => {
		//将res转成数组
		// let temp = Array.from(res)
		categoryOptions.value = res
	})
}
category()
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
const columns = [
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
