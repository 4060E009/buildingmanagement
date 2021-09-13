package com.example.buildingmanagement

import android.R
import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout


class AndroidBug5497Workaround {
    fun assistActivity(activity: Activity) {
        AndroidBug5497Workaround(activity)
    }

    private var mChildOfContent: View? = null
    private var usableHeightPrevious = 0
    private var frameLayoutParams: FrameLayout.LayoutParams? = null
    private var contentHeight = 0
    private var isfirst = true
    private var activity: Activity? = null
    private var statusBarHeight = 0

    private fun AndroidBug5497Workaround(activity: Activity) {
        //获取状态栏的高度
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
        this.activity = activity
        val content = activity.findViewById<View>(R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)

        //界面出现变动都会调用这个监听事件
        mChildOfContent?.viewTreeObserver?.addOnGlobalLayoutListener(OnGlobalLayoutListener {
            if (isfirst) {
                contentHeight = mChildOfContent?.height!! //兼容华为等机型
                isfirst = false
            }
            possiblyResizeChildOfContent()
        })
        frameLayoutParams = mChildOfContent?.layoutParams as FrameLayout.LayoutParams?
    }

    //重新调整跟布局的高度
    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()

        //当前可见高度和上一次可见高度不一致 布局变动
        if (usableHeightNow != usableHeightPrevious) {
            //int usableHeightSansKeyboard2 = mChildOfContent.getHeight();//兼容华为等机型
            val usableHeightSansKeyboard: Int = mChildOfContent?.rootView?.height!!
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                    frameLayoutParams!!.height =
                        usableHeightSansKeyboard - heightDifference + statusBarHeight
                } else {
                    frameLayoutParams!!.height = usableHeightSansKeyboard - heightDifference
                }
            } else {
                frameLayoutParams!!.height = contentHeight
            }
            mChildOfContent?.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    /**     * 计算mChildOfContent可见高度     ** @return      */
    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent?.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

}
