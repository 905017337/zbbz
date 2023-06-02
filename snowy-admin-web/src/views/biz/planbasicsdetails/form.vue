<template>
	<xn-form-container :title="formData.id ? '编辑作战任务' : '增加作战任务'" :width="1200" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-tabs>
			<a-tab-pane key="1" tab="作战任务基础信息">

				<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
					<a-row :gutter="16">
						<a-col :span="12">
							<a-form-item label="名称：" name="name">
								<a-input v-model:value="formData.name" placeholder="请输入名称" allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="开始时间：" name="startTime">
								<a-date-picker v-model:value="formData.startTime" value-format="YYYY-MM-DD HH:mm:ss"
									show-time placeholder="请选择开始时间" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="结束时间：" name="endTime">
								<a-date-picker v-model:value="formData.endTime" value-format="YYYY-MM-DD HH:mm:ss" show-time
									placeholder="请选择结束时间" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="作战位置：" name="location">
								<a-input v-model:value="formData.location" placeholder="请输入作战位置" allow-clear />
							</a-form-item>
						</a-col>
					</a-row>
				</a-form>
			</a-tab-pane>
			<a-tab-pane key="2" tab="配置装备" force-render>
			 <a-row type="flex">
				<a-col :span="6" >
					<a-tree
						v-model:expandedKeys="expandedKeys"
						v-model:selectedKeys="selectedKeys"
						v-model:checkedKeys="checkedKeys"
						checkable
						@check="treeSelect"
						:tree-data="treeData"
					>
					</a-tree>
				</a-col>
				<a-col :span="18" >
					<a-table :row-selection="rowSelection"  rowKey="id" @change="tableOnChangeData" :dataSource="dataSource"  :columns="columns" />
				</a-col>
			</a-row>
			
		</a-tab-pane>
		</a-tabs>

		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="zbbzPlanBasicsDetailsForm">
import { cloneDeep } from 'lodash-es'
import { required } from '@/utils/formRules'
import zbbzPlanBasicsDetailsApi from '@/api/biz/zbbzPlanBasicsDetailsApi'
import zbbzEquCategoryApi from '@/api/biz/zbbzEquCategoryApi'
import zbbzPlanEquApi from '@/api/biz/zbbzPlanEquApi'
import { TreeSelect } from 'ant-design-vue';
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
const SHOW_PARENT = TreeSelect.SHOW_ALL
// 打开抽屉
const onOpen = (record) => {
	console.log(record)
	visible.value = true
	if (record) {
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
	}
	console.log("获取装备配置")
}
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	name: [required('请输入名称')],
	startTime: [required('请输入开始时间')],
	endTime: [required('请输入结束时间')],
	location: [required('请输入作战位置')],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		formData.value.zbbzEquBasicsDetailsParamList = tableSelect
		const formDataParam = cloneDeep(formData.value)
		zbbzPlanBasicsDetailsApi
			.zbbzPlanBasicsDetailsSubmitForm(formDataParam, !formDataParam.id)
			.then(() => {
				onClose()
				emit('successful')
			})
			.finally(() => {
				submitLoading.value = false
			})
	})
};

const expandedKeys = ref();
const selectedKeys = ref();
const checkedKeys = ref();
	watch(expandedKeys, () => {
      console.log('expandedKeys', expandedKeys);
    });
    watch(selectedKeys, () => {
		console.log('selectedKeys', selectedKeys.value);
    });
    watch(checkedKeys, () => {
      console.log('checkedKeys', checkedKeys);
	});
const treeSelect = (selectedKeysValue, info) => {
	let checkedNodes = info.checkedNodes
	let ids = []
	checkedNodes.forEach((item) => {
		ids.push(item.id)
	})
	if(ids.length==0){
		return
	}
	let equByIdsParam = {
		ids: ids,
	}
	zbbzEquCategoryApi.findEquByCategory(equByIdsParam).then((res)=>{
		dataSource.value=res
	})
	selectedKeys.value = selectedKeysValue
};

const treeData = []
const loadData = () => {
	zbbzEquCategoryApi.categoryTree().then((res) => {
		treeData.push(res)
	})
}

//获取装备
const tableOnChangeData = (res) => {
	console.log(res)
}
const dataSource = ref([])
// const onChangeData=(value, label, extra)=>{
//   console.log(value); //分类id
//   console.log(label); //分类名称

// }
const tableSelect =ref([])
//获取表格选中的数据
const rowSelection = {
	//选择后加入对应任务中
	onChange: (selectedRowKeys, selectedRows) => {
		// console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
		tableSelect.value=selectedRows

	},
	
	getCheckboxProps: (record) => ({
		disabled: record.name === 'Disabled User', // Column configuration not to be checked
		name: record.name,
	}),
};

// table表头数据
const columns =[
	{
		title: '姓名',
		dataIndex: 'name',
		key: 'name',
	},
	{
		title: '型号',
		dataIndex: 'model',
		key: 'model',
	},
	{
		title: '所在位置',
		dataIndex: 'location',
		key: 'location',
	},
	{
		title: '剩余寿命',
		dataIndex: 'residueLifetime',
		key: 'residueLifetime',
	},
	{
		title: '占用状态',
		dataIndex: 'status',
		key: 'status',
	},
];

loadData();
// 抛出函数
defineExpose({
	onOpen
})
</script>
