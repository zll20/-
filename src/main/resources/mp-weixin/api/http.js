/**
 * 通用网络请求
 * 基于 Promise 对象实现更简单的 request 使用方式，支持请求和响应拦截
 */
const base = require('./base')
const utils = require('../utils/utils')
exports.get = function(funName, obj) {
  let  token = wx.getStorageSync("appToken");
  return new Promise((resolve, reject) => {
    wx.request({
      url: base.base.url + funName,
      method: 'GET',
      data:obj,
      header: {
        'content-type': 'application/json' ,// 默认值
        'Token' : token,
      },
      success: function(res) {
        if(res.data.code==0||res.data.code==510) {
          resolve(res.data)
        } else if(res.data.code == 401){
          utils.msg('请先登录')
          wx.clearStorageSync()
        } else {
          // 只有调用方明确要求显示错误时才显示
          if(obj && obj.showError !== false) {
            utils.msg(res.data.msg)
          }
          reject(res.data)
        }
      },
      fail: function(err) {
        reject(err)
      }
    })
  });
}
exports.getNoLogin = function(funName, obj) {
  return new Promise((resolve, reject) => {
    wx.request({
      url: base.base.url + funName,
      method: 'GET',
      data:obj,
      header: {
        'content-type': 'application/json' ,// 默认值
      },
      success: function(res) {
        if(res.data.code==0||res.data.code==510) {
          resolve(res.data)
        } else if(res.data.code == 401){
          utils.msg('请先登录')
          wx.clearStorageSync()
        } else {
          if(obj && obj.showError !== false) {
            utils.msg(res.data.msg)
          }
          reject(res.data)
        }
      },
      fail: function(err) {
        reject(err)
      }
    })
  })
}
exports.post = function(funName, _data) {
  return new Promise((resolve) => {
    let  token = wx.getStorageSync("appToken");
    wx.request({
      url: base.base.url + funName,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      data: _data,
      header: {
        'Content-Type': 'application/json;charset=UTF-8',
        'Token' : token,
      }, // 设置请求的 header
      success: function(res) {
        if(res.data.code==0||res.data.code==510||(funName.indexOf('updateColumn')!=-1&&res.data.code==500)||(funName.indexOf('deleteColumn')!=-1&&res.data.code==500)) {
          resolve(res.data)
        } else if(res.data.code == 401){
          utils.msg('请先登录')
          wx.clearStorageSync()
        } else {
          utils.msg(res.data.msg)
        }
      }
    })
  })
}
exports.post2 = function(funName, _data) {
  return new Promise((resolve,reject) => {
    let  token = wx.getStorageSync("appToken");
    wx.request({
      url: base.base.url + funName,
      method: 'POST', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      data: _data,
      header: {
        'Content-Type': 'application/json;charset=UTF-8',
        'Token' : token,
      }, // 设置请求的 header
      success: function(res) {
        if(res.data.code==0||res.data.code==510||(funName.indexOf('updateColumn')!=-1&&res.data.code==500)||(funName.indexOf('deleteColumn')!=-1&&res.data.code==500)) {
          resolve(res.data)
        } else if(res.data.code == 401){
          utils.msg('请先登录')
          wx.clearStorageSync()
        } else {
          reject(res.data)
        }
      }
    })
  })
}
exports.upload =  function async(mediaType) {
  return new Promise((resolve) => {
    wx.chooseMedia({
      count: 9, //默认9
      sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
      mediaType: mediaType,
      //sourceType: ['album'], //从相册选择
      success: (res) => {
        wx.uploadFile({
          url: `${base.base.url}file/upload`, //仅为示例，非真实的接口地址
          filePath: res.tempFiles[0].tempFilePath,
          name: 'file',
          header: {
            'Token': wx.getStorageSync("appToken")
          },
          success: (uploadFileRes) => {
            let result = JSON.parse(uploadFileRes.data);
            if (result.code == 0) {
              resolve(result);
            } else if(result.code == 401){
              utils.msg('请先登录')
              wx.clearStorageSync()
            } else {
              wx.showToast({
                title: result.msg,
                icon: 'none',
                duration: 2000
              });
            }
          }
        });
      }
    });
  });
}