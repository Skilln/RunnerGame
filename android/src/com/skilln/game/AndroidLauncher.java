package com.skilln.game;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.skilln.game.Application;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private InterstitialAd ad;

	private Handler handler = new Handler();

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobileAds.initialize(this, "ca-app-pub-9869688794553717~8194525926");

		ad = new InterstitialAd(this);
		ad.setAdUnitId("ca-app-pub-9869688794553717/6947170665");

	//	AdWhirlTargeting.setTestMode(true);

		if(ad.isLoaded()) {
			Log.d("APP", "Ad loaded!");
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Application(this), config);


		}

	private void show() {
		runOnUiThread(new Runnable() {
			public void run() {
					AdRequest request = new AdRequest.Builder().addTestDevice("1D16BFA0D64043B8424AD3E432D2A97D").build();
			//		AdRequest request = new AdRequest.Builder().build();

					ad.loadAd(request);
					ad.show();
			}
		});
	}

	@Override
	public void showAd() {
		show();
	}

	public void toast(final String msg) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(AndroidLauncher.this, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
