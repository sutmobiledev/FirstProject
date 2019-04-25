package com.example.telegram;

import android.widget.TextView;

// Your "view holder" that holds references to each subview
class ViewHolder {
    final TextView nameTextView;
    final TextView authorTextView;

    public ViewHolder(TextView nameTextView, TextView authorTextView) {
        this.nameTextView = nameTextView;
        this.authorTextView = authorTextView;
    }
}
