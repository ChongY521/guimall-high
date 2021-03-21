import request from "@/utils/httpRequest"

const BASE_URL = "/ware/wareordertask"


export function WareOrderTaskInfoApi(id){
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${id}`),
    method: "get"
  })
}

export function WareOrderTaskListApi(params){
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params , true)
  })
}

export function WareOrderTaskDeleteApi(ids){
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: request.adornData(ids, false)
  })
}

export function WareOrderTaskSaveApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}

export function WareOrderTaskUpdateApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}

