package com.almacen.alamacen202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifImageView;

public class ActivitySplash extends AppCompatActivity {
    GifImageView imagegf;
    ImageView imgVi;
    String StrServer;
    LinearLayout Conten;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        Conten = findViewById(R.id.fondoSplash);
        imgVi = findViewById(R.id.imageSplash);
        imagegf = findViewById(R.id.gifImageView);
        StrServer = preference.getString("Server", "null");


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.jacve)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "sprautomotive.servehttp.com:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.vipla)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "cecra.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.cecra)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "guvi.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.guvi)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "cedistabasco.ddns.net:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.pressa)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "autodis.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.autodis)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9090":

                Conten.setBackgroundColor(Color.rgb(4, 59, 114));
                Picasso.with(getApplicationContext()).
                        load(R.drawable.roda)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9095":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.partech)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9080":


                Picasso.with(getApplicationContext()).
                        load(R.drawable.shark)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "vazlocolombia.dyndns.org:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.bhp)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);


                break;
            default:
                Picasso.with(getApplicationContext()).
                        load(R.drawable.logokepler)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent ScreenFir = new Intent(ActivitySplash.this, ActivityMenu.class);
                overridePendingTransition(0, 0);
                startActivity(ScreenFir);
                overridePendingTransition(0, 0);
                finish();

            }
        }, 5000);
    }
}