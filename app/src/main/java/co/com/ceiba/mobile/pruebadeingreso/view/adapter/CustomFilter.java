package co.com.ceiba.mobile.pruebadeingreso.view.adapter;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.User;

public class CustomFilter extends Filter {

    private UserAdapter adapter;
    private List<User> filterUser;

    public CustomFilter(List<User> filterUser, UserAdapter adapter) {
        this.adapter = adapter;
        this.filterUser = filterUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(charSequence != null && charSequence.length() > 0)
        {
            //CHANGE TO UPPER
            charSequence=charSequence.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<User> filteredUsers=new ArrayList<>();

            for (int i=0;i<filterUser.size();i++)
            {
                //CHECK
                if(filterUser.get(i).getName().toUpperCase().contains(charSequence))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredUsers.add(filterUser.get(i));
                }
            }

            results.count=filteredUsers.size();
            results.values=filteredUsers;
        }else
        {
            results.count=filterUser.size();
            results.values=filterUser;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        adapter.userList = (List<User>) filterResults.values;

        adapter.notifyDataSetChanged();

    }
}
