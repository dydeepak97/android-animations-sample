package code0987.sample;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityOne.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                            MainActivity.this,
                            findViewById(R.id.action_image),
                            "action_image").toBundle();
                    startActivity(intent, options);
                } else {
                    startActivity(intent);
                }
            }
        });

        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        RecyclerView itemsRecyclerView = (RecyclerView) findViewById(R.id.itemsRecyclerView);
        itemsRecyclerView.setAdapter(adapter);

        ArrayList<Item> items = new ArrayList<>();

        items.add(0, new Item(R.drawable.ic_insert_emoticon_black, "Item 1", "Item content 1", "Today"));
        items.add(0, new Item(R.drawable.ic_face_black, "Item 2", "Item content 2", "Yesterday"));
        items.add(0, new Item(R.drawable.ic_favorite_black, "Item 3", "Item content 3", "Century ago"));

        adapter.setData(items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<Item> data;

        public RecyclerViewAdapter() {
            data = new ArrayList<>();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.item_view, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Item item = data.get(position);
            final View v = holder.view;

            // Bind data to view here!

            ImageView icon = (ImageView) v.findViewById(R.id.icon);
            icon.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, item.IconRes));

            TextView title = (TextView) v.findViewById(R.id.title);
            title.setText(item.Title);

            TextView content = (TextView) v.findViewById(R.id.content);
            content.setText(item.Content);

            TextView timestamp = (TextView) v.findViewById(R.id.timestamp);
            timestamp.setText(item.Timestamp);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ItemViewActivity.class);
                    intent.putExtras(Item.pack(item));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Bundle options = ActivityOptions.makeSceneTransitionAnimation(
                                MainActivity.this,
                                v.findViewById(R.id.icon),
                                "action_image").toBundle();
                        startActivity(intent, options);
                    } else {
                        startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setData(List<Item> d) {
            data.clear();
            data.addAll(d);
            notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
        }

    }

}
