package com.oletob.rpncalc.ui.about

class AboutPresenter(private val view: AboutActivity): AboutContract.Presenter {

    override fun init() {
        view.setVersion()
    }

    override fun onClickGithub() {
        view.startSocialProfile(SocialProfile.GITHUB)
    }

    enum class SocialProfile{
        GITHUB
    }
}