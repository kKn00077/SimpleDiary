package kn0077.kr.hs.emirim.simplediary;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatePicker date;
    EditText edit;
    Button but;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date = (DatePicker) findViewById(R.id.date_pick);
        edit = (EditText) findViewById(R.id.edit);
        but = (Button) findViewById(R.id.but);

        Calendar cal = Calendar.getInstance();//static메서드 캘린더(추상클래스)를 사용할 수 있도록 하는 현재객체 클래스의 참조값을 반환
        int year=cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH); //보여지는 값은 1~12이나, 실제 데이터 값은 0~11이므로 onDateChanged()에서 month+1을 해줌
        int day=cal.get(Calendar.DATE);

        date.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                fileName=year+"_"+(month+1)+"_"+day+".txt";
                String readData=readDiary(fileName);
                edit.setText(readData);
                but.setEnabled(true);
            }
        });

    }
    public String readDiary(String fileName){

        return null;
    }
}