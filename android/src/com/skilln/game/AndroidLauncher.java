package com.skilln.game;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AndroidLauncher extends AndroidApplication implements AdHandler {

	private InterstitialAd ad;

	private Handler handler = new Handler();

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MobileAds.initialize(this, "-your id-");

		ad = new InterstitialAd(this);
		ad.setAdUnitId("-your id-");

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		config.useImmersiveMode = true;

		initialize(new WayToHeaven(this), config);


		}

	private void show() {
		runOnUiThread(new Runnable() {
			public void run() {
			//		AdRequest request = new AdRequest.Builder().addTestDevice("1D16BFA0D64043B8424AD3E432D2A97D").build();
					AdRequest request = new AdRequest.Builder().build();

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
