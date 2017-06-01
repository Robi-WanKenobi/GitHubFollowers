package dsa.githubfollowers.Service;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import dsa.githubfollowers.Controller.GitHubClient;
import dsa.githubfollowers.Modelo.User;
import dsa.githubfollowers.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inicio extends AppCompatActivity {

    private EditText etUsuario;
    public String loginUsuario;
    public int reposUsuario;
    public int followersUsuario;
    public String avatarUsuario;
    String usuarioEditTextName;

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        etUsuario = (EditText) findViewById(R.id.usuario);
        mProgress = (ProgressBar) findViewById(R.id.progressBar3);
        mProgress.setVisibility(View.GONE);
    }

    public void getUsuario(View v){

        usuarioEditTextName = etUsuario.getText().toString();
        mProgress.setVisibility(View.VISIBLE);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
              Retrofit.Builder builder = new Retrofit.Builder()
                                // .baseUrl("https://translation.googleapis.com")
                                .baseUrl("https://api.github.com/")
                                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        GitHubClient usuario = retrofit.create(GitHubClient.class);
        Call<User> call = usuario.getUser(usuarioEditTextName);

        call.enqueue(new Callback() {

            //***************Comprobacion de que recoge los datos**********
            @Override
            public void onResponse(Call call, Response response) {

                if (response.isSuccessful()) {
                    User user = (User) response.body();
                    loginUsuario = user.getLogin();
                    reposUsuario = user.getPublic_repos();
                    followersUsuario = user.getFollowers();
                    avatarUsuario = user.getAvatar_url();

                    Intent intent = new Intent(Inicio.this, DatosUsuario.class);
                    intent.putExtra("loginUsuario", loginUsuario);
                    intent.putExtra("reposUsuario", reposUsuario);
                    intent.putExtra("followersUsuario", followersUsuario);
                    intent.putExtra("avatarUsuario", avatarUsuario);
                    startActivity(intent);
                    mProgress.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(Inicio.this, "El usuario es incorrecto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(Inicio.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
