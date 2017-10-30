package brendaukdw.com.samplesqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import models.MyWish;

public class AddWishActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private Button btnAddWish;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        etTitle = (EditText)findViewById(R.id.etTitle);
        etContent = (EditText)findViewById(R.id.etContent);
        btnAddWish = (Button)findViewById(R.id.btnAdd);

        btnAddWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyWish wish = new MyWish();
                wish.setTitle(etTitle.getText().toString());
                wish.setContent(etContent.getText().toString());
                db.addWish(wish);
                db.close();
            }
        });
    }


}
