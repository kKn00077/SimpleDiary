package kn0077.kr.hs.emirim.simplediary;

import android.content.Context;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        but.setOnClickListener(new View.OnClickListener() { // () -> 일을 시킬수있는 클래스를 매개변수로 넣어줌
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fOut= openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str=edit.getText().toString(); // getText()가 String 형이 아닌 다른 형식으로 형반환을 하기 때문에 변환
                    fOut.write(str.getBytes());
                    fOut.close();
                    Toast.makeText(MainActivity.this,"정상적으로 "+fileName+"파일이 저장되었습니다.",Toast.LENGTH_LONG).show();
                } catch (IOException e) { // IOException이 FileNotFoundException의 부모라서 한번에 처리 가능하다
                    e.printStackTrace();
                }
            }
        });

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