<template>
	<xn-form-container :title="formData.id ? '编辑作战任务' : '增加作战任务'" :width="1300" :visible="visible" :destroy-on-close="true"
		@close="onClose">
		<a-tabs>
			<a-tab-pane key="1" tab="作战任务基础信息">

				<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
					<a-row :gutter="16">
						<a-col :span="12">
							<a-form-item label="任务名称：" name="name" >
								<a-input v-model:value="formData.name" :disabled="disabled" placeholder="请输入任务名称" allow-clear />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="开始时间：" name="startDate">
								<a-date-picker v-model:value="formData.startDate" value-format="YYYY-MM-DD HH:mm:ss"
									show-time placeholder="请选择开始时间" :disabled="disabled" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="结束时间：" name="endDate">
								<a-date-picker v-model:value="formData.endDate" value-format="YYYY-MM-DD HH:mm:ss" show-time
									placeholder="请选择结束时间" :disabled="disabled" style="width: 100%" />
							</a-form-item>
						</a-col>
						<a-col :span="12">
							<a-form-item label="作战位置：" name="location">
								<a-input v-model:value="formData.location"  :disabled="disabled" placeholder="请输入作战位置" allow-clear />
							</a-form-item>
						</a-col>
					</a-row>
				</a-form>
			</a-tab-pane>
			<a-tab-pane key="2" tab="配置装备" force-render>
				<a-row type="flex">
					<a-col :span="6">
						<a-tree v-if="treeData.length" v-model:selectedKeys="selectedKeys" v-model:checkedKeys="checkedKeys"
							checkable :defaultExpandAll="true" :default-selected-keys="treeCurrentKey" 默认高亮显示指定树节点
							@check="treeSelect" :tree-data="treeData">
						</a-tree>
					</a-col>
					<a-col :span="18">
						<a-table  :disabled="disabled"  :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" rowKey="id"
							:dataSource="dataSource" :columns="columns">
							<template #bodyCell="{ column, text, record }">
								<template v-if="column.dataIndex === 'startDate'">
									<a-date-picker
										v-model:value="record.startDate"
										value-format="YYYY-MM-DD HH:mm:ss"
										:show-time="{ defaultValue: dayjs('00:00:00', 'HH:mm:ss') }"
										/>
								</template>
								<template v-if="column.dataIndex === 'endDate'">
									<a-date-picker
										v-model:value="record.endDate"
										value-format="YYYY-MM-DD HH:mm:ss"
										:show-time="{ defaultValue: dayjs('00:00:00', 'HH:mm:ss') }"
										/>
								</template>
								<template v-if="column.dataIndex === 'weight'">
									<a-input v-model:value="record.weight" placeholder="装备权重（维修优先级）" />
								</template>
							</template>
						</a-table>
					</a-col>
				</a-row>

			</a-tab-pane>
			<a-tab-pane key="3" tab="地图" force-render>
				<div class="baidumap" id="allmap"></div>
			</a-tab-pane>
		</a-tabs>

		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit"  :disabled="disabled"  :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="zbbzPlanBasicsDetailsForm">
