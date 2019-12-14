package co.com.ceiba.mobile.pruebadeingreso.source.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import lombok.NonNull;

@Database(entities = {User.class, Post.class}, version = 2, exportSchema = false)
public abstract class StoreDatabaseHelper extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PostDao postDao();

    private static StoreDatabaseHelper instance;

    public static synchronized StoreDatabaseHelper getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StoreDatabaseHelper.class, "store_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return  instance;
    }

    private static  RoomDatabase.Callback callback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            System.out.println("DB Creada: " + db.getPath());
        }

        @Override
        public void onOpen(@android.support.annotation.NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            System.out.println("DB OPEN: " + db.getPath());
        }
    };

}
