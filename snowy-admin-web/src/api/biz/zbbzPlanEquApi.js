import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/planequ/` + url, ...arg)

/**
 * 任务装备配置Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:42
 **/
export default {
	// 获取任务装备配置分页
	zbbzPlanEquPage(data) {
		return request('page', data, 'get')
	},
	// 获取任务装备配置列表
	zbbzPlanEquList(data) {
		return request('list', data, 'get')
	},
	// 提交任务装备配置表单 edit为true时为编辑，默认为新增
	zbbzPlanEquSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除任务装备配置
	zbbzPlanEquDelete(data) {
		return request('delete', data)
	},
	// 获取任务装备配置详情
	zbbzPlanEquDetail(data) {
		return request('detail', data, 'get')
	},
	// 更新任务装备配置
	updatePlanEqu(data) {
		return request('updatePlanEqu', data, 'post')
	},
	// 根据分类id获取对应的装备
	findEquByCategory(data) {
		return request('findEquByCategory', data, 'get')
	},
	// 根据计划id获取对应的装备
	findeqyByPlanId(data){
		return request('findeqyByPlanId', data, 'get')
	}
}
