import request from "@/utils/httpRequest"

const BASE_URL = "/ware/wareinfo"

export function WareInfoApi(id){
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${id}`),
    method: "get"
  })
}

export function WareInfoListApi(params){
  return request({
    url: request.adornUrl(`${BASE_URL}/list`),
    method: "get",
    params: request.adornParams(params , true)
  })
}
export function WareInfoDeleteApi(ids){
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: request.adornData(ids, false)
  })
}

export function WareInfoSaveApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}

export function WareInfoUpdateApi(data){
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}
