package com.example.wordwizard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ScoreListAdapter extends ArrayAdapter<FillScoreList> {
    private static final String TAG = "ScoreListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView Holder1;
        TextView Holder2;
        TextView Holder3;
    }

    public ScoreListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FillScoreList> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String Holder1 = getItem(position).getHolder1();
        String Holder2 = getItem(position).getHolder2();
        String Holder3= getItem(position).getHolder3();

        //create the object to hold the score information
        FillScoreList score = new FillScoreList(Holder1,Holder2,Holder3);

        //view for the animation of the loading list
        final View result;

        //viewholder object
        ViewHolder holder;

        if(convertView ==null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.Holder1 = (TextView) convertView.findViewById(R.id.FirstText);
            holder.Holder2 = (TextView) convertView.findViewById(R.id.SecondText);
            holder.Holder3 = (TextView) convertView.findViewById(R.id.ThirdText);

            result = convertView;

            convertView.setTag(holder);
        } else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.loading_down_anim : R.anim.loading_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.Holder1.setText(Holder1);
        holder.Holder2.setText(Holder2);
        holder.Holder3.setText(Holder3);

        return convertView;
    }
}
