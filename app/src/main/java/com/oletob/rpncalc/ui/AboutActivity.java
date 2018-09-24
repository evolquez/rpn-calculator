package com.oletob.rpncalc.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oletob.rpncalc.BuildConfig;
import com.oletob.rpncalc.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(getString(R.string.about));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.imgLinkedin).setOnClickListener(this);
        findViewById(R.id.imgGithub).setOnClickListener(this);

        TextView appVersion = findViewById(R.id.appVersion);

        final String baseVersion = "Version %s";

        appVersion.setText(String.format(baseVersion, BuildConfig.VERSION_NAME));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgLinkedin:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.author_linkedin_profile))));
                break;
            case R.id.imgGithub:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.author_github_profile))));
                break;
        }
    }
}
