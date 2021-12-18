package app.gb.note.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.gb.note.R;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class NoteActivity extends AppCompatActivity {


    TextView title, text, toolbar;
    ImageView img1, img2, img3, img4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = findViewById(R.id.tv_title);
        text = findViewById(R.id.tv_text);
        toolbar = findViewById(R.id.txt_toolbar);
        toolbar.setText(R.string.app_name);
        img1 = findViewById(R.id.img1);
        img1.setImageResource(R.drawable.icon_back);
        img1.setOnClickListener(view -> {
            startActivity(new Intent(NoteActivity.this, MainActivity.class));
            finish();
        });

        img4 = findViewById(R.id.img4);
        img4.setImageResource(R.drawable.icon_share);
        img4.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String share = text.getText().toString();
            intent.putExtra(Intent.EXTRA_TEXT, share);
            Intent modIntent = Intent.createChooser(intent, "share with... ");
            startActivity(modIntent);

        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = getIntent().getStringExtra("idKey");
            title.setText(getIntent().getStringExtra("titleKey"));
            text.setText(getIntent().getStringExtra("textKey"));

        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(NoteActivity.this, MainActivity.class));
        finish();

    }


}