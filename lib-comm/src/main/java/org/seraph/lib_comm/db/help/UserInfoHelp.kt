package org.seraph.lib_comm.db.help

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.save
import com.raizlabs.android.dbflow.kotlinextensions.select
import org.seraph.lib_comm.LibCommConstants
import org.seraph.lib_comm.db.table.UserInfo

/**
 * 用户信息帮助类
 **/
object UserInfoHelp {


    //获取令牌
    fun getToken(): String? {
        return getToken(false)
    }

    /**
     * 获取当前用户的用户id
     */
    fun getToken(isAutoJumpLogin: Boolean): String? {
        return if (getUserInfo(isAutoJumpLogin) != null) {
            getUserInfo(isAutoJumpLogin)!!.token
        } else null
    }


    /**
     * 获取用户表
     */
    fun getUserInfo(): UserInfo? {
        return getUserInfo(false)
    }

    /**
     * 获取用户表
     *
     * @param isAutoJumpLogin 没有用户是否自动跳转登录界面
     */
    fun getUserInfo(isAutoJumpLogin: Boolean): UserInfo? {
        val list = (select from UserInfo::class).queryList()
        if (list.isNotEmpty()) {
            return list[0]
        }
        if (isAutoJumpLogin) {
            startLoginActivity()
        }
        return null
    }


    /**
     * 保存唯一用户
     */
    fun saveUserBean(userInfo: UserInfo) {
        cleanUserBean()
        save(userInfo)
    }

    /**
     * 更新或者保存用户信息
     */
    fun save(userInfo: UserInfo) {
        userInfo.save()
    }

    /**
     * 清除登录信息
     */
    fun cleanUserBean() {
        delete<UserInfo>().execute()
    }


    fun isLogin(): Boolean {
        return isLogin(false)
    }

    /**
     * 是否登录
     * @param isAutoJumpLogin 在没有登录的情况下是否跳转
     */
    fun isLogin(isAutoJumpLogin: Boolean, isShowMsg: Boolean = true): Boolean {
        val list = (select from UserInfo::class).queryList()
        if (list.isNotEmpty()) {
            return true
        }
        if (isAutoJumpLogin) {
            startLoginActivity(isShowMsg)
        }
        return false
    }

    private fun startLoginActivity(isShowMsg: Boolean = true) {
        if (isShowMsg) {
            ToastUtils.showShort("请先登录")
        }
        ARouter.getInstance().build(LibCommConstants.PATH_USER_LOGIN).navigation()
    }

}