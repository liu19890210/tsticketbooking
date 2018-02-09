package guominchuxing.tsbooking.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import guominchuxing.tsbooking.constant.Const;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 统一管理类
 * Created by admin on 2017/1/19.
 */

public class ApiManager {

   private static  ApiManagerService apiManagerService;
   private static final Gson INNER_GSON = new Gson();
   private static Gson gson;
   public static ApiManagerService getDefaut(){
            if (apiManagerService == null){
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .build();
                Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(List.class, new JsonDeserializer() {
                    @Override
                    public List deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
                        if (json.isJsonArray()) {
                            return INNER_GSON.fromJson(json, type);
                        } else if (json.isJsonNull()) {
                            return Collections.emptyList();
                        } else {
                            return Collections.emptyList();
                        }
                    }
                }).create();
                GsonConverterFactory factory = GsonConverterFactory.create(gson);
                apiManagerService = new Retrofit.Builder()
                        .baseUrl(Const.BASEIP)
                        .client(client)
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(factory)
                        .build().create(ApiManagerService.class);
            }
        return apiManagerService;
    }

}
