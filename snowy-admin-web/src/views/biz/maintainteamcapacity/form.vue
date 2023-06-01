<template>
	<xn-form-container
		:title="formData.id ? '编辑零部件管理' : '增加零部件管理'"
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
					<a-form-item label="零部件名称：" name="equComponentName">
						<a-input v-model:value="formData.equComponentName" placeholder="请输入零部件名称" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="能力：" name="capacity">
						<a-input v-model:value="formData.capacity" placeholder="请输入能力" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="数量：" name="number">
						<a-input v-model:value="formData.number" placeholder="请输入数量" allow-clear />
					</a-form-item>
				</a-col>
				<a-col :span="12">
					<a-form-item label="剩余寿命：" name="residueLifetime">
						<a-input v-model:value="formData.residueLifetime" placeholder="请输入剩余寿命" allow-clear />
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

<script setup name="zbbzMaintainTeamCapacityForm">
	import { cloneDeep } from 'lodash-es'
	import { required } from '@/utils/formRules'
	import zbbzMaintainTeamCapacityApi from '@/api/biz/zbbzMaintainTeamCapacityApi'
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
			zbbzMaintainTeamCapacityApi
				.zbbzMaintainTeamCapacitySubmitForm(formDataParam, !formDataParam.id)
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
