<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="维修团队名" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入维修团队名" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzMaintainTeamDetailsAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<a-button type="primary" @click="formRef.onOpen()">
						<template #icon><plus-outlined /></template>
						导入
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzMaintainTeamDetailsBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzMaintainTeamDetails"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzMaintainTeamDetailsEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzMaintainTeamDetailsEdit', 'zbbzMaintainTeamDetailsDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzMaintainTeamDetails(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzMaintainTeamDetailsDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="maintainteamdetails">
	import Form from './form.vue'
	import zbbzMaintainTeamDetailsApi from '@/api/biz/zbbzMaintainTeamDetailsApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '维修团队名',
			dataIndex: 'name'
		},
		{
			title: '经纬度',
			dataIndex: 'location'
		},
		{
			title: '维修效率',
			dataIndex: 'maintainCapacity'
		},
		{
			title: '移动效率',
			dataIndex: 'actionCapacity'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzMaintainTeamDetailsEdit', 'zbbzMaintainTeamDetailsDelete'])) {
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
		return zbbzMaintainTeamDetailsApi.zbbzMaintainTeamDetailsPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzMaintainTeamDetails = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzMaintainTeamDetailsApi.zbbzMaintainTeamDetailsDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzMaintainTeamDetails = (params) => {
		zbbzMaintainTeamDetailsApi.zbbzMaintainTeamDetailsDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
