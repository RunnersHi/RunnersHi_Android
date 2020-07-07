## RunnersHi

### A-1. ConstraintLayout 사용한 화면 개발

:point_right: activity_login.xml
- activity_login.xml의 뷰들을 가운데 정렬하기 위해 guideline으로 g_left,g_right를 사용했다. edt_login_id, edt_login_pw, btn_login_confirm에 0dp로 match_constraint속성을 적용하여 guideline 범위내에 꽉차게 맞췄다.
-아이디/비밀번호 버튼인 tv_login_sign_find는 layout_constraintStart_toEndOf="@id/tv_login_sign_up"으로 회원가입 버튼과 연관성있는 제약조건을 걸어주었다.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".LoginActivity">

    <ImageView
        android:id="@+id/imgv_login_logo"
        android:layout_width="96dp"
        android:layout_height="96dp"
        android:layout_marginTop="146dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@drawable/emblem_rh" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/g_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_begin="54dp" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/g_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintGuide_end="54dp" />

    <EditText
        android:id="@+id/edt_login_id"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="30dp"
        android:background="@drawable/grey_round_background"
        android:paddingVertical="18dp"
        android:paddingLeft="16dp"
        android:hint="아이디"
        android:singleLine="true"
        android:textColor="@color/brown_grey"
        app:layout_constraintEnd_toStartOf="@+id/g_right"
        app:layout_constraintStart_toStartOf="@+id/g_left"
        app:layout_constraintTop_toBottomOf="@+id/imgv_login_logo" />

    <EditText
        android:id="@+id/edt_login_pw"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginTop="12dp"
        android:background="@drawable/grey_round_background"
        android:paddingVertical="18dp"
        android:paddingLeft="16dp"
        android:hint ="비밀번호"
        android:singleLine="true"
        android:inputType="textPassword"
        android:textColor="@color/brown_grey"
        app:layout_constraintEnd_toStartOf="@+id/g_right"
        app:layout_constraintStart_toStartOf="@+id/g_left"
        app:layout_constraintTop_toBottomOf="@+id/edt_login_id" />

    <TextView
        android:id="@+id/tv_login_error"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:lineSpacingExtra="12sp"
        android:layout_marginTop="12dp"
        android:text="아이디 혹은 비밀번호가 일치하지 않습니다."
        android:textColor="@color/grapefruit"
        android:textSize="12sp"
        android:visibility="invisible"
        app:layout_constraintEnd_toStartOf="@+id/g_right"
        app:layout_constraintStart_toStartOf="@+id/g_left"
        app:layout_constraintTop_toBottomOf="@+id/edt_login_pw" />

    <TextView
        android:id="@+id/btn_login_confirm"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:textColor="@color/white"
        android:lineSpacingExtra="9sp"
        android:gravity="center_horizontal"
        android:text="로그인"
        android:layout_marginTop="100dp"
        android:background="@drawable/blue_btn_background"
        android:paddingHorizontal="98dp"
        android:paddingVertical="16dp"
        app:layout_constraintEnd_toEndOf="@id/g_right"
        app:layout_constraintStart_toStartOf="@id/g_left"
        app:layout_constraintTop_toBottomOf="@id/edt_login_pw" />

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="22dp"
        app:layout_constraintEnd_toStartOf="@+id/g_right"
        app:layout_constraintStart_toStartOf="@id/g_left"
        app:layout_constraintTop_toBottomOf="@id/btn_login_confirm">

        <TextView
            android:id="@+id/tv_login_sign_up"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:lineSpacingExtra="12sp"
            android:text="회원가입"
            android:textColor="@color/black"
            android:textSize="12sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tv_login_find"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="30dp"
            android:lineSpacingExtra="12sp"
            android:text="아이디/비밀번호 찾기"
            android:textColor="@color/black"
            android:textSize="12sp"
            app:layout_constraintStart_toEndOf="@id/tv_login_sign_up"
            app:layout_constraintTop_toTopOf="parent" />
    </androidx.constraintlayout.widget.ConstraintLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
---

