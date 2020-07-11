package com.team.runnershi

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.fragment.app.Fragment as Fragment


class HomeActivity : AppCompatActivity() {

    var permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.RECORD_AUDIO
    )

    var bottom_dialog : BottomSheetFragment = BottomSheetFragment()

    fun showDialog() {
        bottom_dialog.show(supportFragmentManager,"bottomSheet")
        bottom_dialog.showsDialog
        check_permission()

    }

//    private lateinit var bottom_sheet_behavior : BottomSheetBehavior<ConstraintLayout>
//    fun showBottomSheet() {
//        bottom_sheet_behavior.state = BottomSheetBehavior.STATE_EXPANDED
//        bottom_sheet_behavior.peekHeight
//        check_permission()
//
//    }   다른거 실험중



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        bottom_dialog.show(supportFragmentManager,"bottomSheet")
//        bottom_dialog.showsDialog

//        bottom_sheet_behavior = BottomSheetBehavior.from(bottom_sheet_layout)
//        bottom_sheet_behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//        bottom_sheet_behavior.peekHeight = 0
//        bottom_sheet_behavior.isHideable = false    다른거 실험중


//        val frag_home : Button? = HomeFragment().btn_home_runnow
//
//        frag_home!!.setOnClickListener(object : View.OnClickListener{
//            override fun onClick(p0: View?) {
//
//                val frag_dial : BottomSheetFragment = BottomSheetFragment()
//                frag_dial.showsDialog
//
//            }
//
//        })




        vp_home.adapter = HomePagerAdapter(supportFragmentManager)
        vp_home.offscreenPageLimit = 3
        vp_home.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                bottom_navigation_view.menu.getItem(position).isChecked = true
            }
        })

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.tabHome->vp_home.currentItem=0
                R.id.tabRecord->vp_home.currentItem=1
                R.id.tabRank->vp_home.currentItem=2
                R.id.tabMypage->vp_home.currentItem=3
            }
            true
        }
    }

    fun check_permission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        for(permission : String in permission_list) {
            var chk = checkCallingOrSelfPermission(permission)
            if(chk == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission_list, 0)
                break
            }
        }
    }

}