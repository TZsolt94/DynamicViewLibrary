package com.example.dynamicviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.dynamicview.DynamicViewListHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dynVLH : DynamicViewListHolder
    lateinit var dynVLH1 : DynamicViewListHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dynVLH = DynamicViewListHolder(this, 3, arrayOf(StartButton, ProfileButton, OptionsButton, StatisticsButton, BMButton, ShopButton, puMButton))
        dynVLH1 = DynamicViewListHolder(this, 3, arrayOf(StartButton1, ProfileButton1, OptionsButton1, StatisticsButton1, BMButton1, ShopButton1, puMButton1))

        val clickListenerForDynViews1 = View.OnClickListener { view ->
            dynVLH1.onClick(view as Button)

            when (view.getId()) {
                R.id.StartButton1 -> print(1)
                R.id.OptionsButton1 -> print(2)
            }
        }
        val clickListenerForDynViews = View.OnClickListener { view ->
            dynVLH.onClick(view as Button)

            when (view.getId()) {
                R.id.StartButton -> print(1)
                R.id.OptionsButton -> print(2)
            }
        }

        StartButton.setOnClickListener(clickListenerForDynViews)
        StartButton1.setOnClickListener(clickListenerForDynViews1)
        OptionsButton.setOnClickListener(clickListenerForDynViews)
        OptionsButton1.setOnClickListener(clickListenerForDynViews1)
        ProfileButton.setOnClickListener(clickListenerForDynViews)
        ProfileButton1.setOnClickListener(clickListenerForDynViews1)
        BMButton.setOnClickListener(clickListenerForDynViews)
        BMButton1.setOnClickListener(clickListenerForDynViews1)
        ShopButton.setOnClickListener(clickListenerForDynViews)
        ShopButton1.setOnClickListener(clickListenerForDynViews1)
        StatisticsButton.setOnClickListener(clickListenerForDynViews)
        StatisticsButton1.setOnClickListener(clickListenerForDynViews1)

        puMButton.setOnClickListener {
            dynVLH.showPopup(puMButton)
        }
        puMButton1.setOnClickListener {
            dynVLH1.showPopup(puMButton1)
        }
        ExitButton.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        dynVLH1.createConstraints(R.id.constraintLayout1, false)
        dynVLH.createConstraints(R.id.constraintLayout2, false)
        super.onResume()
    }

    override fun onStop() {
        dynVLH.saveFrequency()
        dynVLH1.saveFrequency()
        super.onStop()
    }
}
