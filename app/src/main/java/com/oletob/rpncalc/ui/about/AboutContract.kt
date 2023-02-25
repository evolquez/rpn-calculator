package com.oletob.rpncalc.ui.about

import com.oletob.rpncalc.ui.common.BaseView

interface AboutContract {

    interface View: BaseView {
        fun setVersion()
        fun startSocialProfile(socialProfile: AboutPresenter.SocialProfile)
    }

    interface Presenter{
        fun init()
        fun onClickGithub()
    }
}