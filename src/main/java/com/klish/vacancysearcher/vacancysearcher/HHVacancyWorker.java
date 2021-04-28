package com.klish.vacancysearcher.vacancysearcher;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class HHVacancyWorker {

    public static ArrayList<String> getKeySkills(String region, String vacancy) throws Exception {
        int vacancy_counter = 0;

        ArrayList<String> id_array = getFromAllVacancies(region, vacancy).get(0);

        Counter<String> key_skills_counter = new Counter<>();
        ArrayList<String> key_skills_array = new ArrayList<>();

        for (String id : id_array) {
            ArrayList<String> vacancy_key_skills_array = getFromOneVacancy(id);
            vacancy_key_skills_array.remove(0);
            vacancy_counter++;
            for (String key_skill : vacancy_key_skills_array) {
                key_skills_counter.add(key_skill);
                key_skills_array.add(key_skill);
            }
        }
        return key_skills_array;
    }

    private static ArrayList<ArrayList<String>> getFromAllVacancies(String region, String vacancy) throws Exception {

        // Получает список id и названий вакансий.

        ArrayList<ArrayList<String>> result_array = new ArrayList<>();

        String all_vacancies_request = "vacancies?per_page=100&text=" + URLEncoder.encode(vacancy + " " + region, "UTF-8").replace("\\+", "%20");
        JSONObject json_object = getJSONByRequest(all_vacancies_request);

        result_array.add(getFromJSONArray(json_object, "items", "id"));
        result_array.add(getFromJSONArray(json_object, "items", "name"));

        return result_array;
    }

    private static ArrayList<String> getFromOneVacancy(String id) throws Exception {

        //Получает навыки по одной вакансии, первым объектом в результате является имя нанимателя

        ArrayList<String> result_array;

        String one_vacancy_request = "vacancies/" + id;
        JSONObject one_vacancy_json_object = getJSONByRequest(one_vacancy_request);

        result_array = getFromJSONArray(one_vacancy_json_object, "key_skills", "name");
        result_array.add(0, one_vacancy_json_object.getJSONObject("employer").getString("name"));

        return result_array;
    }

    private static ArrayList<String> getFromJSONArray(JSONObject json_object, String key_name, String value_name) throws JSONException {

        // Преобразует JSON объект в JSON массив, и записывает его значения в ArrayList.
        ArrayList<String> result_array = new ArrayList<>();
        JSONArray json_array = json_object.getJSONArray(key_name);

        for (int i = 0; i < json_array.length(); i++)
            result_array.add(json_array.getJSONObject(i).getString(value_name));
        return result_array;
    }

    private static JSONObject getJSONByRequest(String request_field)  throws Exception {

        // Делает запрос и возвращает JSON объект.

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String request_url = "https://api.hh.ru/" + request_field;

        HttpGet request = new HttpGet(request_url);
        request.addHeader(HttpHeaders.USER_AGENT, "VacancySearcherJavaSpring (tuha99@mail.ru)");

        try (CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return new JSONObject(result);
        }
    }
}
