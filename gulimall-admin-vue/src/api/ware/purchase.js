import request from "@/utils/httpRequest"
const BASE_URL = "/ware/purchase"

export function PurchaseUpdateApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}
/**
 * 
 * 获取spu列表
 * @param {JSON} param 
 */
export function PurchaseListApi(param) {
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(param , true)
  })
}

/**
 * 
 * @param {Array} ids  
 */
export function PurchaseDeleteApi(ids) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: ids 
  })
}
export function PurchaseMerageApi(data) {
  return request.modelRequest(`${BASE_URL}/merge`, "post", data);
}

export function PurchaseUnreceiveListApi() {
  return request.modelRequest(`${BASE_URL}/unreceive/list`);
}

