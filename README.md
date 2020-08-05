<div align="center" style="display:flex;">
       <img src=https://user-images.githubusercontent.com/57262833/87799675-7c048580-c888-11ea-8bbb-ec6c4e5c769e.png alt="drawing" width="600"/> 
       <img src="https://user-images.githubusercontent.com/57262833/87800520-73f91580-c889-11ea-9a2f-ef43cf60a361.png" width="300"/>
</div>

#  러닝을 게임처럼, 러너스하이

우리는 '함께' 뛸 때 더 많은 시너지를 만들어냅니다. 요즘 세대들은 러닝 크루에 소속되어 러닝을 즐기곤 하지만, Offline 러닝크루는 고정된 시간과 장소라는 한계점을 가지고 있습니다.  우리는 **같이 달리고 싶지만, 달릴 수 없는 순간들이 존재한다는 점**에 집중하고자 합니다.  우리의 서비스를 통해 시공간의 제약없이 다양한 사람들과 함께 달림으로써 건강한 라이프 스타일을 만들어나가길 바랍니다.

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



## :pig: A-1. ConstraintLayout 사용한 화면 개발

</br>

#### :point_right: activity_login.xml
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

</br>

#### :point_right: activity_sign_up.xml
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

</br>

#### :point_right: activity_on_board.xml4
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

</br>

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





## :pig: A-2. Kotlin을 이용한 프로그래밍
### :running: Kotlin Collection 확장함수 사용
- 서버와 위도, 경도 데이터를 소켓통신하기 위하여, 기존의 ArrayList를 JSONArray로 변환하는 함수
~~~
 fun getCoorsJSONArr(coordsArr: ArrayList<LatLng>): JSONArray {
        return JSONArray(coordsArr.map {
            try{
                JSONObject().apply {
                    this.put("latitude", it.latitude)
                    this.put("longitude", it.longitude)
                }
            }catch (e: JSONException){e.printStackTrace()}
        })
 }
~~~

### :running: Custom 확장함수 

#### :point_right: dpToPx.kt
- xml코드와 달리 kotlin source code에서는 px단위를 사용, dp를 px로 변환해주는 확장함수
```
fun Int.dpToPx(resources: Resources): Int = TypedValue
    .applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), resources.displayMetrics).toInt()
```
</br>

#### :point_right: logDebug.kt
- Log 쉽게 볼 수 있도록 해주는 확장함수
```
fun String.logDebug(any: Any) {
    Log.d(any::class.java.simpleName, this)
}

```
:point_right: newStartActivity.kt
- 액티비티 전환 확장함수
```
fun <T> Context.newStartActivity(toClass: Class<T>){
    val intent = Intent(this, toClass)
    startActivity(intent)
}

```


## :pig: A-3. 프로젝트 설명

### 프로젝트 사용 라이브러리
</br>
- lottie 사용, 앱 실행시 나오는 splash, 상대방 또는 자신과의 대결시 나오는 10초 카운트 및 RUN! 화면 처리를 위함. </br>
-> implementation 'com.airbnb.android:lottie:3.4.1'

- 네이버지도 SDK, RUNNER'S HI 앱의 핵심기능으로 자신의 현재위치 및 이동경로, 게임 후 결과를 보여주기 위해 필요한 지도로 네이버사의 SDK를 사용함. </br>
-> implementation 'com.naver.maps:map-sdk:3.8.0'

- Retrofit 라이브러리, 서버통신을 위함 : https://github.com/square/retrofit </br>
-> implementation 'com.squareup.retrofit2:retrofit:2.6.2'

- Retrofit 라이브러리 응답으로 가짜 객체를 만들기 위해 </br>
-> implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'

- 객체 시리얼라이즈를 위한 Gson 라이브러리 : https://github.com/google/gson </br>
-> implementation 'com.google.code.gson:gson:2.8.6'

- Retrofit 에서 Gson 을 사용하기 위한 라이브러리 </br>
-> implementation 'com.squareup.retrofit2:converter-gson:2.6.2'

- Java Scalars Converter : String이나 primitive 타입을 text/plain boides로 컨버팅 하는데 쓴다. </br>
-> implementation 'com.squareup.retrofit2:converter-scalars:2.8.0'

- 리사이클러뷰 </br>
-> implementation 'androidx.recyclerview:recyclerview:1.1.0'

- material 디자인 라이브러리 </br>
-> implementation "com.google.android.material:material:1.2.0-alpha06"

