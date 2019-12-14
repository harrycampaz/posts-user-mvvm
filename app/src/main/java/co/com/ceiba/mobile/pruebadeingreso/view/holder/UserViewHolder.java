package co.com.ceiba.mobile.pruebadeingreso.view.holder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

public class UserViewHolder extends RecyclerView.ViewHolder {

    TextView name, phone, email;
    Button viewButton;

    public UserViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        phone = itemView.findViewById(R.id.phone);
        email = itemView.findViewById(R.id.email);
        viewButton = itemView.findViewById(R.id.btn_view_post);

    }

    public void bind(final User user){

        name.setText(user.getName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(itemView.getContext(), PostActivity.class);
                intent.putExtra("user", user);
                itemView.getContext().startActivity(intent);

                System.out.println("Ver publicaciones de " + user.getId());
            }
        });



    }


}
