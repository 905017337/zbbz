<template>
	<xn-form-container
		:title="formData.id ? '编辑任务修改记录' : '增加任务修改记录'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-form-item label="任务id：" name="planId">
				<a-input v-model:value="formData.planId" placeholder="请输入任务id" allow-clear />
			</a-form-item>
			<a-form-item label="任务开始时间：" name="startDate">
				<a-date-picker v-model:value="formData.startDate" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择任务开始时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="任务结束时间：" name="endDate">
				<a-date-picker v-model:value="formData.endDate" value-format="YYYY-MM-DD HH:mm:ss" show-time placeholder="请选择任务结束时间" style="width: 100%" />
			</a-form-item>
			<a-form-item label="装备配置：" name="equIds">
				<a-input v-model:value="formData.equIds" placeholder="请输入装备配置" allow-clear />
			</a-form-item>
			<a-form-item label="任务执行地点：" name="location">
				<a-input v-model:value="formData.location" placeholder="请输入任务执行地点" allow-clear />
			</a-form-item>
			<a-form-item label="任务名称：" name="planName">
				<a-input v-model:value="formData.planName" placeholder="请输入任务名称" allow-clear />
			</a-form-item>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="zbbzPlanLogForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import zbbzPlanLogApi from '@/api/biz/zbbzPlanLogApi'
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
	}
	// 验证并提交数据
	const onSubmit = () => {
		formRef.value.validate().then(() => {
			submitLoading.value = true
			const formDataParam = cloneDeep(formData.value)
			zbbzPlanLogApi
				.zbbzPlanLogSubmitForm(formDataParam, !formDataParam.id)
				.then(() => {
					onClose()
					emit('successful')
				})
				.finally(() => {
					submitLoading.value = false
				})
		})
	}
	// 抛出函数
	defineExpose({
		onOpen
	})
</script>
