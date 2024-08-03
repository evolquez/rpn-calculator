package com.oletob.rpncalc.ui.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.URLSpan
import android.view.MenuItem
import com.oletob.rpncalc.BuildConfig
import com.oletob.rpncalc.R
import com.oletob.rpncalc.RpnApplication
import com.oletob.rpncalc.databinding.ActivityAboutBinding
import com.oletob.rpncalc.ui.common.BaseActivity
import javax.inject.Inject

class AboutActivity: BaseActivity(), AboutContract.View {

    @Inject lateinit var presenter: AboutContract.Presenter

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as RpnApplication)
            .appGraph
            .aboutComponent()
            .create(this)
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setActionBar(R.string.about, true)

        presenter.init()

        val githubLinkText = getString(R.string.author_github_profile)

        binding.textViewGithub.apply {
            text =  SpannableString(githubLinkText).apply {
                setSpan(URLSpan(githubLinkText), 0, githubLinkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            setOnClickListener {
                presenter.onClickGithub()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressedDispatcher.onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun setVersion() {
        binding.textViewVersion.text = getString(R.string.version_format, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    override fun startSocialProfile(socialProfile: AboutPresenter.SocialProfile) {
        val resString = when(socialProfile){
            AboutPresenter.SocialProfile.GITHUB -> R.string.author_github_profile
        }

        if(resString != 0){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(resString))))
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, AboutActivity::class.java)
    }
}