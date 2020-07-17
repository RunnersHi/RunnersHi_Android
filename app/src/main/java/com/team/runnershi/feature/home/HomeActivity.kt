package com.team.runnershi.feature.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.team.runnershi.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    val multiplePermissionsCode = 100

    var permission_list = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.WAKE_LOCK
    )

    var bottom_dialog : BottomSheetFragment =
        BottomSheetFragment()

    fun showDialog() {
        bottom_dialog.show(supportFragmentManager,"bottomSheet")
        bottom_dialog.showsDialog
        check_permission()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        vp_home.adapter =
            HomePagerAdapter(
                supportFragmentManager
            )
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
                R.id.tabHome ->vp_home.currentItem=0
                R.id.tabRecord ->vp_home.currentItem=1
                R.id.tabRank ->vp_home.currentItem=2
                R.id.tabMypage ->vp_home.currentItem=3
            }
            true
        }
    }

    //권한 확인 및 요청
    fun check_permission() {

        var rejectPermissionList = ArrayList<String>()

        for(permission in permission_list) {
//            var chk = checkCallingOrSelfPermission(permission)
//            if(chk == PackageManager.PERMISSION_DENIED) {
//                requestPermissions(permission_list, 0)
//                break
//            }
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectPermissionList.add(permission)
                bottom_dialog.dismiss()
            }
//            else if(ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
//                bottom_dialog.show(supportFragmentManager,"bottomSheet")
//            }

        }
        if(rejectPermissionList.isNotEmpty()) {
            val array = arrayOfNulls<String>(rejectPermissionList.size)
            ActivityCompat.requestPermissions(this, rejectPermissionList.toArray(array), multiplePermissionsCode)
        }
    }

}