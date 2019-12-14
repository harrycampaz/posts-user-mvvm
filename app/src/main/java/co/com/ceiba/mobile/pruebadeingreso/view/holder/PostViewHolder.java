package co.com.ceiba.mobile.pruebadeingreso.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder {

    TextView title, body;

    public PostViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        body = itemView.findViewById(R.id.body);
    }

    public void bind(final Post post){
        title.setText(post.getTitle());
        body.setText(post.getBody());
    }
}
