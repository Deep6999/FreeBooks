package com.heloo.freebooks;

import android.media.Image;
import android.view.View;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.heloo.freebooks.R;

public class mViewHolder extends RecyclerView.ViewHolder {
    ImageView bookimage;
    TextView bookname;
    View view;
    LinearLayout linearLayout2;
    public mViewHolder(@NonNull View itemView) {
        super(itemView);
        bookname = itemView.findViewById(R.id.bookname);
        view = itemView;
        linearLayout2 = itemView.findViewById(R.id.linearLayout2);
        bookimage = itemView.findViewById(R.id.bookimage);
    }
}
//public  class mViewHolder extends RecyclerView.ViewHolder {
//    ImageView bookimage;
//    TextView bookname;
//    View view;
//    public mViewHolder(@NonNull View itemView) {
//        super(itemView);
//        bookname = itemView.findViewById(R.id.bookname);
//        view = itemView;
//        bookimage = itemView.findViewById(R.id.bookimage);
//    }
//}