:point_right: activity_goal.xml
- activity_goal.xml 버튼 정렬을 맞추기 위해 guideline 2개 사용 

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingTop="40dp"
    tools:context=".GoalActivity">

    <ImageView
        android:id="@+id/btn_goal_back"
        android:layout_width="48dp"
        android:layout_height="48dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/tv_goal_title"
        style="@style/TextStyle3"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="64dp"
        android:text="오늘의 러닝 목표시간은?"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/btn_goal_back" />


    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/gl_goal_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_begin="53dp" />

    <TextView
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="79dp"
        android:background="@drawable/bg_btn_goal"
        android:text="30min"
        app:layout_constraintEnd_toEndOf="@id/gl_goal_right"
        app:layout_constraintStart_toStartOf="@id/gl_goal_left"
        app:layout_constraintTop_toBottomOf="@id/tv_goal_title" />

    <TextView
        android:id="@+id/btn_goal_30"
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="79dp"
        android:background="@drawable/bg_btn_goal"
        android:gravity="center"
        android:lineSpacingExtra="6sp"
        android:text="30min"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/gl_goal_right"
        app:layout_constraintStart_toStartOf="@id/gl_goal_left"
        app:layout_constraintTop_toBottomOf="@id/tv_goal_title" />

    <TextView
        android:id="@+id/btn_goal_45"
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="8dp"
        android:background="@drawable/bg_btn_goal"
        android:gravity="center"
        android:lineSpacingExtra="6sp"
        android:text="45min"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/btn_goal_30"
        app:layout_constraintStart_toStartOf="@id/btn_goal_30"
        app:layout_constraintTop_toBottomOf="@id/btn_goal_30" />

    <TextView
        android:id="@+id/btn_goal_60"
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="8dp"
        android:background="@drawable/bg_btn_goal"
        android:gravity="center"
        android:lineSpacingExtra="6sp"
        android:text="1hour"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/btn_goal_45"
        app:layout_constraintStart_toStartOf="@id/btn_goal_45"
        app:layout_constraintTop_toBottomOf="@id/btn_goal_45" />

    <TextView
        android:id="@+id/btn_goal_90"
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="8dp"
        android:background="@drawable/bg_btn_goal"
        android:gravity="center"
        android:lineSpacingExtra="6sp"
        android:text="1h 30min"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintEnd_toEndOf="@id/btn_goal_60"
        app:layout_constraintStart_toStartOf="@id/btn_goal_60"
        app:layout_constraintTop_toBottomOf="@id/btn_goal_60" />

    <TextView
        android:id="@+id/btn_goal_next"
        android:layout_width="0dp"
        android:layout_height="48dp"
        android:layout_marginTop="69dp"
        android:background="@drawable/bg_btn_goal_next"
        android:gravity="center"
        android:lineSpacingExtra="9sp"
        android:text="NEXT"
        android:textColor="@color/white"
        android:textSize="16sp"
        app:layout_constraintEnd_toEndOf="@id/btn_goal_90"
        app:layout_constraintStart_toStartOf="@id/btn_goal_90"
        app:layout_constraintTop_toBottomOf="@id/btn_goal_90" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/gl_goal_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_end="53dp" />


