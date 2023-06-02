<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入名称" />
					</a-form-item>

				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table
			ref="table"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.id"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzPlanBasicsDetailsAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzPlanBasicsDetailsBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzPlanBasicsDetails"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)">查看</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzPlanBasicsDetailsEdit', 'zbbzPlanBasicsDetailsDelete'], 'and')" />
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzPlanBasicsDetailsEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzPlanBasicsDetailsEdit', 'zbbzPlanBasicsDetailsDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzPlanBasicsDetails(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzPlanBasicsDetailsDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="planbasicsdetails">
	import Form from './form.vue'
	import zbbzPlanBasicsDetailsApi from '@/api/biz/zbbzPlanBasicsDetailsApi'
	import zbbzPlanEquApi from '@/api/biz/zbbzPlanEquApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const visible = ref(false);
	const showModal = (record) => {
		console.log(record)
		zbbzPlanEquApi.findeqyByPlanId(record.id).then(res => {
			console.log(res)
			dataSource.value = res.data
		})
      visible.value = true;
    };
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '名称',
			dataIndex: 'name'
		},
		{
			title: '开始时间',
			dataIndex: 'startTime'
		},
		{
			title: '结束时间',
			dataIndex: 'endTime'
		},
		{
			title: '作战位置',
			dataIndex: 'location'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzPlanBasicsDetailsEdit', 'zbbzPlanBasicsDetailsDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: '250px'
		})
	}
	const tableColumns =[
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
	const selectedRowKeys = ref([])
	// 列表选择配置
	const options = {
		// columns数字类型字段加入 needTotal: true 可以勾选自动算账
		alert: {
			show: true,
			clear: () => {
				selectedRowKeys.value = ref([])
			}
		},
		rowSelection: {
			onChange: (selectedRowKey, selectedRows) => {
				selectedRowKeys.value = selectedRowKey
			}
		}
	}
	const loadData = (parameter) => {
		const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
		return zbbzPlanBasicsDetailsApi.zbbzPlanBasicsDetailsPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzPlanBasicsDetails = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzPlanBasicsDetailsApi.zbbzPlanBasicsDetailsDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzPlanBasicsDetails = (params) => {
		zbbzPlanBasicsDetailsApi.zbbzPlanBasicsDetailsDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
