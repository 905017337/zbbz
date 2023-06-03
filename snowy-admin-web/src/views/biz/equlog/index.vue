<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="装备名称" name="equName">
						<a-input v-model:value="searchFormState.equName" placeholder="请输入装备名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="任务名称" name="planName">
						<a-input v-model:value="searchFormState.planName" placeholder="请输入任务名称" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzEquLogAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzEquLogBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzEquLog"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzEquLogEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzEquLogEdit', 'zbbzEquLogDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzEquLog(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzEquLogDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equlog">
	import Form from './form.vue'
	import zbbzEquLogApi from '@/api/biz/zbbzEquLogApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '开始时间',
			dataIndex: 'startDate'
		},
		{
			title: '结束时间',
			dataIndex: 'endDate'
		},
		{
			title: '执行任务位置',
			dataIndex: 'location'
		},
		{
			title: '装备名称',
			dataIndex: 'equName'
		},
		{
			title: '任务名称',
			dataIndex: 'planName'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzEquLogEdit', 'zbbzEquLogDelete'])) {
		columns.push({
			title: '操作',
			dataIndex: 'action',
			align: 'center',
			width: '150px'
		})
	}
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
		return zbbzEquLogApi.zbbzEquLogPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzEquLog = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzEquLogApi.zbbzEquLogDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzEquLog = (params) => {
		zbbzEquLogApi.zbbzEquLogDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
