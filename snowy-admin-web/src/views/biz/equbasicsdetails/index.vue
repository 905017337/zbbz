<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="装备名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入装备名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="型号" name="model">
						<a-input v-model:value="searchFormState.model" placeholder="请输入型号" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="剩余寿命" name="residueLifetime">
						<a-input v-model:value="searchFormState.residueLifetime" placeholder="请输入剩余寿命" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="使用情况" name="status">
						<a-input v-model:value="searchFormState.status" placeholder="请输入使用情况" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
					<a @click="toggleAdvanced" style="margin-left: 8px">
						{{ advanced ? '收起' : '展开' }}
						<component :is="advanced ? 'up-outlined' : 'down-outlined'"/>
					</a>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzEquBasicsDetailsAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzEquBasicsDetailsBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzEquBasicsDetails"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzEquBasicsDetailsEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzEquBasicsDetailsEdit', 'zbbzEquBasicsDetailsDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzEquBasicsDetails(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzEquBasicsDetailsDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equbasicsdetails">
	import Form from './form.vue'
	import zbbzEquBasicsDetailsApi from '@/api/biz/zbbzEquBasicsDetailsApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	// 查询区域显示更多控制
	const advanced = ref(false)
	const toggleAdvanced = () => {
		advanced.value = !advanced.value
	}
	const columns = [
		{
			title: '装备名称',
			dataIndex: 'name'
		},
		{
			title: '型号',
			dataIndex: 'model'
		},
		{
			title: '经纬度',
			dataIndex: 'location'
		},
		{
			title: '剩余寿命',
			dataIndex: 'residueLifetime',
			customRender: ({ text }) => {
				if (text == '4') {
					return '新品'
				} else if (text == '3') {
					return '勘用'
				} else if (text == '2') {
					return '待修'
				} else if (text == '1') {
					return '报废'
				}else{
					return '未知'
				}
			}
			
		},
		{
			title: '使用情况',
			dataIndex: 'status',
			customRender: ({ text }) => {
				return text === 1 ? '使用中' : '空闲'
			}
		},
		{
			title: '入库时间',
			dataIndex: 'exportDate'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzEquBasicsDetailsEdit', 'zbbzEquBasicsDetailsDelete'])) {
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
		return zbbzEquBasicsDetailsApi.zbbzEquBasicsDetailsPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzEquBasicsDetails = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzEquBasicsDetailsApi.zbbzEquBasicsDetailsDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzEquBasicsDetails = (params) => {
		zbbzEquBasicsDetailsApi.zbbzEquBasicsDetailsDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
