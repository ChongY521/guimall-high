import request from "@/utils/httpRequest"
const BASE_URL = "/sys/menu"

export function MenuNavApi() {
  return request({
    url: request.adornUrl(`${BASE_URL}/nav`),
    method: "get",
    params: request.adornParams()
  })
}
export function MenuSelectApi() {
  return request({
    url: request.adornUrl(`${BASE_URL}/select`),
    method: "get",
    params: request.adornParams()
  })
}
export function MenuInfoApi(menuId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${menuId}`),
    method: "get",
    params: request.adornParams()
  })
}
/**
 * 所有菜单
 */
export function MenuListApi() {
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams()
  })
}
/**
 * 删除菜单
 * @param {Number} id  菜单id
 */
export function MenuDeleteApi(id) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete/${id}`),
    method: "post"
  })
}
/**
 * 添加菜单
 * @param {*} data 
 */
export function MenuSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 修改菜单
 * @param {*} data 
 */
export function MenuUpdateApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "post",
    data: request.adornData(data, false)
  })
}