
package co.com.ceiba.mobile.pruebadeingreso.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "users_table")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")

    private Integer id;

    @ColumnInfo(name = "name")

    private String name;

    @ColumnInfo(name = "username")

    private String username;

    @ColumnInfo(name = "email")

    private String email;

    @ColumnInfo(name = "phone")

    private String phone;

    @ColumnInfo(name = "website")

    private String website;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }


}
