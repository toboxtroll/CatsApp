package model;

import io.github.cdimascio.dotenv.Dotenv;

public class Cat {
    Dotenv dotevn = Dotenv.load();
    String id;
    String url;
    String apiKey = dotevn.get("API_KEY");

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

}
