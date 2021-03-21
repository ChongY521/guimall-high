import Vue from 'vue'
import axios from 'axios'
import router from '@/router'
import qs from 'qs'
import merge from 'lodash/merge'
import { clearLoginInfo } from '@/utils' // index.js

import errorCode from '@/utils/errorCode'
import { WarningConfirm, ErrorNotification, ErrorMessage } from './message.js'

const http = axios.create({
  timeout: 1000 * 30,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})




/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
  config.headers['token'] = Vue.cookie.get('token') // 请求头带上token
  return config
}, error => {
  return Promise.reject(error)
})
/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
  let data = response.data;
  let code = data.code || 0;
  if (code != 0) {
    return requestHasError(data);
  } else {
    return response.data;
  }
}, error => {
  console.error('请求错误' + error)
  ErrorMessage(error.message);

  return Promise.reject(error)
})
/**
 * 判断是否有异常  并作出提示 
 * @param {*} data 响应的数据
 */
function requestHasError(data) {
  const code = data.code;
  // 获取错误信息
  const message = data.msg || errorCode[code] || errorCode['default']
  if (code == 401) {
    WarningConfirm(() => {
      // 重新登录的逻辑
      clearLoginInfo()
      router.push({ name: 'login' })
    }, '登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', '重新登录')
  }
  else if (code === 500) {
    ErrorMessage(message)
    return Promise.reject(new Error(message))
  } else if (code !== 0) {
    if (data.msg) {
      ErrorNotification(message)
    }
    if (data.data) {
      // 参数校验 错误 循环打印所有错误
      let error = data.data;
      if (error instanceof Array) {
        for (const key in error) {
          ErrorNotification(error[key], key, 0);
        }
      }
    }
    return Promise.reject('error')
  }
}
/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
  // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
  return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefultParams = true) => {
  var defaults = {
    't': new Date().getTime()
  }
  return openDefultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefultdata 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefultdata = true, contentType = 'json') => {
  var defaults = {
    't': new Date().getTime()
  }
  data = openDefultdata ? merge(defaults, data) : data
  return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

/**
 * 请求方法
 * @param {*} api  api 地址
 * @param {*} method  请求方式
 * @param {*} data  data数据 ,
 * @param {*} params  params 参数
 */
http.modelRequest = function (api, method = 'get', data, params) {
  return http({
    url: http.adornUrl(api),
    method,
    data: http.adornData(data, false),
    params: http.adornParams(params)
  });
}


export default http