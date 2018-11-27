package com.example.formation12.quizz.api;

import android.util.Log;

import com.example.formation12.quizz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIClient {

    //private final String baseUrl = "http://192.168.10.38:3000";
    private final String baseUrl = "http://192.168.10.144:3000";

    private final String KEY_ID = "id";
    private final String KEY_INTITULE = "title";
    private final String KEY_ANSWER1 = "answer_1";
    private final String KEY_ANSWER2 = "answer_2";
    private final String KEY_ANSWER3 = "answer_3";
    private final String KEY_ANSWER4 = "answer_4";
    private final String KEY_BONNEREPONSE = "correct_answer";
    private final String KEY_IMAGE_AUTHOR = "author_img_url";
    private final String KEY_AUTHOR_NAME = "author";

    private final String AUTHOR_IMAGE = "https://img.ohmymag.com/article/480/humour/mr-bean-s-incruste-dans-avatar_8d73c59406e9ab1833b2cb3cb403bf93ee3dfe26.jpg";
    private final String AUTHOR_NAME = "josselin";


    private final OkHttpClient client = new OkHttpClient();

    private static APIClient sInstance = new APIClient();

    public static APIClient getInstance(){
        return sInstance;

    }

    public void getQuestions(final APIResult<List<Question>> result) {

        Request request = new Request.Builder()
                .url(baseUrl + "/questions")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                List<Question> questions = new ArrayList<>();

                String responseData = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(responseData);
                    Log.d("Debug : ", jsonArray.toString());

                    for (int i = 0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        questions.add(parseJsonObject(jsonObject));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result.OnSuccess(questions);
            }
        });

    }

    public void createQuestion(final APIResult<Question> result, Question q) {
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json = parseQuestionToJSON(q);
        } catch (JSONException e) {
            Log.d("Debug : ", e.getMessage());
        }
        Request request = request = new Request.Builder()
                .url(baseUrl+"/questions").method("POST", RequestBody.create(JSON_TYPE,json.toString()))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONObject jsonServerObject = new JSONObject(responseData);
                    result.OnSuccess(parseJsonObject(jsonServerObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void deleteQuestion(final APIResult<Question> result, Question q) {
        MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json = parseQuestionToJSON(q);
        } catch (JSONException e) {
            Log.d("Debug : ", e.getMessage());
        }
        Request request = request = new Request.Builder()
                .url(baseUrl+"/questions").method("DELETE", RequestBody.create(JSON_TYPE,json.toString()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("Debug delete ", "Response !!");
                try {
                    JSONObject jsonServerObject = new JSONObject(responseData);
                    result.OnSuccess(parseJsonObject(jsonServerObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Question parseJsonObject (JSONObject jsonObject) throws JSONException {

        Question question = new Question(jsonObject.getString(KEY_INTITULE), 4);
        question.propositions.add(jsonObject.getString(KEY_ANSWER1));
        question.propositions.add(jsonObject.getString(KEY_ANSWER2));
        question.propositions.add(jsonObject.getString(KEY_ANSWER3));
        question.propositions.add(jsonObject.getString(KEY_ANSWER4));
        question.bonneReponse = question.propositions.get(jsonObject.getInt(KEY_BONNEREPONSE)-1);
        question.idquestion = jsonObject.getInt(KEY_ID);
        question.imageAuthorUrl = jsonObject.getString(KEY_IMAGE_AUTHOR);
        question.authorName = jsonObject.getString(KEY_AUTHOR_NAME);
        return question;
    }

    private JSONObject parseQuestionToJSON(Question q) throws JSONException {
        JSONObject json = new JSONObject();
        json.put(KEY_BONNEREPONSE,(q.propositions.indexOf(q.bonneReponse)+1));
        json.put(KEY_ANSWER1,q.propositions.get(0));
        json.put(KEY_ANSWER2,q.propositions.get(1));
        json.put(KEY_ANSWER3,q.propositions.get(2));
        json.put(KEY_ANSWER4,q.propositions.get(3));
        json.put(KEY_INTITULE, q.intitule);
        json.put(KEY_IMAGE_AUTHOR, AUTHOR_IMAGE);
        json.put(KEY_AUTHOR_NAME, AUTHOR_NAME);
        json.put(KEY_ID, q.idquestion);
        return json;
    }

    public interface APIResult<T> {
        void onFailure(Exception e);
        void OnSuccess(T object);
    }

}
