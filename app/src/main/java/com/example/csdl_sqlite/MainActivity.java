package com.example.csdl_sqlite;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editWord,editWordMean,editID;
    Button btnXoaHetDL,btnThemTu,btnXoaTu,btnLayDL,btnSuaTu;
    ImageButton imgClose;
    ListView lvDsTu;
    ArrayAdapter<Word> adapter=null;
    List<Word> dsTu=null;
    SQLHelper db;
    Word wordSelected=new Word();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFromWidget();
        //get Instance.: csll, tạo bảng
        db=new SQLHelper(this);
        //insert data
       fakeData();
        //chuẩn bị dữ liệu
        //gán dl cho listview
        refreshDataForListView();
// Button btnXoaHetDL,btnThemTu,btnXoaTu,btnLayHetTu,btnSuaTu;
        btnXoaHetDL.setOnClickListener(MainActivity.this);
        btnThemTu.setOnClickListener(MainActivity.this);
        btnXoaTu.setOnClickListener(MainActivity.this);
        btnLayDL.setOnClickListener(MainActivity.this);
        btnSuaTu.setOnClickListener(MainActivity.this);
        imgClose.setOnClickListener(MainActivity.this);
        lvDsTu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                wordSelected=dsTu.get(arg2);
                editID.setText(wordSelected.getId()+"");
                editWord.setText(wordSelected.getWord()+"");
                editWordMean.setText(wordSelected.getMean()+"");
            }
        });
    }
    public void refreshDataForListView(){
        try {
            dsTu=db.getAllWord();
            adapter=new ArrayAdapter<Word>(this,
                    android.R.layout.simple_list_item_1,
                    dsTu);
            lvDsTu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            System.out.println(" có lỗi: "+e.toString());
        }
    }
    public void fakeData(){
        //chèn vi dụ 3 từ
        db.deleteAllWord();
        db.insertWord(new Word("book", "Sách(n), đặt chỗ(v)"));
        db.insertWord(new Word("table", "bàn(n)"));
        db.insertWord(new Word("action movie", "Phim hành động "));
    }
    public void getFromWidget(){
        editID=(EditText) findViewById(R.id.editID);
        editWord=(EditText) findViewById(R.id.editWord);
        editWordMean=(EditText) findViewById(R.id.editWordMean);
        //lấy các nút lệnh
        //btnTaoBang=(Button) findViewById(R.id.btnCreateTable);
        btnThemTu= findViewById(R.id.btnThemTu);
        btnLayDL= findViewById(R.id.btnLayAllDL);
        btnXoaTu= findViewById(R.id.btnXoaTu);
        btnSuaTu= findViewById(R.id.btnSuaTu);
        btnXoaHetDL= findViewById(R.id.btnXoaHetDL);
        imgClose=findViewById(R.id.imgClose);
        //lấy listview
        lvDsTu=(ListView) findViewById(R.id.lvWord);
        //tự sinh mã số
        editID.setEnabled(false);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnXoaHetDL:
                db.deleteAllWord();
                refreshDataForListView();
                break;
            case R.id.btnThemTu:
                them();
                break;
            case R.id.btnXoaTu:
               // xoaTu();
                break;
            case R.id.btnLayAllDL:
                refreshDataForListView();
                break;
            case R.id.btnSuaTu:
//                capNhatTu();
                break;
            case R.id.imgClose:
                finish();
                break;
        }//end of switch
    }
    public void them(){
        Word w=new Word();
        w.setWord(editWord.getText()+"");
        w.setMean(editWordMean.getText()+"");
        boolean t=db.insertWord(w);
        refreshDataForListView();
    }
//    public void capNhatTu(){
//        Word w = wordSelected;
//        w.setWord(editWord.getText()+"");
//        w.setMean(editWordMean.getText()+"");
//        db.updateWord(w);
//        Toast.makeText(this, " sửa : "+w.toString(), Toast.LENGTH_SHORT).show();
//        refreshDataForListView();
//    }
//    public void laySoDong(){
//        //loge total rows in database
//        Log.e(TAG, "onCreate: " + db.getTotalWord());
//    }
//    public void xoaTu(){
//        //delete word
//        db.deleteWord(wordSelected);
//        refreshDataForListView();
//    }

}