</androidx.constraintlayout.widget.ConstraintLayout>
```


:point_right: activity_result.xml

- tvResultTitle의 경우 View의 최상단에 위치하므로 parent에 Constraint, marginTop 적용
- 그 이하의 요소들은 주변의 요소들과 제약조건을 맺음
- imgvResultProfile 이미지의 경우 해당 이미지보다 큰 요소들이 있어 크기의 기준이 되지않아 디자이너가 제플린에 올려준 크기대로 설정함.
- View 내에서 가장 큰 요소(이미지뷰, 레이아웃)를 기준으로 최상단의 ConstraintLayout의 padding값을 조절하여 초기 View 구도를 설계함. 

```

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="20dp"
    tools:context=".ResultActivity">

    <TextView
        android:id="@+id/tvResultTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="40dp"
        android:text="WINNER"
        android:textColor="@color/lightish_blue"
        android:textSize="40sp"
        android:textStyle="bold|italic"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <ImageView
        android:id="@+id/imgvResultProfile"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_marginTop="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvResultTitle"
        app:srcCompat="@drawable/smile" />

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/constraintLayout2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="40dp"
        android:background="@drawable/layout_lightgreen"
        android:padding="20dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/imgvResultProfile">

        <TextView
            android:id="@+id/tvResultDistData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="4.43 km"
            android:textColor="@color/black"
            android:textSize="35sp"
            android:textStyle="bold|italic"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tvResultDistTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:text="러닝거리"
            android:textColor="@color/black"
            android:textSize="15sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultDistData" />

        <TextView
            android:id="@+id/tvResultPaceData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:text="8'22&quot;"
            android:layout_marginStart="45dp"
            android:textColor="@color/black"
            android:textSize="30sp"
            android:textStyle="bold|italic"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultDistTitle" />

        <TextView
            android:id="@+id/tvResultPaceTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="45dp"
            android:layout_marginTop="5dp"
            android:text="평균 페이스"
            android:textColor="@color/black"
            android:textSize="12sp"
            app:layout_constraintEnd_toEndOf="@+id/tvResultPaceData"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultPaceData" />

        <TextView
            android:id="@+id/tvResultTimeData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:layout_marginEnd="45dp"
            android:text="30:00"
            android:textColor="@color/black"
            android:textSize="30sp"
            android:textStyle="bold|italic"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultDistTitle" />

        <TextView
            android:id="@+id/tvResultRunTimeTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="5dp"
            android:layout_marginEnd="45dp"
            android:text="러닝시간"
            android:textColor="@color/black"
            android:textSize="12sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="@+id/tvResultTimeData"
            app:layout_constraintTop_toBottomOf="@+id/tvResultTimeData" />
    </androidx.constraintlayout.widget.ConstraintLayout>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/constraintLayout3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:background="@drawable/layout_grey"
        android:paddingHorizontal="15dp"
        android:paddingVertical="12dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.4"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/constraintLayout2">


        <TextView
            android:id="@+id/tvResultRivalRec"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="성북천치타의 기록"
            android:textColor="@color/black"
            android:textSize="13sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

        <TextView
            android:id="@+id/tvResultRivalDistData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:text="4.23 km"
            android:textColor="@color/black"
            android:textSize="25sp"
            android:textStyle="bold|italic"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalRec" />

        <TextView
            android:id="@+id/tvResultRivalDistTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="러닝 거리"
            android:textSize="13sp"
            app:layout_constraintEnd_toEndOf="@+id/tvResultRivalDistData"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalDistData" />

        <TextView
            android:id="@+id/tvResultRivalPaceData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:text="6'88&quot;"
            android:textColor="@color/black"
            android:textSize="25sp"
            android:textStyle="bold|italic"
            app:layout_constraintEnd_toStartOf="@+id/tvResultRivalTimeData"
            app:layout_constraintStart_toEndOf="@+id/tvResultRivalDistData"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalRec" />

        <TextView
            android:id="@+id/tvResultRivalPaceTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="페이스"
            android:textSize="13sp"
            app:layout_constraintEnd_toEndOf="@+id/tvResultRivalPaceData"
            app:layout_constraintStart_toStartOf="@+id/tvResultRivalPaceData"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalPaceData" />

        <TextView
            android:id="@+id/tvResultRivalTimeData"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:text="30:00"
            android:textColor="@color/black"
            android:textSize="25sp"
            android:textStyle="bold|italic"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalRec" />

        <TextView
            android:id="@+id/tvResultRivalTimeTitle"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="러닝 시간"
            android:textSize="13sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="@+id/tvResultRivalTimeData"
            app:layout_constraintTop_toBottomOf="@+id/tvResultRivalTimeData" />
    </androidx.constraintlayout.widget.ConstraintLayout>

    <Button
        android:id="@+id/btnDetailRec"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="15dp"
        android:background="@drawable/btn_next"
        android:text="기록 자세히 보기"
        android:textStyle="bold"
        android:textSize="15sp"
        android:textColor="@color/white"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/constraintLayout3"/>
</androidx.constraintlayout.widget.ConstraintLayout>


