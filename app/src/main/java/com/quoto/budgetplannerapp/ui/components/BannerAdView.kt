// BannerAdView.kt
package com.quoto.budgetplannerapp.ui.components

import android.util.Log
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAdView(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        MobileAds.initialize(context)
    }

    AndroidView(
        factory = {
            val adView = AdView(it)
            adView.setAdSize(AdSize.BANNER)
            adView.setAdUnitId("ca-app-pub-3294909292598214/2448866020") // ✅ Correct setter
//            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111") // testing id
            adView.adListener = object : AdListener() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.e("AdMob", "Ad failed: ${error.message}")
                }

                override fun onAdLoaded() {
                    Log.d("AdMob", "Ad loaded!")
                }
            }

            adView.loadAd(AdRequest.Builder().build())
            adView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            adView
        },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)

    )

}

