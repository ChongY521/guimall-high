import request from "@/utils/httpRequest"
const BASE_URL = "/product/attr"


/**
 * 获取当前分类可以使用的销售属性
 * @param {Number} categoryId 三级分类id
 * @param {*} params  分页参数
 */
export function AttrSaleListApi(categoryId , params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/0/list/${categoryId}`) ,
      method: "get" ,
      params : request.adornParams(params , false )
  })
}
export  function AttrInfoApi(attrId){
  return  request({
      url:  request.adornUrl( `${BASE_URL}/info/${attrId}`) ,
      method: "get"
    })
}


export function ProductAttrListApi(attrType , categoryId , params){
  return request({
      url:  request.adornUrl( `${BASE_URL}/${attrType}/list/${categoryId}`) ,
      method: "get",
      params : request.adornParams(params , false )
  })
}

export  function AttrSaveApi(data){
  return request({
      url:  request.adornUrl( `${BASE_URL}/save`) ,
      method: "post",
      data : data
  })
}
export  function AttrUpdateApi(data){
  return request({
      url:  request.adornUrl( `${BASE_URL}/update`) ,
      method: "put",
      data : data
  })
}


/**
 * 删除商品属性
 * @param {*} attrType  0  基础属性  1 销售属性 
 * @param {*} ids 
 */
export function ProductAttrDeleteApi(attrType , ids){
  return request({
      url:  request.adornUrl( `${BASE_URL}/delete/${ttrtype}`) ,
      method: "delete",
      data : ids
  })
}


// base/listforspu


