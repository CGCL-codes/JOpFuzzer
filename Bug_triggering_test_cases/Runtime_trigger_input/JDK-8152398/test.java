
import org.kohsuke.github.*;
import org.postgresql.ds.PGPoolingDataSource;
import java.io.IOException;
import java.sql.*;

public class GithubFollowers {

    public static void main(String[] args) throws IOException, SQLException {
        new GithubFollowers().run();
    }

    public void run() throws SQLException, IOException {
        PGPoolingDataSource dataSource = new PGPoolingDataSource();
        dataSource.setDatabaseName("github");

        GitHub github = GitHub.connect("username", "token");
        while (true) {

            try (Connection connection = dataSource.getConnection()) {
                Repo repo = new Repo(connection);

                try {

                    try (PreparedStatement statement = connection.prepareStatement("SELECT login FROM fetch_users WHERE fetched_time IS NULL ORDER BY created_at ASC LIMIT 100000")) {
                        ResultSet resultSet = statement.executeQuery();
                        while (resultSet.next()) {
                            String loginName = resultSet.getString(1);
                            System.out.println(loginName);
                            try {
                                process(github, repo, loginName);
                            } catch (java.io.FileNotFoundException e) {
                                System.out.println("FileNotFoundException: " + e.getMessage());
                                repo.updateFetched(loginName);
                            }
                        }
                    }
                } catch (Exception | Error e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

    }

    private void process(GitHub github, Repo repo, String loginName) throws IOException, SQLException {
        GHUser user = github.getUser(loginName);
        System.out.println("Following = " + user.getFollowingCount() + ", Followers = " + user.getFollowersCount());
        repo.saveUser(user);
        repo.saveLogin(user);

        for (PagedIterator<GHUser> i = user.listFollowers().withPageSize(100).iterator(); i.hasNext(); ) {
            for (GHUser follower : i.nextPage()) {
                repo.saveFollower(follower, user);
                repo.saveLogin(follower);
                repo.addFetchUser(follower.getLogin());
                repo.addFetchUser(follower.getLogin());
            }
        }

        PagedIterator<GHUser> i = user.listFollows().withPageSize(100).iterator();
        if (i.hasNext()) {
            for (GHUser ghUser : i.nextPage()) {
                repo.saveFollower(user, ghUser);
                repo.saveLogin(ghUser);
                repo.addFetchUser(ghUser.getLogin());
            }
        }

        repo.updateFetched(loginName);

        GHRateLimit rateLimit = github.getRateLimit();
        System.out.println(rateLimit);
    }
}
