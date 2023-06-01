<template>
	<xn-form-container
		:title="formData.id ? '编辑作战任务' : '增加作战任务'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
	<a-tabs >
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
						<a-date-picker v-model:value="formData.startTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择开始时间" style="width: 100%" />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="结束时间：" name="endTime">
						<a-date-picker v-model:value="formData.endTime" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择结束时间" style="width: 100%" />
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
			<a-table :row-selection="rowSelection" :columns="columns" :data-source="data" :scroll="{ x: 2000 }">
			  </a-table>
        <!-- 表格 -->
        <!-- <a-table  @change="tableOnChangeData" :dataSource="dataSource" :columns="columns" /> --> -->
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
	// 抽屉状态
	const visible = ref(false)
	const emit = defineEmits({ successful: null })
	const formRef = ref()
	// 表单数据
	const formData = ref({})
	const submitLoading = ref(false)

	// 打开抽屉
	const onOpen = (record) => {
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
		name: [required('请输入名称')],
		startTime: [required('请输入开始时间')],
		endTime: [required('请输入结束时间')],
		location: [required('请输入作战位置')],
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
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
    
    const loadData=()=>{
        zbbzEquCategoryApi.categoryTree().then((res) => {
          treeData.push(res)
      })
    }
	const tableOnChangeData=(res)=>{
		console.log(res)
	}
    // const onChangeData=(value, label, extra)=>{
    //   console.log(value); //分类id
    //   console.log(label); //分类名称
    //   //获取分类对应的装备
	//   const ZbbzEquAndCatewayParam = {"catewayId":value}
    //   zbbzEquCategoryApi.findEquByCategory(ZbbzEquAndCatewayParam).then((res)=>{
    //    dataSource.value=res
    //   })
    // }
	const columns = [
  { title: 'Name', dataIndex: 'name', key: 'name', fixed: true },
  { title: 'Age', dataIndex: 'age', key: 'age' },
  { title: 'Address', dataIndex: 'address', key: 'address' },
  { title: 'Action', key: 'action' },
];

const data = [
  {
    key: 1,
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park',
	children: [{
		key: 1,
		name: 'John Brown',
		age: 32,
		address: 'New York No. 1 Lake Park'
	}]
  },
  {
    key: 2,
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park',
    description: 'My name is Jim Green, I am 42 years old, living in London No. 1 Lake Park.',
  },
  {
    key: 3,
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park',
    description: 'My name is Joe Black, I am 32 years old, living in Sidney No. 1 Lake Park.',
  },
];

    loadData();
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
