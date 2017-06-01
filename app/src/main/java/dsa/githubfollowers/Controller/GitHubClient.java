package dsa.githubfollowers.Controller;

import java.util.List;

import dsa.githubfollowers.Modelo.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Roberto on 31/05/2017.
 */

public interface GitHubClient {

    @GET ("users/{username}")
    Call<User> getUser (@Path("username") String username);

    @GET ("users/{username}/followers")
    Call<List<User>> getFollowers (@Path("username") String username);

}
