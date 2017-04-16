package code0987.sample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.item_view_activity);

        Item item = Item.unpack(getIntent().getExtras());

        ImageView icon = (ImageView) findViewById(R.id.icon);
        icon.setImageDrawable(ContextCompat.getDrawable(this, item.IconRes));

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(item.Title);

        TextView content = (TextView) findViewById(R.id.content);
        content.setText(item.Content);

        findViewById(R.id.fab_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.fab_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

}
