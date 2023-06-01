import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/planbasicsdetails/` + url, ...arg)

/**
 * 作战任务Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:40
 **/
export default {
	// 获取作战任务分页
	zbbzPlanBasicsDetailsPage(data) {
		return request('page', data, 'get')
	},
	// 获取作战任务列表
	zbbzPlanBasicsDetailsList(data) {
		return request('list', data, 'get')
	},
	// 提交作战任务表单 edit为true时为编辑，默认为新增
	zbbzPlanBasicsDetailsSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除作战任务
	zbbzPlanBasicsDetailsDelete(data) {
		return request('delete', data)
	},
	// 获取作战任务详情
	zbbzPlanBasicsDetailsDetail(data) {
		return request('detail', data, 'get')
	},

}
