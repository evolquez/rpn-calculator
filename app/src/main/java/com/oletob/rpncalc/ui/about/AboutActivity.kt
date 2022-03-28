package com.oletob.rpncalc.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.oletob.rpncalc.BuildConfig
import com.oletob.rpncalc.R

class AboutActivity: AppCompatActivity(), AboutContract.View {

    private lateinit var versionTextView: TextView
    private lateinit var presenter: AboutContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        versionTextView = findViewById(R.id.text_view_version)

        presenter = AboutPresenter(this)
        presenter.init()

        findViewById<ImageView>(R.id.image_view_linkedin).setOnClickListener{presenter.onClickLinkedIn()}
        findViewById<ImageView>(R.id.image_view_github).setOnClickListener{presenter.onClickGithub()}
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) onBackPressed()

        return super.onOptionsItemSelected(item)
    }

    override fun setActionBar() {
        supportActionBar?.title = getString(R.string.about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setVersion() {
        versionTextView.text = String.format(getString(R.string.version_format),
            BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    override fun startSocialProfile(socialProfile: AboutPresenter.SocialProfile) {
        val resString = when(socialProfile){
            AboutPresenter.SocialProfile.LINKEDIN -> R.string.author_linkedin_profile
            AboutPresenter.SocialProfile.GITHUB -> R.string.author_github_profile
        }

        if(resString != 0){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(resString))))
        }
    }
}