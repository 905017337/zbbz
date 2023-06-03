<template>
	<xn-form-container
		:title="formData.id ? '编辑 维护作战任务日志' : '增加 维护作战任务日志'"
		:width="700"
		:visible="visible"
		:destroy-on-close="true"
		@close="onClose"
	>
		<a-form ref="formRef" :model="formData" :rules="formRules" layout="vertical">
			<a-row :gutter="16">
				<a-col :span="12">
					<a-form-item label="维修团队id：" name="maintainTeamId">
						<a-input v-model:value="formData.maintainTeamId" placeholder="请输入维修团队id" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="任务id：" name="planId">
						<a-input v-model:value="formData.planId" placeholder="请输入任务id" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="维修团队详情：" name="maintainTeamInfo">
						<a-input v-model:value="formData.maintainTeamInfo" placeholder="请输入维修团队详情" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="任务名称：" name="planName">
						<a-input v-model:value="formData.planName" placeholder="请输入任务名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="维修团队：" name="maintainTeamName">
						<a-input v-model:value="formData.maintainTeamName" placeholder="请输入维修团队" allow-clear />
					</a-form-item>
				</a-col>
			</a-row>
		</a-form>
		<template #footer>
			<a-button style="margin-right: 8px" @click="onClose">关闭</a-button>
			<a-button type="primary" @click="onSubmit" :loading="submitLoading">保存</a-button>
		</template>
	</xn-form-container>
</template>

<script setup name="zbbzMaintainPlanLogForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import zbbzMaintainPlanLogApi from '@/api/biz/zbbzMaintainPlanLogApi'
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
			zbbzMaintainPlanLogApi
				.zbbzMaintainPlanLogSubmitForm(formDataParam, !formDataParam.id)
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
