package com.example.ipclib.bean

import com.example.ipclib.ServiceManager

/**

 * @Author: chen

 * @datetime: 2024/4/18

 * @desc:

 */
class RequestBean(val name:String?,val methodName:String?,val type:ServiceManager.ServiceType,val parameters : ArrayList<RequestParameter>?) {



}