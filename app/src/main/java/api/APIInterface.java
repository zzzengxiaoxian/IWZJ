package api;

import bean.TestModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by dell on 2016/10/14.
 */
public interface APIInterface {
    @GET("/users/{user}")
    Call<TestModel> repo(@Path("user") String user);

}
