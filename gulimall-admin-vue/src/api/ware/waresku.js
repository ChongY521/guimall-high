import request from "@/utils/httpRequest"

const BASE_URL = "/ware/waresku"

export function WareInfoApi(id) {
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${id}`),
    method: "get"
  })
}
export function WareSkuListApi(params) {
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params, true)
  })
}
export function WareSkuDeleteApi(ids) {
  return request.modelRequest(`${BASE_URL}/delete`, "delete", ids);
}
export function WareSkuInfoApi(id) {
  return request.modelRequest(`${BASE_URL}/info/${id}`);
}
export function WareSkuSaveApi(data) {
  return request.modelRequest(`${BASE_URL}/save`, "post", data);
}
export function WareSkuUpdateApi(data) {
  return request.modelRequest(`${BASE_URL}/update`, "put", data);
}