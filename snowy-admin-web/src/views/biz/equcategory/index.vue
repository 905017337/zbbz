<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="类型名称" name="name">
						<a-input v-model:value="searchFormState.name" placeholder="请输入类型名称" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('zbbzEquCategoryAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('zbbzEquCategoryBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchZbbzEquCategory"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('zbbzEquCategoryEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['zbbzEquCategoryEdit', 'zbbzEquCategoryDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteZbbzEquCategory(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('zbbzEquCategoryDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equcategory">
	import Form from './form.vue'
	import zbbzEquCategoryApi from '@/api/biz/zbbzEquCategoryApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		
		{
			title: '节点ID',
			dataIndex: 'id'
		},
		{
			title: '类型名称',
			dataIndex: 'name'
		},
		{
			title: '父节点ID',
			dataIndex: 'parentId'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['zbbzEquCategoryEdit', 'zbbzEquCategoryDelete'])) {
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
		return zbbzEquCategoryApi.zbbzEquCategoryPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteZbbzEquCategory = (record) => {
		let params = [
			{
				id: record.id
			}
		]
		zbbzEquCategoryApi.zbbzEquCategoryDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchZbbzEquCategory = (params) => {
		zbbzEquCategoryApi.zbbzEquCategoryDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const getTree=()=>{
		zbbzEquCategoryApi.categoryTree().then((res) => {
			console.log(res)
		})
	}
	getTree()
</script>
