import request from "@/utils/httpRequest"
const BASE_URL = "/sys"

/**
 * 登录
 * @param {*} data 
 */
export function LoginApi(data) {
  return request({
    url: request.adornUrl(`${BASE_URL}/login`),
    method: "post",
    data: request.adornData(data, false)
  })
}
export function CaptchaPathApi(uuid){
  return  request.adornUrl(`/captcha.jpg?uuid=${uuid}`) ;
}