<div align="center" style="display:flex;">
       <img src="https://user-images.githubusercontent.com/57262833/86694398-8764df00-c046-11ea-8afb-5b8b7087dc1b.png">
</div>

<div align="center">
    🏃‍♀️ RUNNER'S HI_ANDRIOD  🏃‍♂️
<br> 러닝을 게임처럼, 러너스하이
</div>

---
우리는 '함께' 뛸 때 더 많은 시너지를 만들어냅니다.  
요즘 세대들은 러닝 크루에 소속되어 러닝을 즐기곤 하지만, Offline 러닝크루는 고정된 시간과 장소라는 한계점을 가지고 있습니다.  
우리는 **같이 달리고 싶지만, 달릴 수 없는 순간들이 존재한다는 점**에 집중하고자 합니다.  
우리의 서비스를 통해 시공간의 제약없이 다양한 사람들과 함께 달림으로써 건강한 라이프 스타일을 만들어나가길 바랍니다.   

```
✨  기존 러닝 어플리케이션과의 확실한 차별화 요소를 적용했습니다.    

    사용자는 '실시간 경쟁'이라는 게이미피케이션 요소를 통해 지루한 러닝이 아닌, 게임처럼 유쾌한 러닝을 즐길 수 있습니다.
```

```
✨  '따로, 또 같이' 함께 하는 가치를  추구하고자 합니다.  

    어떤 단체에 소속되지 않아도, 굳이 친구와 약속을 잡고 만나지 않아도, 사용자는 언제 어디서든 나와 함께 뛸 상대를 찾아 같이 러닝을 즐길 수 있습니다.
```

```
✨  트렌디한 디자인적 요소를 통해 시각적인 즐거움을 얻을 수 있습니다.  

    특정 목표를 달성하면 개성있는 여러 가지 뱃지를 획득할 수 있음과 동시에 다양한 러너 랭킹을 통해 사용자의 즐거움을 도모하고자 합니다. 또, 누구나 사용하기 쉬운 UX 요소는 즐거움을 배로 향상시켜 줄거에요.
```

</br>
</br>

### A-1. ConstraintLayout 사용한 화면 개발

:point_right: activity_login.xml
- activity_login.xml의 뷰들을 가운데 정렬하기 위해 guideline으로 g_left,g_right를 사용했다
- edt_login_id, edt_login_pw, btn_login_confirm에 0dp로 match_constraint속성을 적용하여 guideline 범위내에 꽉차게 맞췄다.
- 아이디/비밀번호 버튼인 tv_login_sign_find는 layout_constraintStart_toEndOf="@id/tv_login_sign_up"으로 회원가입 버튼과 연관성있는 제약조건을 걸어주었다.

```

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
 
```
/br

:point_right: activity_sign_up.xml
- activity_sign_up.xml내의 뷰들을 가운데로 정렬하기 위해 margin_vertical 대신 guideLine인 g_left,g_right를 사용했다.
- edt_sign_up_id,edt_sign_up_nick_name,edt_sign_up_pw,edt_sign_up_pw_confirm에 android:layout_width="0dp"으로 guideLine에 꽉차도록 match_constraint속성을 걸어주었다.
- 중복확인버튼인 btn_sign_up_id_confirm에 layout_constraintTop_toTopOf="@+id/edt_sign_up_id" 속성을 주어 아이디입력칸과 연관성있는 제약조건을 걸어주었다.
- 중복확인버튼인 btn_sign_up_nick_name_confirm에 layout_constraintTop_toTopOf="@+id/edt_sign_up_nick_name" 속성을 주어 닉네임입력칸과 연관성있는 제약조건을 걸어주었다.


```
<?xml version="1.0" encoding="utf-8"?>



        <androidx.constraintlayout.widget.Guideline
            android:id="@+id/g_left"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="30dp"
            android:orientation="vertical"
            app:layout_constraintGuide_begin="30dp"
            app:layout_constraintStart_toStartOf="parent" />

        <androidx.constraintlayout.widget.Guideline
            android:id="@+id/g_right"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            app:layout_constraintGuide_end="30dp" />
```
/br

:point_right: activity_on_board.xml4
- 화면의 중앙에 위치한 4단계로 분류된 온보딩 뷰를 좌, 우로 넘겨보기 위해 ViewPager를 활용하여 각 화면들을 fragment로 구성했다.
- Fragment가 들어갈 ViewPager를 dot indicator가 있는 상단의 ConstraintLayout과 textView Button이 있는 하단 ConstraintLayout와 연동한뒤, layout_width는 match_parent로, layout_height는 0dp로 줌으로서 match_contraint를 준다.

```
<androidx.viewpager.widget.ViewPager
        android:id="@+id/vp_on_board"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_marginTop="26dp"
        app:layout_constraintBottom_toTopOf="@+id/constraint_id"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/indicator_on_board"
        app:layout_constraintVertical_bias="1.0" />
```

/br

:point_right: fragment_my_profile.xml4
- 마이 프로필의 프로필 사진의 경우 제플린에 올린 이미지의 기본값(wrap_content)과 적용값의 차이가 있어 적용값을 기입하여 뷰를 만들었다.
```
<ImageView
            android:id="@+id/imgv_my_profile_img"
            android:layout_width="132dp"
            android:layout_height="132dp"
            android:layout_marginTop="2dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/imgv_my_profile_setting"
            app:srcCompat="@drawable/icon_redman_shorthair" />
```

