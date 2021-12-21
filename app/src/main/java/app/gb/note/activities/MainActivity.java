package app.gb.note.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import app.gb.note.R;
import app.gb.note.RecyclerAdapter;
import app.gb.note.database.DataBaseHelper;
import app.gb.note.database.DataNote;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import com.pushpole.sdk.PushPole;


public class MainActivity extends AppCompatActivity {

    TextView nothing, toolbar;
    ImageView img4;
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    DataBaseHelper myDb;
    FloatingActionButton btn_new;

    List<DataNote> dataNotes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushPole.initialize(this,false);

        myDb = new DataBaseHelper(this);
        recyclerView = findViewById(R.id.rv_main);
        btn_new = findViewById(R.id.btn_new);
        nothing = findViewById(R.id.txt_nothing);
        toolbar = findViewById(R.id.txt_toolbar);
        toolbar.setText(R.string.app_name);
        img4 = findViewById(R.id.img4);
        img4.setImageResource(R.drawable.icon_info);

        img4.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, InfoActivity.class));
            finish();

        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new RecyclerAdapter(this, dataNotes);
        recyclerView.setAdapter(adapter);


        LoadData();


        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InputActivity.class));
                finish();
            }
        });

    }

    public void LoadData() {
        Cursor cursor = myDb.getAllNotes();
        if (cursor.getCount() == 0) {
            nothing.setVisibility(View.VISIBLE);
        }
        while (cursor.moveToNext()) {
            DataNote dataNote = new DataNote();
            dataNote.id = cursor.getString(0);
            dataNote.title = cursor.getString(1);
            dataNote.text = cursor.getString(2);

            dataNotes.add(dataNote);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}