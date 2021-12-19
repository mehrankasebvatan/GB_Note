package app.gb.note.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import app.gb.note.R;
import app.gb.note.database.DataBaseHelper;
import app.gb.note.database.DataNote;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class EditActivity extends AppCompatActivity {


    TextView toolbar;
    ImageView img1;
    EditText title, text;
    FloatingActionButton done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        title = findViewById(R.id.et_title);
        text = findViewById(R.id.et_text);
        done = findViewById(R.id.btn_done);
        toolbar = findViewById(R.id.txt_toolbar);
        toolbar.setText(R.string.edit_note);
        img1 = findViewById(R.id.img1);
        img1.setImageResource(R.drawable.icon_back);
        img1.setOnClickListener(view -> {
            startActivity(new Intent(EditActivity.this, MainActivity.class));
            finish();
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = getIntent().getStringExtra("idKey");
            title.setText(getIntent().getStringExtra("titleKey"));
            text.setText(getIntent().getStringExtra("textKey"));
            text.setMovementMethod(new ScrollingMovementMethod());

        }

        done.setOnClickListener(view -> {


        });


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditActivity.this, MainActivity.class));
        finish();

    }


}