import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/equcomponentdetails/` + url, ...arg)

/**
 * 装备零部件Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:51
 **/
export default {
	// 获取装备零部件分页
	zbbzEquComponentDetailsPage(data) {
		return request('page', data, 'get')
	},
	// 获取装备零部件列表
	zbbzEquComponentDetailsList(data) {
		return request('list', data, 'get')
	},
	// 提交装备零部件表单 edit为true时为编辑，默认为新增
	zbbzEquComponentDetailsSubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除装备零部件
	zbbzEquComponentDetailsDelete(data) {
		return request('delete', data)
	},
	// 获取装备零部件详情
	zbbzEquComponentDetailsDetail(data) {
		return request('detail', data, 'get')
	},
	// 获取装备零部件列表
	findComponentAll(data) {
		return request('findComponentAll', data, 'get')
	},
	//添加装备对应的零部件
	addComponentForm(data) {
		return request('addComponentForm', data, 'post')
	},
	
	//获取装备对应的零部件列表
	findComponentByPlanId(data) {
		return request('findComponentByPlanId', data, 'get')
	},

}
