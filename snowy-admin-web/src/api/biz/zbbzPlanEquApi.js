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
	}
}
