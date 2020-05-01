package com.example.dynamicviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.dynamicview.DynamicViewListHolder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dynVLH : DynamicViewListHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dynVLH = DynamicViewListHolder(this, 3, arrayOf(StartButton, ProfileButton, OptionsButton, StatisticsButton, BMButton, ShopButton, puMButton))

        val clickListenerForDynViews = View.OnClickListener { view ->
            dynVLH.onClick(view as Button)

            when (view.getId()) {
                R.id.StartButton -> print(1)
                R.id.OptionsButton -> print(2)
            }
        }

        StartButton.setOnClickListener(clickListenerForDynViews)
        OptionsButton.setOnClickListener(clickListenerForDynViews)
        ProfileButton.setOnClickListener(clickListenerForDynViews)
        BMButton.setOnClickListener(clickListenerForDynViews)
        ShopButton.setOnClickListener(clickListenerForDynViews)
        StatisticsButton.setOnClickListener(clickListenerForDynViews)

        puMButton.setOnClickListener {
            dynVLH.showPopup(puMButton)
        }
        ExitButton.setOnClickListener {
            this.finish()
        }
    }

    override fun onResume() {
        dynVLH.createDynamicView(R.id.constraintLayout, true)
        super.onResume()
    }

    override fun onStop() {
        dynVLH.saveFrequency()
        super.onStop()
    }
}
