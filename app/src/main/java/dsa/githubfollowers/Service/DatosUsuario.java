package dsa.githubfollowers.Service;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dsa.githubfollowers.Controller.GitHubClient;
import dsa.githubfollowers.Modelo.User;
import dsa.githubfollowers.R;
import dsa.githubfollowers.Tools.ImageLoadTask;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatosUsuario extends AppCompatActivity {

    public String loginUsuario;
    public int reposUsuario;
    public int followersUsuario;
    public String avatarUsuario;
    TextView login, repos, followers;
    ImageView avatar;
    List<String> nombresFollowers = new ArrayList<String>();
    ListView listView;

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_followers);
        mProgress = (ProgressBar) findViewById(R.id.progressBar);
        mProgress.setVisibility(View.VISIBLE);

        loginUsuario = getIntent().getExtras().getString("loginUsuario");
        reposUsuario = getIntent().getExtras().getInt("reposUsuario");
        followersUsuario = getIntent().getExtras().getInt("followersUsuario");
        avatarUsuario = getIntent().getExtras().getString("avatarUsuario");

        login = (TextView) findViewById(R.id.login);
        login.setText(login.getText() + loginUsuario);

        repos = (TextView) findViewById(R.id.repos);
        repos.setText(repos.getText() + Integer.toString(reposUsuario));

        followers = (TextView) findViewById(R.id.followings);
        followers.setText(followers.getText() + Integer.toString(followersUsuario));

        listView=(ListView)findViewById(R.id.list);
        loginUsuario = getIntent().getExtras().getString("loginUsuario");

        avatar = (ImageView) findViewById(R.id.avatar);
        new ImageLoadTask(avatarUsuario, avatar).execute();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                // .baseUrl("https://translation.googleapis.com")
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GitHubClient listaFollowers = retrofit.create(GitHubClient.class);
        Call<List<User>> call = listaFollowers.getFollowers(loginUsuario);

        call.enqueue(new Callback<List<User>>() {

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> followers = response.body();

                for (User u : followers){
                    System.out.println(u.getLogin());
                    nombresFollowers.add(u.getLogin());
                }

                ArrayAdapter adapter=new ArrayAdapter(DatosUsuario.this,android.R.layout.simple_list_item_1, nombresFollowers);
                listView.setAdapter(adapter);
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }
}
