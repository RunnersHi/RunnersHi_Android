## RunnersHi

### 1. ConstraintLayout 사용한 화면 개발
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
