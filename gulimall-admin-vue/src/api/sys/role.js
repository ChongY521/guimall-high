import request from "@/utils/httpRequest"
const BASE_URL = "/sys/role"

/**
 * 查看当前用户权限
 */
export function RoleSelectApi() {
  return request({
    url: request.adornUrl(`${BASE_URL}/select`),
    method: "get",
    params: request.adornParams()
  })
}
/**
 * 所有的权限
 */
export function RoleListApi(params) {
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params)
  })
}
/**
 * role 详情
 * @param {*} roleId 
 */
export function RoleInfoApi(roleId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${roleId}`),
    method: "get",
    params: request.adornParams()
  })
}


/**
 * 删除权限
 * @param {Array} data 权限的ids
 */
export function RoleDeleteApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "post",
    data: request.adornData(data, false)
  })
}

/**
 * 新增权限
 * @param {*} data 
 */
export function RoleSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 修改权限
 * @param {*} data 
 */
export function RoleUpdateApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "post",
    data: request.adornData(data, false)
  })
}

