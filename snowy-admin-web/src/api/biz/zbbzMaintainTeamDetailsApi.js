import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/maintainteamdetails/` + url, ...arg)

/**
 * 基础信息Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:43
 **/
export default {
	// 获取基础信息分页
	zbbzMaintainTeamDetailsPage(data) {
		return request('page', data, 'get')
	},
	// 获取基础信息列表
	zbbzMaintainTeamDetailsList(data) {
		return request('list', data, 'get')
	},
	// 提交基础信息表单 edit为true时为编辑，默认为新增
	zbbzMaintainTeamDetailsSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除基础信息
	zbbzMaintainTeamDetailsDelete(data) {
		return request('delete', data)
	},
	// 获取基础信息详情
	zbbzMaintainTeamDetailsDetail(data) {
		return request('detail', data, 'get')
	}
}
