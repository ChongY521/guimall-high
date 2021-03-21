import request from "@/utils/httpRequest"
const BASE_URL = "/product/attrgroup"
/**
 * 17、获取分类下所有分组&关联属性
 * @param {Number} categoryId  三级分类id
 */
export function AttrGroupCategoryWithAttrApi(categoryId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/${categoryId}/withattr`),
    method: "get",
    params: request.adornParams({}, true)
  })
}


export function AttrGroupListApi(categoryId , params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/list/${categoryId}`) ,
      method: "get",
      params : request.adornParams(params , false )
  })
}
export function AttrGroupInfoApi(attrgroupId){
  return request({
      url:  request.adornUrl( `${BASE_URL}/info/${attrgroupId}`) ,
      method: "get"
  })
}
export function AttrGroupDeleteApi(ids) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delte`),
    method: "delete",
    data: request.adornData(ids, false)
  })
}

export function AttrGroupSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/save`),
    method: "post",
    data: request.adornData(data, false)
  })
}
export function AttrGroupUpdateApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/update`),
    method: "put",
    data: request.adornData(data, false)
  })
}

// ====== 关联关系api=====

/**
 * 删除属性与属性分组的关系
 * 
 * @param {Array} ids 
 */
export function AttrAttrGroupRelationDeleteApi(ids) {
  return request({
    url: request.adornUrl(`${BASE_URL}/attr/relation/delte`),
    method: "delete",
    data: request.adornData(ids, false)
  })
}
export function AttrAttrGroupRelationSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/attr/relation`),
    method: "post",
    data: request.adornData(data, false)
  })
}
/**
 * 获取已关联的分组信息
 * @param {*} attrgroupId 
 */
export function AttrAttrGroupRelationListApi(attrgroupId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/${attrgroupId}/attr/relation`),
    method: "get"
  })
}
/**
 * 获取未关联的分组信息
 * @param {*} attrgroupId 
 */
export function AttrAttrGroupNoRelationListApi(attrgroupId , params) {
  return request({
    url: request.adornUrl(`${BASE_URL}/${attrgroupId}/noattr/relation`),
    method: "get",
    params: request.adornParams(params)
  })
}
