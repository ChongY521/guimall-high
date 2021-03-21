import request from "@/utils/httpRequest"
const BASE_URL = "/product/brand"

/**
 * 品牌列表
 * @param {*} params 
 */
export function BrandListApi(params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/list`) ,
      method: "get",
      params : request.adornParams(params , false )
  })
}
export function BrandInfoApi(brandId){
  return request({
      url:  request.adornUrl( `${BASE_URL}/info/${brandId}`) ,
      method: "get"
  })
}
/**
 * 修改品牌状态
 * @param {*} data 
 */
export function BrandUpdateStatusApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update/status`),
    method: "put",
    data: request.adornData(data, false)
  })
}
/**
 * 删除品牌
 * @param {Array} data 
 */
export function BrandDeleteApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "delete",
    data: request.adornData(data, false)
  })
}


