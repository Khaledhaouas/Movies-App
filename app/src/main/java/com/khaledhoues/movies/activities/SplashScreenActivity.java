package com.khaledhoues.movies.activities;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.khaledhoues.movies.R;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

/**
 * Created by varsovski on 28-Sep-15.
 */
public class SplashScreenActivity extends AwesomeSplash {


    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.movies_icon); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1500); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)

        //Customize Title
        configSplash.setTitleSplash("");

    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

}