- 이미지 로딩 라이브러리 : glide </br>
-> implementation "com.github.bumptech.glide:glide:4.11.0"
   annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
   kapt "com.github.bumptech.glide:compiler:4.11.0"
   
- 동그란 이미지 커스텀 뷰 라이브러리 : https://github.com/hdodenhof/CircleImageView </br>
-> implementation 'de.hdodenhof:circleimageview:3.1.0'

- CoordinateLayout 라이브러리 </br>
-> implementation 'com.google.android.material:material:1.2.0-alpha01'
-> implementation 'androidx.legacy:legacy-support-v4:1.0.0'
-> implementation 'androidx.recyclerview:recyclerview:1.1.0'
-> testImplementation 'junit:junit:4.12'
</br>

### :running:프로젝트 구조
<img src=https://user-images.githubusercontent.com/57262833/87794364-78213500-c881-11ea-88eb-a13454fbdf83.png alt="drawing" width="300"/> 

- 패키지 설명

| package | 설명 |  
|:---:|:---|
| `data` | 프로젝트 내에서 쓰는 데이터관련 클래스 | 
| `extension` | 확장함수관련 파일 |
| `feature` | 기능별 폴더들 |
| `network` | Retrofit을 사용한 http 통신시 사용하는 인터페이스 및 클래스 | 
| `socket` | Socket.io를 사용한 소켓 통신시 사용하는 클래스 및 서비스 |
| `util` | SharePrererence 및 기타 사용 클래스 | 


- feature내 하위 패키지 설명

| feature내 하위 패키지 | 해당 액티비티 및 프레그먼트 | 설명 | 
|:---:|:---|:---|
| `badgedetail` | BadgeDetailActivity | 뱃지 상세보기 화면 | 
| `finishrun` | FinishRunActivity | 러닝완료 후 화면 |
| `home` | HomeActivity, HomeFragment, BottomSheetFragment | 홈 화면 |
| `login` | LoginActivity | 로그인 화면 |
| `matchfail` | MatchFailActivity | 매칭 실패 화면 |
| `myprofile` | MyProfileFragment | 마이프로필 화면 |
| `onboard` | OnBoardActivity | 온보딩 화면 |
| `rank` | RankFragment | 랭킹 화면 |
| `recdetail` | RecDetailActivity | 기록상세보기 화면 |
| `record_badge` | BadgeFragment, RecBadgeFragment, RecFragment | 뱃지 모아보기 화면, 기록 모아보기 화면 |
| `result` | ResultActivity | 러닝 결과 화면 |
| `run` | GoalActivity, RivalActivity, MatchProcActivity, MatchSucActivity, RunActivity | 매칭상대선택 화면, 매칭 화면, 달리기 화면 |
| `runalone` | WaitMeActivity, GoalRunMeActivity, FinishRunActivity | 나와 달리기 화면, 더미와 달리기 화면, 나와 달리기 결과 화면 |
| `runcountdown` | CountDownActivity, StartRunActivity | 달리기전 카운트 다운 화면, 달리기 시작 알림 화면 |
| `rundummy` | MatchDummyActivity | 더미데이터와 달리기 매칭 화면 |
| `signup` | SignUpActivity | 회원가입 화면 | 
| `splash` | SplashActivity | 스플래시 화면 |


### :running: 핵심기능 및 구현화면 정리

#### :fire: 스플래쉬, 온보딩, 회원가입, 로그인 화면


</br>
<div>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809124-ccceab00-c895-11ea-8dd0-be8d5585c50b.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809129-cfc99b80-c895-11ea-9729-6ed4e04e29a5.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809139-d2c48c00-c895-11ea-805c-a96e082e3669.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809146-d48e4f80-c895-11ea-8a2d-4034611cdb98.png>
<div>
</br>
</br>
<div>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809182-e7088900-c895-11ea-9e9c-278edffeedba.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87809186-e96ae300-c895-11ea-8b69-d3af2fc3d15c.png>
<div>
</br>
</br>

</br>
</br>
<div>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87810040-529f2600-c897-11ea-9ede-dbfe3a1d0c84.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87810058-56cb4380-c897-11ea-87a0-1a096e193a6e.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87810066-592d9d80-c897-11ea-84e0-e3ccc2632f63.png>
<div>

