package model;

public class CatFavourite extends Cat{
    String image_id;
    CatImage image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCat() {
        return image_id;
    }

    public void setIdCat(String image_id) {
        this.image_id = image_id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public CatImage getCatImage() {
        return image;
    }

    public void setCatImage(CatImage catImage) {
        this.image = catImage;
    }
}
