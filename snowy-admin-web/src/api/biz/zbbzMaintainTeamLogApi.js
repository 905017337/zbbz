import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/maintainteamlog/` + url, ...arg)

/**
 * 维修团队日志Api接口管理器
 *
 * @author czh
 * @date  2023/06/03 21:38
 **/
export default {
	// 获取维修团队日志分页
	zbbzMaintainTeamLogPage(data) {
		return request('page', data, 'get')
	},
	// 获取维修团队日志列表
	zbbzMaintainTeamLogList(data) {
		return request('list', data, 'get')
	},
	// 提交维修团队日志表单 edit为true时为编辑，默认为新增
	zbbzMaintainTeamLogSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除维修团队日志
	zbbzMaintainTeamLogDelete(data) {
		return request('delete', data)
	},
	// 获取维修团队日志详情
	zbbzMaintainTeamLogDetail(data) {
		return request('detail', data, 'get')
	}
}
