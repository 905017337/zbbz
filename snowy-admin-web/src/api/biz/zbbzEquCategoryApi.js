import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/equcategory/` + url, ...arg)

/**
 * 装备分类Api接口管理器
 *
 * @author czh
 * @date  2023/06/01 12:49
 **/
export default {
	// 获取装备分类分页
	zbbzEquCategoryPage(data) {
		return request('page', data, 'get')
	},
	// 获取装备分类列表
	zbbzEquCategoryList(data) {
		return request('list', data, 'get')
	},
	// 提交装备分类表单 edit为true时为编辑，默认为新增
	zbbzEquCategorySubmitForm(data, edit = false) {
		return request(edit ? 'add' : 'edit', data)
	},
	// 删除装备分类
	zbbzEquCategoryDelete(data) {
		return request('delete', data)
	},
	// 获取装备分类详情
	zbbzEquCategoryDetail(data) {
		return request('detail', data, 'get')
	},
	categoryTree(data) {
		return request('categoryTree', data, 'get')
	},
	//获取分类对应的装备
	findEquByCategory(data){
		return request("findEquByCategory",data,"get")
	}
}
