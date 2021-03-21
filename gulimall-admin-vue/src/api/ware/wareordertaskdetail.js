import request from "@/utils/httpRequest"

const BASE_URL = "/ware/wareordertaskdetail"


export function WareOrderTaskDetailDetailInfoApi(id){
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${id}`),
    method: "get"
  })
}
export function WareOrderTaskDetailDetailListApi(params){
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params , true)
  })
}

export function WareOrderTaskDetailDetailDeleteApi(ids){
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: request.adornData(ids, false)
  })
}

export function WareOrderTaskDetailSaveApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}

export function WareOrderTaskDetailUpdateApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}