import { cloneDeep } from 'lodash-es'
import dayjs from 'dayjs';
import { required } from '@/utils/formRules'
import zbbzPlanBasicsDetailsApi from '@/api/biz/zbbzPlanBasicsDetailsApi'
import zbbzEquCategoryApi from '@/api/biz/zbbzEquCategoryApi'
// 抽屉状态
const visible = ref(false)
const emit = defineEmits({ successful: null })
const formRef = ref()
// 表单数据
const formData = ref({})
const submitLoading = ref(false)
//树形数据
const treeData = []
const expandedKeys = ref();
const selectedKeys = ref([]);
const checkedKeys = ref([]);
//tree默认高光id
const treeCurrentKey = ref(['101']);
//tree默认选中的数据
const selectedRowKeys = ref([])
//table
const dataSource = ref([])
const tableSelect = ref([])
// 输入框状态 默认禁用
const disabled = ref(true)
//格式化后端返回的树形数据
const dealTreeData = (treeData) => {
	const data = treeData.map(item => ({
		...item,
		title: item.name,
		key: item.id,
		children: (item.child && item.child.length) ? dealTreeData(item.child) : null
	}))
	return data
}
const selectTableDate = ref([])
const action = ref()
const equByIdsParam = {
	planId: '',
	startDate: '',
	endDate: '',
	ids:''
}
// 打开抽屉
const onOpen = (record, action) => {
	console.log(record)

	visible.value = true
	if ('add' == action.action) { 
		dataSource.value = ([])
		treeData.value = ([])
		selectedKeys.value = ([])
		disabled.value = false
	} else if ('edit' == action.action) {
		equByIdsParam.startDate = record.startDate
		equByIdsParam.planId = record.id
		equByIdsParam.endDate = record.endDate
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
		checkedKeys.value = record.treeSelect
		selectTableDate.value = record.zbbzEquBasicsDetailsParamList
		dataSource.value = selectTableDate.value
		disabled.value = false
	} else if ('view' == action.action) {
		equByIdsParam.startDate = record.startDate
		equByIdsParam.planId = record.id
		equByIdsParam.endDate = record.endDate
		let recordData = cloneDeep(record)
		formData.value = Object.assign({}, recordData)
		checkedKeys.value = record.treeSelect
		selectTableDate.value = record.zbbzEquBasicsDetailsParamList
		dataSource.value = selectTableDate.value
		disabled.value = true
	}
}
const loadData = () => {
	zbbzEquCategoryApi.categoryTree().then((res) => {
		treeData.push(res)
	})
}
loadData()
// 关闭抽屉
const onClose = () => {
	formRef.value.resetFields()
	formData.value = {}
	visible.value = false
}
// 默认要校验的
const formRules = {
	name: [required('请输入任务名称')],
	startDate: [required('请输入开始时间')],
	endDate: [required('请输入结束时间')],
	location: [required('请输入作战位置')],
}
// 验证并提交数据
const onSubmit = () => {
	formRef.value.validate().then(() => {
		submitLoading.value = true
		formData.value.zbbzPlanEquAddParamList = tableSelect
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
//监听tree数据变化
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
	if (false == info.checked) {
		return
	}
	let checkedNodes = info.node.dataRef.id
	let ids = []
	ids.push(checkedNodes)
	if (ids.length == 0) {
		return
	}
	equByIdsParam.ids = ids
	//获取到所有的资源

	zbbzEquCategoryApi.findEquByCategory(equByIdsParam).then((res) => {
		console.log(res)
		//比较res数据中selectTableDate中的数据，如果有相同的设置table的选中状态
		let tempSelectTableDate = cloneDeep(selectTableDate.value)
		let tempRes = cloneDeep(res)
		let tempdate = []
			tempSelectTableDate.forEach((item) => {
				tempRes.forEach((item2) => {
					if (item.equId == item2.id) {
						tempdate.push(item2.id)
					}
				})
			})
		selectedRowKeys.value = tempdate
		dataSource.value = res
	})
	selectedKeys.value = selectedKeysValue
};
//判断tree勾选时是选中还是取消
const treeCheck = (checkedKeysValue, info) => {
	checkedKeys.value = checkedKeysValue
};



//获取表格中选中的数据
const onSelectChange = (selectedRowKeysValue, selectedRows) => {
	selectedRowKeys.value = selectedRowKeysValue
	tableSelect.value = selectedRows
}

// table表头数据
const columns = [
	{
		title: '名称',
		dataIndex: 'name',
		key: 'name',
		align: 'center',
	},
	{
		title: '型号',
		dataIndex: 'model',
		key: 'model',
		align: 'center',
	},
	{
		title: '所在位置',
		dataIndex: 'location',
		key: 'location',
		align: 'center',
	},
	{
		title: '开始时间',
		dataIndex: 'startDate',
		key: 'startDate',
		align: 'center',
	},
	{
		title: '结束时间',
		dataIndex: 'endDate',
		key: 'endDate',
		align: 'center',
	},
	{
		title: '装备权重',
		dataIndex: 'weight',
		key: 'weight',
		align: 'center',
	},

];

// 抛出函数
defineExpose({
	onOpen
})
</script>
