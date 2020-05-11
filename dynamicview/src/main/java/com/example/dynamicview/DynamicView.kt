package com.example.dynamicview

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class DynamicView(val button: Button, var usageNumber: Int): Comparable<DynamicView> {
    override fun compareTo(other: DynamicView): Int {
        return this.usageNumber.compareTo(other.usageNumber)
    }

    fun onClick(){
        usageNumber++
    }
}

class DynamicViewListHolder(private val act: Activity, private val visibleButtonNumber : Int, bArray : Array<Button>){
    private var dvList = this.createDynViewsList(bArray)

    fun onClick(button: Button) {
        for (item in dvList) {
            if (button.id == item.button.id) {
                item.onClick()
            }
        }
    }

    private fun hideRecentlyUsedButtons(){
        for (i in 0 until dvList.size-1){
            if (i >= visibleButtonNumber) {
                act.findViewById<Button>(dvList[i].button.id).visibility = View.GONE
            }
            else{
                act.findViewById<Button>(dvList[i].button.id).visibility = View.VISIBLE
            }
        }
    }

    private fun createDynViewsList(bArray : Array<Button>) : List<DynamicView>{
        val sharedPref = act.getPreferences(Context.MODE_PRIVATE)
        val dvList : MutableList<DynamicView> = mutableListOf()

        for (button in bArray){
            val dView = DynamicView(button, sharedPref.getInt(button.text.toString(),0))
            dvList.add(dView)
        }
        return dvList
    }

    fun createConstraints(constraintLayoutID: Int, dynamicButtonReSize : Boolean, marginTop : Int = 0, marginButton : Int = 0, topViewID : Int = constraintLayoutID) {
        val constraintLayout = act.findViewById<ConstraintLayout>(constraintLayoutID)
        var topView : View = constraintLayout
        if(topViewID != constraintLayoutID){
            topView = act.findViewById(topViewID)
        }
        dvList = dvList.sortedDescending()
        val displayMetrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(
            dvList[0].button.id,
            ConstraintSet.TOP,
            topView.id,
            ConstraintSet.TOP,
            marginTop
        )
        var quotient:Double = 1.0
        for (i in 0 until dvList.size-1){
            if(dynamicButtonReSize) {
                dvList[i].button.width = (width*quotient).toInt()
            }
            constraintSet.connect(
                dvList[i+1].button.id,
                ConstraintSet.TOP,
                dvList[i].button.id,
                ConstraintSet.BOTTOM,
                marginButton
            )
            quotient-=0.1
        }

        constraintSet.applyTo(constraintLayout)
        hideRecentlyUsedButtons()
    }

    fun showPopup(view: View) {
        val popup = PopupMenu(act, view)
        for (i in visibleButtonNumber until dvList.size-1){
            popup.menu.add(0, dvList[i].button.id, i, dvList[i].button.text)
        }
        popup.setOnMenuItemClickListener {
            act.findViewById<Button>(it.itemId).callOnClick()
        }
        popup.show()
        }

    fun saveFrequency(){
        val editor : SharedPreferences.Editor = act.getPreferences(Context.MODE_PRIVATE).edit()

        for (item in dvList) {
            editor.putInt(item.button.text.toString(), item.usageNumber)
        }
        editor.apply()
    }
}