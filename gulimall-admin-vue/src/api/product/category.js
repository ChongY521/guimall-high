import request from "@/utils/httpRequest"
const BASE_URL = "/product/category"

/**
 * 菜单三级分类
 * */
export function CategoryListTreeApi(){
  return request({
      url:  request.adornUrl( `${BASE_URL}/list/tree`) ,
      method: "get"
  })
}
/**
 * 删除
 * @param {Array<Number> } categoryIds  三级分类的id
 */
export function CategoryDeleteApi(categoryIds) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: request.adornData(categoryIds, false)
  })
}
export function CategoryInfoApi(categoryId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${categoryId}`),
    method: "get"
  })
}
/**
 * 修改节点排序
 * @param {Array} data 节点集合
 */
export function CategoryUpdataSortApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update/sort`),
    method: "put",
    data: request.adornData(data, false)
  })
}
/**
 * 修改分类
 * @param {*} data 
 */
export function CategoryUpdataApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}
/**
 * 保存分类
 * @param {*} data 
 */
export function CategorySaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}

