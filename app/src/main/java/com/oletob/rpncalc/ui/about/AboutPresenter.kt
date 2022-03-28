package com.oletob.rpncalc.ui.about

class AboutPresenter(private val view: AboutActivity): AboutContract.Presenter {

    override fun init() {
        view.setActionBar()
        view.setVersion()
    }

    override fun onClickLinkedIn() {
        view.startSocialProfile(SocialProfile.LINKEDIN)
    }

    override fun onClickGithub() {
        view.startSocialProfile(SocialProfile.GITHUB)
    }

    enum class SocialProfile{
        LINKEDIN, GITHUB
    }
}