package app.gb.note.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import app.gb.note.R;
import app.gb.note.database.DataBaseHelper;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class InputActivity extends AppCompatActivity {

    DataBaseHelper myDb;
    EditText et_title, et_text;
    String title, text;
    SharedPreferences pref;
    FloatingActionButton btn_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        myDb = new DataBaseHelper(this);
        pref = getApplicationContext().getSharedPreferences("Mypref", MODE_PRIVATE);

        et_title = findViewById(R.id.et_title);
        et_text = findViewById(R.id.et_text);

        btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                text = et_text.getText().toString();

                if (title.length() == 0) {
                    SharedPreferences.Editor editor = pref.edit();

                    int idName = pref.getInt("name", 0);
                    idName++;
                    title = getApplicationContext().getResources().getString(R.string.new_note) + " " + idName + " : ";
                    editor.putInt("name", idName);
                    editor.apply();
                }

                if (text.length() == 0) {
                    Toast.makeText(InputActivity.this, getApplicationContext().getResources().getString(R.string.empty_text), Toast.LENGTH_SHORT).show();

                } else {
                    myDb.insertNotes(title, text);
                    startActivity(new Intent(InputActivity.this, MainActivity.class));
                    finish();
                }


            }
        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        title = et_title.getText().toString();
        text = et_text.getText().toString();

        if (title.length() == 0) {
            SharedPreferences.Editor editor = pref.edit();

            int idName = pref.getInt("name", 0);
            idName++;
            title = getApplicationContext().getResources().getString(R.string.new_note) + " " + idName + " : ";
            editor.apply();
        }

        if (text.length() == 0) {
            Toast.makeText(InputActivity.this, getApplicationContext().getResources().getString(R.string.empty_text), Toast.LENGTH_SHORT).show();

        } else {
            myDb.insertNotes(title, text);
            startActivity(new Intent(InputActivity.this, MainActivity.class));
            finish();
        }


    }
}