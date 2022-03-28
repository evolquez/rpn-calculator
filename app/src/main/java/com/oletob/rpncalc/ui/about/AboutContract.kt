package com.oletob.rpncalc.ui.about

interface AboutContract {

    interface View {
        fun setActionBar()
        fun setVersion()
        fun startSocialProfile(socialProfile: AboutPresenter.SocialProfile)
    }

    interface Presenter{
        fun init()
        fun onClickLinkedIn()
        fun onClickGithub()
    }
}