<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="零部件名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入零部件名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="型号" name="model">
						<a-input v-model:value="searchFormState.model" placeholder="请输入型号" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzEquComponentDetailsAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<a-button type="primary" @click="ImpExpRef.onOpen()" v-if="hasPerm('zbbzPlanBasicsDetailsAdd')">
						<template #icon><import-outlined /></template>
						<span>{{ $t('common.imports') }}</span>
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzEquComponentDetailsBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzEquComponentDetails"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzEquComponentDetailsEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzEquComponentDetailsEdit', 'zbbzEquComponentDetailsDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzEquComponentDetails(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzEquComponentDetailsDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<ImpExp ref="ImpExpRef" />
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equcomponentdetails">
	import Form from './form.vue'
	import ImpExp from './impExp.vue'
import tool from '@/utils/tool'
	import zbbzEquComponentDetailsApi from '@/api/biz/zbbzEquComponentDetailsApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const ImpExpRef = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '零部件名称',
			dataIndex: 'name'
		},
		{
			title: '型号',
			dataIndex: 'model'
		},
		{
			title: '描述',
			dataIndex: 'equDesc'
		},
		{
			title: '子编号',
			dataIndex: 'iId'
		},
		{
			title: '剩余寿命',
			dataIndex: 'residueLifetime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzEquComponentDetailsEdit', 'zbbzEquComponentDetailsDelete'])) {
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
		return zbbzEquComponentDetailsApi.zbbzEquComponentDetailsPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzEquComponentDetails = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzEquComponentDetailsApi.zbbzEquComponentDetailsDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzEquComponentDetails = (params) => {
		zbbzEquComponentDetailsApi.zbbzEquComponentDetailsDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
