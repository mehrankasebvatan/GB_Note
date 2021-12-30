package app.gb.note.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adivery.sdk.Adivery;
import com.adivery.sdk.AdiveryAdListener;
import com.adivery.sdk.AdiveryBannerAdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.valdesekamdem.library.mdtoast.MDToast;

import app.gb.note.R;
import app.gb.note.database.DataBaseHelper;
import app.gb.note.database.DataNote;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class EditActivity extends AppCompatActivity {


    TextView toolbar;
    ImageView img1;
    EditText title, text;
    FloatingActionButton done;
    DataBaseHelper myDb;
    String id = null;
    String APP_ID = "f9627c46-dc2c-4e52-963a-943a624b40a4";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        myDb = new DataBaseHelper(this);
        Adivery.configure(getApplication(), APP_ID);
        showAdd();

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
            id = getIntent().getStringExtra("idKey");
            title.setText(getIntent().getStringExtra("titleKey"));
            text.setText(getIntent().getStringExtra("textKey"));

        }

        done.setOnClickListener(view -> {

            if (id == null) {

                MDToast mdToast = MDToast.makeText(EditActivity.this, getApplicationContext().getResources().getString(R.string.edit_id_null), MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                mdToast.show();
            }
            DataNote upValue = new DataNote();
            upValue.title = title.getText().toString().trim();
            upValue.text = text.getText().toString().trim();
            boolean resultUpdate = myDb.updateNote(id, upValue);

            if (resultUpdate = true) {
                MDToast mdToast = MDToast.makeText(EditActivity.this, getApplicationContext().getResources().getString(R.string.edit_ok), MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                mdToast.show();
            } else {
                MDToast mdToast = MDToast.makeText(EditActivity.this, getApplicationContext().getResources().getString(R.string.edit_fail), MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                mdToast.show();
            }


            onBackPressed();


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

    public void showAdd() {

        AdiveryBannerAdView bannerAd = findViewById(R.id.banner_ad);

        bannerAd.setBannerAdListener(new AdiveryAdListener() {
            @Override
            public void onAdLoaded() {
                // تبلیغ به‌طور خودکار نمایش داده می‌شود، هر کار دیگری لازم است اینجا انجام دهید.
            }

            @Override
            public void onError(String reason) {
                // خطا را چاپ کنید تا از دلیل آن مطلع شوید
            }

            @Override
            public void onAdClicked() {
                // کاربر روی بنر کلیک کرده
            }
        });

        bannerAd.loadAd();

    }

}