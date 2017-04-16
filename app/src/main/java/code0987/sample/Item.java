package code0987.sample;

import android.os.Bundle;

public class Item {

    public int IconRes;
    public String Title;
    public String Content;
    public String Timestamp;

    public Item(int iconRes, String t, String c, String ts) {
        IconRes = iconRes;
        Title = t;
        Content = c;
        Timestamp = ts;
    }

    private static String KEY_ICONRES = "ires";
    private static String KEY_TITLE = "t";
    private static String KEY_CONTENT = "c";
    private static String KEY_TIMESTAMP = "ts";

    public static Bundle pack(Item n) {
        Bundle bundle = new Bundle();

        bundle.putInt(KEY_ICONRES, n.IconRes);
        bundle.putString(KEY_TITLE, n.Title);
        bundle.putString(KEY_CONTENT, n.Content);
        bundle.putString(KEY_TIMESTAMP, n.Timestamp);

        return bundle;
    }

    public static Item unpack(Bundle bundle) {
        Item n = new Item(R.drawable.ic_gesture_black, "", "", "");

        n.IconRes = bundle.getInt(KEY_ICONRES);
        n.Title = bundle.getString(KEY_TITLE);
        n.Content = bundle.getString(KEY_CONTENT);
        n.Timestamp = bundle.getString(KEY_TIMESTAMP);

        return n;
    }

}
