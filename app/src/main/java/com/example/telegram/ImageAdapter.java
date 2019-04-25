package com.example.telegram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<Card> {


    public ImageAdapter(Context context, int resource, List<Card> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.linearlayout_card, null);

            final TextView nameTextView = (TextView)view.findViewById(R.id.text1);
            final TextView authorTextView = (TextView)view.findViewById(R.id.textview_book_author);
            final ViewHolder viewHolder = new ViewHolder(nameTextView, authorTextView);
            view.setTag(viewHolder);
        }
        Card card = getItem(i);

        final ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.nameTextView.setText(card.getName());
        viewHolder.authorTextView.setText(card.getAuthor());

        return view;
    }


}

