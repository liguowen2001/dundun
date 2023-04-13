package edu.hebut.dundun.entity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xuexiang.xui.adapter.simple.ViewHolder;

import java.util.List;

import edu.hebut.dundun.R;

public class RemindTextAdapter extends ArrayAdapter<RemindText> {

    private View.OnClickListener onClickListener;

    private final String TAG = "RemindTextAdapter";

    private int resourceId;

    public RemindTextAdapter(Context context, int textViewResourceId, List<RemindText> objects, View.OnClickListener onClickListener) {
        super(context, textViewResourceId, objects);
        this.onClickListener = onClickListener;
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        RemindText remindText = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        } else {
            view = convertView;
        }
        TextView title = view.findViewById(R.id.itemRemindTextTitle);
        TextView text = view.findViewById(R.id.itemRemindText);
        ImageView delete = view.findViewById(R.id.itemRemindDelete);
        title.setText(remindText.getTitle());
        text.setText(remindText.getText());

        delete.setTag(position);
        delete.setOnClickListener(onClickListener);
        return view;
    }

}
