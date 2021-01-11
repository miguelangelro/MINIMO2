package com.example.minimo2DSAMiguel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    List<Repos> listaElements = new ArrayList<>();
    RecyclerView recycler;
    RecyclerView.Adapter myAdapter;
    private ProgressBar progressBar;

    TextView repos_text;
    TextView following_text;
    TextView followers_text;
    TextView nameText;
    ImageView image;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    API api = retrofit.create((API.class));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infouseractivity);

        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        //configuracion recycler
        recycler = findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)); //como queremos los datos vertical, etc
        recycler.setHasFixedSize(true);

        repos_text = findViewById(R.id.Repos_text);
        following_text = findViewById(R.id.following);
        followers_text = findViewById(R.id.followers);
        nameText = findViewById(R.id.name);
        image = findViewById(R.id.miimagen);

        String tot;
        String tit;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tit= null;
            } else {
                tit=extras.getString("User"); //variable user del Main
            }
        } else {
            tit =(String) savedInstanceState.getSerializable("User");
        }
        tot=tit;

        progressBar.setVisibility(ProgressBar.VISIBLE);
        Call<List<Repos>> call = api.ReposInfo(tot);

        call.enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if (!response.isSuccessful()) {
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "User not found", Toast.LENGTH_SHORT);
                    toast1.show();
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    return;
                }

                progressBar.setVisibility(ProgressBar.INVISIBLE);
                listaElements = response.body(); //resspuesta del servidor parseada
                myAdapter = new MyAdapter(listaElements, UserActivity.this);
                recycler.setAdapter(myAdapter);


            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
            Toast toast1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);


        });

        progressBar.setVisibility(ProgressBar.VISIBLE);
        Call<User> call1 = api.userInfo(tot);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call1, Response<User> response) {
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(ProgressBar.INVISIBLE);
                    return;
                }
            User user = response.body();
            following_text.setText("Following: "+Integer.toString(user.getFollowing()));
            followers_text.setText("Followers: "+Integer.toString(user.getFollowers()));
            nameText.setText(user.getName());

            Picasso.get().load(user.getAvatar_url()).into(image);
                progressBar.setVisibility(ProgressBar.INVISIBLE);


            }

            @Override
            public void onFailure(Call<User> call1, Throwable t) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }
            Toast toast1 = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);


        });

    }
}
