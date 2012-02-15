
package com.gsbina.android.adot4j4a.adapter;

import java.util.List;

import twitter4j.Status;
import twitter4j.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsbina.android.adot4j4a.R;
import com.gsbina.android.adot4j4a.TwitterStatus;
import com.gsbina.android.adot4j4a.R.id;
import com.gsbina.android.utils.ImageUtil;

public class TimelineAdapter extends ArrayAdapter<TwitterStatus> {

    private LayoutInflater mInflater;
    private int mLayoutId;

    public TimelineAdapter(Context context, int layoutId,
            List<TwitterStatus> timeLine) {
        super(context, layoutId, timeLine);
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // ÉrÉÖÅ[ÇéÛÇØéÊÇÈ
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(mLayoutId, null);

            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView screenName = (TextView) view.findViewById(R.id.screen_name);
            TextView tweet = (TextView) view.findViewById(R.id.tweet);
            TextView date = (TextView) view.findViewById(R.id.date);

            holder = new ViewHolder();
            holder.icon = icon;
            holder.name = name;
            holder.screenName = screenName;
            holder.tweet = tweet;
            holder.date = date;

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TwitterStatus twitterStatus = getItem(position);
        Status status = twitterStatus.getStatus();
        User user = status.getUser();
        holder.icon.setImageBitmap(ImageUtil.byteArrayToBitmap(twitterStatus.getImage()));
        holder.name.setText(String.valueOf(user.getName()));
        holder.screenName.setText(user.getScreenName());
        holder.tweet.setText(status.getText());
        holder.date.setText(status.getCreatedAt().toString());

        return view;
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
        TextView screenName;
        TextView tweet;
        TextView date;
    }

}
