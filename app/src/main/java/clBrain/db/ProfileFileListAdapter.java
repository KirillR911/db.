package clBrain.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by olpyh on 05.03.2018.
 */

@SuppressLint("ValidFragment")
class WatchPhoto extends DialogFragment {

    Activity activity;
    Context context;
    String url;

    public WatchPhoto(Activity activity, Context context, String url) {
        this.activity = activity;
        this.context = context;
        this.url = url;
    }

    public WatchPhoto() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View dialog;
        dialog = inflater.inflate(R.layout.show_picture, container);
        Picasso.with(context).load(url).placeholder(R.drawable.image_success)
                .error(R.drawable.image_error).fit().into((ImageView) dialog.findViewById(R.id.image));
        return dialog;
    }
}

public class ProfileFileListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<FileModel> fileModelsArray;
    Activity activity;

    ProfileFileListAdapter (Context cnt, ArrayList<FileModel> fileModels, Activity activity){
        this.fileModelsArray = fileModels;
        context = cnt;
        inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return fileModelsArray.size();
    }

    @Override
    public Object getItem(int i) {
        return fileModelsArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return fileModelsArray.get(i).getRef().hashCode();
    }



    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View nView = view;
        if (nView == null) {
            nView = inflater.inflate(R.layout.file_list_item, parent, false);
        }

        final FileModel p = getFileModel(i);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) nView.findViewById(R.id.file_item_name_of_file)).setText(p.name);
        ((ImageView) nView.findViewById(R.id.file_item_image)).setImageResource(R.drawable.ic_home_white_24dp);
        nView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlStr = p.getRef();
                new WatchPhoto(activity, context, urlStr).show(activity.getFragmentManager(), "dialogShowImage");
            }
        });
        return nView;
    }

    public FileModel getFileModel(int i){
        return fileModelsArray.get(i);
    }
}
