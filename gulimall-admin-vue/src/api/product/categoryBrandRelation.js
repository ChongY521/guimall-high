import request from "@/utils/httpRequest"
const BASE_URL = "/product/categorybrandrelation"

export function CategoryBrandRelationSaveApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/info/${categoryId}`),
    method: "get",
    data: request.adornData(data, false)
  })
}
export function CategoryBrandRelationDeleteApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/delete`),
    method: "delete",
    data: request.adornData(data, false)
  })
}
export function CategoryBrandRelationCategoryListApi(brandId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/category/list`),
    method: "get",
    params: request.adornParams({ brandId })
  })
}

export function CategoryBrandRelationCategoryBrandListApi(categoryId) {
  return request({
    url: request.adornUrl(`${BASE_URL}/brands/list`),
    method: "get",
    params: request.adornParams({ categoryId })
  })
}
