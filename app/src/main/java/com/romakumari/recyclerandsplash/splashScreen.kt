package com.romakumari.recyclerandsplash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.romakumari.recyclerandsplash.databinding.ActivityMainBinding
import com.romakumari.recyclerandsplash.databinding.ActivitySplashScreenBinding

class splashScreen : AppCompatActivity() {
    lateinit var splashScreenBinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenBinding= ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)
        Handler().postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        },2000)
    }
}
