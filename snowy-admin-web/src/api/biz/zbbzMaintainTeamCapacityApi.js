import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/maintainteamcapacity/` + url, ...arg)

/**
 * 零部件管理Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:49
 **/
export default {
	// 获取零部件管理分页
	zbbzMaintainTeamCapacityPage(data) {
		return request('page', data, 'get')
	},
	// 获取零部件管理列表
	zbbzMaintainTeamCapacityList(data) {
		return request('list', data, 'get')
	},
	// 提交零部件管理表单 edit为true时为编辑，默认为新增
	zbbzMaintainTeamCapacitySubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除零部件管理
	zbbzMaintainTeamCapacityDelete(data) {
		return request('delete', data)
	},
	// 获取零部件管理详情
	zbbzMaintainTeamCapacityDetail(data) {
		return request('detail', data, 'get')
	},

}