```


:point_right: activity_rec_detail.xml

- activity_result.xml와 거의 유사하게 코딩함.
- View의 최하단러닝시간, 페이스, 러닝거리 textView는 3개가 나열되어있으므로 가운데에 있는 tvRecDetailRivalPaceData는 양 옆에 있는 
textView와 제약조건을 맺고, tvRecDetailRivalPaceTitle는 tvRecDetailRivalPaceData 하단에 위치하면서 양 옆의 textView의 가운데에 위치해야 하므로
상단의 tvRecDetailRivalPaceData와 제약조건을 맺음.

```
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingVertical="20dp"
    android:background="@color/grey"
    tools:context=".RecDetailActivity">

    <ImageView
        android:id="@+id/btnRecDetailBack"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="?attr/actionModeCloseDrawable" />

    <TextView
        android:id="@+id/tvRecDetailDate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginTop="10dp"
        android:text="6월 17일의 러닝"
        android:textColor="@color/black"
        android:textSize="18sp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/btnRecDetailBack" />

    <TextView
        android:id="@+id/tvRecDetailTime"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="20dp"
        android:layout_marginTop="7dp"
        android:text="오후 6:18 - 6:28"
        android:textColor="@color/black"
        android:textSize="14sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvRecDetailDate" />

    <com.google.android.gms.maps.MapView
        android:id="@+id/mapView"
        android:layout_width="match_parent"
        android:layout_height="240dp"
        android:layout_marginTop="8dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvRecDetailTime" />

    <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/constraintLayout"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:paddingVertical="15dp"
        android:paddingHorizontal="25dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/mapView">


        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/constraintLayout4"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="13dp"
            android:background="@drawable/box_blue"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <TextView
                android:id="@+id/tvRecDetailDistData"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:text="4.43 km"
                android:textColor="@color/black"
                android:textSize="35sp"
                android:textStyle="bold|italic"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <TextView
                android:id="@+id/tvRecDetailDistTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="5dp"
                android:text="러닝거리"
                android:textColor="@color/black"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailDistData" />

            <TextView
                android:id="@+id/tvRecDetailPaceData"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="20dp"
                android:text="8'22&quot;"
                android:layout_marginStart="45dp"
                android:textColor="@color/black"
                android:textSize="30sp"
                android:textStyle="bold|italic"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailDistTitle" />

            <TextView
                android:id="@+id/tvRecDetailPace"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="45dp"
                android:layout_marginTop="5dp"
                android:text="페이스"
                android:textColor="@color/black"
                android:textSize="12sp"
                app:layout_constraintEnd_toEndOf="@+id/tvRecDetailPaceData"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailPaceData" />

            <TextView
                android:id="@+id/tvRecDetailTimeData"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="20dp"
                android:layout_marginEnd="45dp"
                android:text="30:00"
                android:textColor="@color/black"
                android:textSize="30sp"
                android:textStyle="bold|italic"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailDistTitle" />

            <TextView
                android:id="@+id/tvRecDetailTime"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="5dp"
                android:layout_marginEnd="45dp"
                android:text="러닝시간"
                android:textColor="@color/black"
                android:textSize="12sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="@+id/tvRecDetailTimeData"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailTimeData"
                tools:ignore="DuplicateIds" />




        </androidx.constraintlayout.widget.ConstraintLayout>


        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="15dp"
            android:paddingHorizontal="15dp"
            android:paddingVertical="12dp"
            android:background="@drawable/box_white"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/constraintLayout4">


            <TextView
                android:id="@+id/tvRecDetailRivalRecord"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="성북천치타의 기록"
                android:textColor="@color/black"
                android:textSize="13sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <TextView
                android:id="@+id/tvRecDetailRivalDistDatad"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="4.23 km"
                android:textColor="@color/black"
                android:textSize="25sp"
                android:textStyle="bold|italic"
                app:layout_constraintEnd_toEndOf="@+id/tvRecDetailRivalRecord"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalRecord" />

            <TextView
                android:id="@+id/tvRecDetailRivalDiscTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="러닝 거리"
                android:textSize="13sp"
                app:layout_constraintEnd_toEndOf="@+id/tvRecDetailRivalDistDatad"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalDistDatad" />

            <TextView
                android:id="@+id/tvRecDetailRivalPaceData"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="00'00&quot;"
                android:textColor="@color/black"
                android:textSize="25sp"
                android:textStyle="bold|italic"
                app:layout_constraintEnd_toStartOf="@+id/tvRecDetailRivalTimeData"
                app:layout_constraintStart_toEndOf="@+id/tvRecDetailRivalDistDatad"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalRecord" />

            <TextView
                android:id="@+id/tvRecDetailRivalPaceTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="페이스"
                android:textSize="13sp"
                app:layout_constraintEnd_toEndOf="@+id/tvRecDetailRivalPaceData"
                app:layout_constraintStart_toStartOf="@+id/tvRecDetailRivalPaceData"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalPaceData" />

            <TextView
                android:id="@+id/tvRecDetailRivalTimeData"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="15dp"
                android:text="30:00"
                android:textColor="@color/black"
                android:textSize="25sp"
                android:textStyle="bold|italic"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalRecord" />

            <TextView
                android:id="@+id/tvRecDetailRivalTimeTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="러닝 시간"
                android:textSize="13sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/tvRecDetailRivalTimeData" />
        </androidx.constraintlayout.widget.ConstraintLayout>

    </androidx.constraintlayout.widget.ConstraintLayout>
</androidx.constraintlayout.widget.ConstraintLayout>

```

