import request from "@/utils/httpRequest"
const BASE_URL = "/sys/user"

/**
 * 获取当前用户信息
 */
export function UserInfoApi(userId) {
  return request({
    url: request.adornUrl(userId ? `${BASE_URL}/info/${userId}` :  `${BASE_URL}/info`),
    method: "get",
    params: request.adornParams()
  })
}
/**
 * 用户列表
 */
export  function UserListApi(params){
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params,false)
  })
}
/**
 * 删除用户
 * @param {Array } data 待删除用户的ids
 */
export function UserDeleteApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 添加用户
 * @param {*} data 
 */
export function UserSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 修改用户
 * @param {*} data 
 */
export function UserUpdateApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "post",
    data: request.adornData(data, false)
  })
}