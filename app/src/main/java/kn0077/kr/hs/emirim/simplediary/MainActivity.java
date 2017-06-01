package kn0077.kr.hs.emirim.simplediary;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        String diaryStr=null;
        FileInputStream fIn=null;

        try {
            fIn=openFileInput(fileName);
            byte[] buf=new byte[500];
            fIn.read(buf);
            diaryStr=new String(buf).trim();
            but.setText("수정 하기");

        } catch (FileNotFoundException e) { // IOException으로 처리해주어도 상관없음
            edit.setText("일기가 존재하지 않습니다."); // 파일이 없을 때
            but.setText("새로 저장");
        } catch (IOException e) {

        }

        try {
            fIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return diaryStr;
    }
}