package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText max_text, min_text;
    private Button btn;
    private TextView text_view;
    private ListView listView;
    DataBase dbHelper;
    private ArrayAdapter<String> my_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        max_text = (EditText)findViewById(R.id.max_text);
        min_text = (EditText)findViewById(R.id.min_text);
        btn = (Button)findViewById(R.id.btn);
        text_view = (TextView)findViewById(R.id.txt_task);
        dbHelper = new DataBase(this);
        listView = (ListView)findViewById(R.id.list_view);

        dbHelper.removeAll();

        loadAllAbb();

        OnClickList();

    }




    //нажатие на кнопку
    public void OnClick (View v) {
        //первая проверка
        if (!max_text.getText().toString().equals("")) {
            //вторая проверка
            if (!min_text.getText().toString().equals("")) {
                //третья проверка на совпадение
                if (!dbHelper.EqualsDB(min_text.getText().toString())){
                    //ТУТ ПОЛОЖИТЕЛЬНАЯ КНОПКА
                    btn.setText("Готово");
                    dbHelper.insertData(max_text.getText().toString(), min_text.getText().toString());
                    loadAllAbb();
                    max_text.setText("");
                    min_text.setText("");
                }else {
                    //третье если
                    //ТУТ В СЛУЧАЕ ЕСЛИ ЕСТЬ УЖЕ КОРОТКАЯ ЗАПИСЬ
                    btn.setText("Укажите другое сокращение");
                }

            } else {
                //второе если
                //ТУТ В СЛУЧАЕ ЕСЛИ КОРОТКАЯ ССЫЛКА НЕ ВЕРНАЯ
                btn.setText("Сокращенная ссылка не верная");
            }
        } else {
            //первое если
            //ТУТ ЕСЛИ ДЛИННАЯ ССЫЛКА НЕ ВЕРНАЯ
            btn.setText("Ссылка не верная");
            //КОНЕЦ ПРОВЕРКИ КНОПКИ
        }

    }

    //вывод данных
    public void loadAllAbb(){
        ArrayList<String > abbList = dbHelper.getAllAbb();
        if(my_adapter == null) {
            my_adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.txt_task, abbList);
            listView.setAdapter(my_adapter);
        }else{
            my_adapter.clear();
            my_adapter.addAll(abbList);
            my_adapter.notifyDataSetChanged();
        }
    }

    //выделение определенного элемента листа
    private void OnClickList (){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                TextView listViewItemText = (TextView) itemClicked.findViewById(R.id.txt_task);
                String item = listViewItemText.getText().toString();
                //переход по ссылке
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + dbHelper.getItemDB(item)));
                startActivity(browserIntent);

                //удаление строк
                //dbHelper.deleteData(item);
                //loadAllAbb();

            }
        });
    }















//НЕ ТРОГАТЬ
}
