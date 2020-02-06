package club.geekart.multiverse.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import club.geekart.multiverse.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity{

    Toolbar mToolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.about_layout);
        mToolbar = (Toolbar) findViewById(R.id.others_toolbar);

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); */
        Element versionElement = new Element();
        versionElement.setTitle(getString(R.string.version));

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.about)
                .setDescription(getString(R.string.about_description))
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("clubgeekart@gmail.com")
                .addWebsite(getString(R.string.website_link))
                .addFacebook("nazarov7mu")
                .addPlayStore("club.geekart.multiverse")
                .addInstagram("nazarov7mu")
                .create();

        linearLayout.addView(aboutPage);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish(); // Close activity when click back button
        return super.onOptionsItemSelected(item);
    }
}



