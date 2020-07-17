## 핵심기능 및 구현화면 정리

### 스플래쉬, 온보딩, 회원가입, 로그인 화면
- 스플래쉬 화면에서 SharedPreferences으로 자동로그인 구현
- 회원가입 화면에서 textWatcher으로 실시간으로 정규표현식 검사, 모든 조건 만족시에만 회원가입 버튼 활성화\


### 홈화면, 매칭화면, 러닝화면- 다른사람과 경쟁, 나와 경쟁하기 :point_left: 핵심기능

- 홈화면에서 BottomSheetNavigation 사용
- 매칭화면에서 Socket통신으로 사용자 매칭
- 러닝화면에서 Socket통신
- 러닝화면에서 NaverMap 사용으로 지도 기능 구현
- 나와 경쟁하기 화면에서 TextToSpeeach와 Timer로 음성피드백 기능 구현

### 기록화면, 기록 자세히보기 화면

- 기록자세히 보기화면에서 NaverMap사용으로 러닝 경로 불러오기 

### 뱃지화면, 마이페이지 화면


<br>
<br>


### A-2. custom 확장 함수

:point_right: dpToPx.kt
- xml코드와 달리 kotlin source code에서는 px단위를 사용, dp를 px로 변환해주는 확장함수
```
fun Int.dpToPx(resources: Resources): Int = TypedValue
    .applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(), resources.displayMetrics).toInt()
```

:point_right: logDebug.kt
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
