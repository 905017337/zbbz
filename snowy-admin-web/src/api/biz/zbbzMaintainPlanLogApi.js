import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/maintainplanlog/` + url, ...arg)

/**
 *  维护作战任务日志Api接口管理器
 *
 * @author czh
 * @date  2023/06/03 21:40
 **/
export default {
	// 获取 维护作战任务日志分页
	zbbzMaintainPlanLogPage(data) {
		return request('page', data, 'get')
	},
	// 获取 维护作战任务日志列表
	zbbzMaintainPlanLogList(data) {
		return request('list', data, 'get')
	},
	// 提交 维护作战任务日志表单 edit为true时为编辑，默认为新增
	zbbzMaintainPlanLogSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除 维护作战任务日志
	zbbzMaintainPlanLogDelete(data) {
		return request('delete', data)
	},
	// 获取 维护作战任务日志详情
	zbbzMaintainPlanLogDetail(data) {
		return request('detail', data, 'get')
	}
}
