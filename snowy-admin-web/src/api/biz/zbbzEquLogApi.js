import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/equlog/` + url, ...arg)

/**
 * 装备使用日志Api接口管理器
 *
 * @author czh
 * @date  2023/06/03 21:43
 **/
export default {
	// 获取装备使用日志分页
	zbbzEquLogPage(data) {
		return request('page', data, 'get')
	},
	// 获取装备使用日志列表
	zbbzEquLogList(data) {
		return request('list', data, 'get')
	},
	// 提交装备使用日志表单 edit为true时为编辑，默认为新增
	zbbzEquLogSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除装备使用日志
	zbbzEquLogDelete(data) {
		return request('delete', data)
	},
	// 获取装备使用日志详情
	zbbzEquLogDetail(data) {
		return request('detail', data, 'get')
	}
}
