package dsa.githubfollowers.Modelo;

/**
 * Created by Roberto on 31/05/2017.
 */

public class User {

    private String login;
    private String avatar_url;
    private int followers;
    private int following;
    private int public_repos;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }

    public int getPublic_repos() {
        return public_repos;
    }
}
