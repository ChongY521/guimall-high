import request from "@/utils/httpRequest"

const BASE_URL = "/product/spuinfo"
/**
 * 保存 spu info
 * @param {*} data spu 信息
 */
export function SpuInfoSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 
 * 获取spu列表
 * @param {JSON} param 
 */
export function SpuInfoApi(param) {
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(param , true)
  })
}
export function SpuInfoUpApi(spuId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/${spuId}/up`),
    method: "put"
  })
}

