package com.emarsys.mobileengage.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.emarsys.mobileengage.inbox.model.Notification;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(LinearLayout root) {
            super(root);
            mTextView = (TextView) root.findViewById(R.id.adapterNotificationTitle);
        }
    }

    List<Notification> notifications;

    public NotificationListAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout root = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification_list_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(NotificationListAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(notifications.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
