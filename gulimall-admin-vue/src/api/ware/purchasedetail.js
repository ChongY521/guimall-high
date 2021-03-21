import request from "@/utils/httpRequest"
const BASE_URL = "/ware/purchasedetail"



export function PurchaseDetailListApi(params) {
  return request.modelRequest(`${BASE_URL}/list`,"get","" , params);
}
export function PurchaseDetailDeleteApi(data) {
  return request.modelRequest(`${BASE_URL}/delete`,"delete" , data);
}
export function PurchaseDetailInfoApi(id) {
  return request.modelRequest(`${BASE_URL}/info/${id}`);
}
export function PurchaseDetailSaveApi(data) {
  return request.modelRequest(`${BASE_URL}/save`,"post" , data);
}
export function PurchaseDetailUpdateApi(data) {
  return request.modelRequest(`${BASE_URL}/update`,"put" , data);
}
