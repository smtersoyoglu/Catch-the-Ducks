package com.sametersoyoglu.kotlincatchtheduck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    var score = 0
    var imageArray = ArrayList<ImageView>()
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //ImageArray --  resimlerimizi görünmez yapmak için hepsini almak için array listesi yapıp alıyoruz
        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        //onCreate içerisinde çağırırız ki uygulama acıldığında direk gizlensin
        hideImages()


        // CountDownTimer
        val sayac = object : CountDownTimer(15500,1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Time: ${millisUntilFinished/1000}"

            }
            override fun onFinish() {
                timeText.text = " Time: 0"

                // süre bitince arka planda ki çalışmayı durdurmak için -- hareketi durdurmak için
                handler.removeCallbacks(runnable)
                // resimlerin rastgele görünüp devam etmesini de durdurmalıyız
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }





                // bittikten sonra uyarı mesajı verip tekrar başlamakmı cıkmakmı istiyor onu sorma
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Do you want to START OVER")
                alert.setPositiveButton("Yes"){dialog, which ->
                    //Restart -- yeniden başlatma, intent ile tekrar ekranı getireceğim finish ile kapattıktan sonra
                    val intent = intent
                    finish()
                    startActivity(intent)

                }
                alert.setNegativeButton("No") {dialog, which ->
                    Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                }
                alert.show()
            }
        }
        sayac.start()

    }

    // görselleri gizleme
    fun hideImages() {

        // rastgele ekrana gelen resimleri hareket ettirmek için runnable & handler kullanırız
        runnable = object : Runnable {
            override fun run() {

                for (image in imageArray) {
                    // bütün resimleri görünmez yapar
                    image.visibility = View.INVISIBLE
                }

                // resimleri rastgele görünür hale getirmek için
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                // kac saniyede ekrana geliceğini ayarlama
                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)

    }


    fun duckScore(view: View) {
        score ++
        scoreText.text = "Score: $score"

    }


}