- 스플래쉬 화면에서 SharedPreferences으로 자동로그인 구현
- 회원가입 화면에서 textWatcher으로 실시간으로 정규표현식 검사, 모든 조건 만족시에만 회원가입 버튼 활성화
</br>
</br>
</br>
</br>


#### :point_right:  :fire: 홈화면, 매칭화면, 러닝화면- 다른사람과 경쟁, 나와 경쟁하기 :point_left: 핵심기능
<div>
       <img src=https://user-images.githubusercontent.com/57262833/87808693-1e2a6a80-c895-11ea-8534-65c65cad4e7b.png alt="drawing" width="150"/> 
       <img src="https://user-images.githubusercontent.com/57262833/87808723-2e424a00-c895-11ea-8bf8-b8035b5cc351.png" width="150"/>
              <img src="https://user-images.githubusercontent.com/57262833/87809035-ab6dbf00-c895-11ea-9072-77b27d10866f.png" width="150"/>
       <img src="https://user-images.githubusercontent.com/57262833/87809063-b1fc3680-c895-11ea-865b-6028a4af1a20.png" width="150"/>
              <img src="https://user-images.githubusercontent.com/57262833/87809193-f12a8780-c895-11ea-8080-2faf92af5aea.png" width="150"/>
<div>
       </br>
</br>

- 홈화면에서 BottomSheetNavigation 사용
- 매칭화면에서 Socket.io를 이용한 실시간 통신으로 사용자와 매칭함
- 러닝화면에서 Socket.io를 이용하여 서버로 러닝 기록 전송, 매칭 상대방의 러닝 기록 받음
- 러닝화면에서 NaverMap API를 이용하여 사용자의 이동거리 기록
- 러닝화면에서 실시간으로 이동시간, 러닝거리, 페이스 보여줌
- 나와 경쟁하기 화면에서 TextToSpeeach와 Timer로 음성피드백 기능 구현

#### :fire: 기록화면, 기록 자세히보기 화면
<div>
       <img src=https://user-images.githubusercontent.com/57262833/87809342-35b62300-c896-11ea-9b03-4a22b25458f0.png alt="drawing" width="150"/> 
       <img src=https://user-images.githubusercontent.com/57262833/87809345-36e75000-c896-11ea-881e-13690eeb4895.png alt="drawing" width="150"/> 
       <img src=https://user-images.githubusercontent.com/57262833/87811322-72374e00-c899-11ea-8618-a510611fc2fe.png akt="drawing" width="150"/> 
       <img src=https://user-images.githubusercontent.com/57262833/87811327-76fc0200-c899-11ea-8bc7-48b9384c8e5d.png alt="drawing" width="150"/> 
<div>

- 기록화면에서 매칭상대방과 러닝 기록 비교 가능 및 승패 여부 보여줌
- 기록자세히 보기화면에서 NaverMap사용으로 러닝 경로 불러오기 
</br>
</br>

#### :fire: 나의 러닝 기록 화면, 뱃지화면
</br>
<div>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87827843-a836fb00-c8b6-11ea-9552-9eb459b5c2bc.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87811174-32706680-c899-11ea-94e8-4ee18e71154d.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87811182-343a2a00-c899-11ea-9bb7-9afbdf41b741.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87828264-85f1ad00-c8b7-11ea-96c1-dd42b9717c36.png>
<div>
</br>
      
 - 탭 레이아웃 fragment의 좌, 우측에 위치한 러닝기록, 러닝배지 뷰이다.
 - 나의 모든 기록과 승패, 기록을 볼 수 있으며 클릭시 기록 상세보기 뷰를 볼 수 있다.
 - 러닝배지는 자신이 보유한 모든 배지를 볼 수 있으며, 배지 클릭시 배지 상세보기를 할 수 있다.

</br>
</br>

#### :fire: 랭킹, 마이 프로필 뷰
</br>
<div>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87827938-de747a80-c8b6-11ea-9dc8-a86c9fef7517.png>
<img width="150" src=https://user-images.githubusercontent.com/63635840/87827911-cac91400-c8b6-11ea-8d61-71cba39189bd.png>
<div>
</br>
       
 - 랭킹뷰에서 이달의 러너, 명예의 전당, 졌잘싸에 대한 상대방의 랭킹을 확인해볼 수 있다.
 - 마이 프로필에서 나의 레벨, 전적, 배지들을 확인할 수 있으며 아래로 스크롤하여 로그아웃 버튼을 통해 로그 아웃을 할 수 있다.
