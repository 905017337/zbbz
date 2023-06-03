import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/planlog/` + url, ...arg)

/**
 * 任务修改记录Api接口管理器
 *
 * @author czh
 * @date  2023/06/03 21:29
 **/
export default {
	// 获取任务修改记录分页
	zbbzPlanLogPage(data) {
		return request('page', data, 'get')
	},
	// 获取任务修改记录列表
	zbbzPlanLogList(data) {
		return request('list', data, 'get')
	},
	// 提交任务修改记录表单 edit为true时为编辑，默认为新增
	zbbzPlanLogSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除任务修改记录
	zbbzPlanLogDelete(data) {
		return request('delete', data)
	},
	// 获取任务修改记录详情
	zbbzPlanLogDetail(data) {
		return request('detail', data, 'get')
	}
}
