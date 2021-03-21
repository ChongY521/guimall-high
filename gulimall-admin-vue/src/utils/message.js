import { MessageBox, Message, Notification } from "element-ui"

function modeMessage(msg , type='info' , clallback = undefined){
  Message({
    type: type,
    message: msg ,
    showClose: true,
    onClose: () => {
      if (callback && typeof callback === 'function') {
        callback();
      }
    }
  })
}

// 普通消息
export function ErrorMessage(msg) {
  modeMessage(msg , "error")
}
export function SuccessMessage(msg, callback) {
  modeMessage(msg , "success" , callback)
}

export function InfoMessage(msg) {
  modeMessage(msg)
}

// end 普通消息
// 弹窗回调消息
function ConfirmMessageBox(type, callback, msg, title = '提示', confirmButtonText = '确认', cancelButtonText = '取消') {
  MessageBox.confirm(msg, title, {
    confirmButtonText,
    cancelButtonText,
    type: type ,
    center: false
  }).then(() => {
    console.log(callback , "callback")
    if (callback && typeof callback === 'function') {
      
      callback();
    }
  }).catch((e) => {
    InfoMessage("已取消")
  })

}
/**
 * 
 * @param {Function} callback  确认回调函数 
 * @param {String} msg 消息内容
 * @param {String} title 标题
 * @param {String} confirmButton Text
 * @param {String} canclButton Text
 */
export function WarningConfirm(callback, msg = '警告', title = '提示', confirmButtonText = '确认', canclButtonText = '取消') {
  ConfirmMessageBox('waring', callback, msg);
}


// end 弹窗回调消息
// 多个消息Notification
export function ErrorNotification(msg, title = '错误', duration = 4500) {
  Notification({
    title,
    message: msg,
    type: 'error',
    position: 'top-right',
    duration
  })
}





