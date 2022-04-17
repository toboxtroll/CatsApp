package service;

import com.google.gson.Gson;
import model.Cat;
import model.CatFavourite;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ViewCatsService {

    public static void showCats(){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
            Response response = okHttpClient.newCall(request).execute();
            String json = Objects.requireNonNull(response.body()).string();
            json = json.replaceAll("^\\[|]$", "");

            Gson gson = new Gson();
            Cat cat = gson.fromJson(json, Cat.class);
            ImageIcon imageCatBackground = generateImage(cat.getUrl());
            String options = "Opciones: \n" + "1. Ver otra imagen \n" + "2. Favorito \n" + "3. Volver: \n";

            ArrayList<String> botons = new ArrayList<>();
            botons.add("Ver otra imagen");
            botons.add("Favorito");
            botons.add("Volver");

            Object input = JOptionPane.showInputDialog(null, options, cat.getId(), JOptionPane.INFORMATION_MESSAGE, imageCatBackground, botons.toArray(), botons.get(0));
            int optionSelected = botons.indexOf(input);
            switch (optionSelected) {
                case 0:
                    showCats();
                    break;

                case 1:
                    favouriteCat(cat);
                    break;

                default:
                    break;
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public static ImageIcon generateImage(String url){
        try{
            Image imageCat;
            URL urlImage = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlImage.openConnection();
            http.addRequestProperty("User-Agent", "");
            BufferedImage bufferedImage = ImageIO.read(http.getInputStream());
            ImageIcon imageCatBackground = new ImageIcon(bufferedImage);

            if (imageCatBackground.getIconWidth() > 800){
                imageCat = imageCatBackground.getImage();
                imageCat = imageCat.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                imageCatBackground = new ImageIcon(imageCat);
            }
            return imageCatBackground;
        } catch (IOException e){
            System.out.println(e);
        }
        return null;
    }

    public static void favouriteCat(Cat cat){
        try {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create("{\n  \"image_id\": \"" + cat.getId() + "\"}", mediaType);
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("POST", body)
                    .addHeader("x-api-key", cat.getApiKey())
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()){
                JOptionPane.showMessageDialog(null, "Favorite cat marked successful");
            }
        } catch (IOException e){
            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void showFavouriteCats(String apiKey){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .method("GET", null)
                    .addHeader("x-api-key", apiKey)
                    .build();
            Response response = client.newCall(request).execute();

            String json = Objects.requireNonNull(response.body()).string();

            Gson gson = new Gson();
            CatFavourite[] catFavourites = gson.fromJson(json, CatFavourite[].class);

            if (catFavourites.length > 0) {
                Random random = new Random();
                int index = random.nextInt(catFavourites.length);
                CatFavourite catFavourite = catFavourites[index];
                System.out.println(catFavourite.getCatImage().getUrl());
                System.out.println(catFavourite.getApiKey());


                ImageIcon imageCatBackground = generateImage(catFavourite.getCatImage().getUrl());
                String options = "Opciones: \n" + "1. Ver otra imagen \n" + "2. Eliminar Favorito \n" + "3. Volver: \n";

                ArrayList<String> botons = new ArrayList<>();
                botons.add("Ver otra imagen");
                botons.add("Eliminar Favorito");
                botons.add("Volver");

                Object input = JOptionPane.showInputDialog(null, options, catFavourite.getId(), JOptionPane.INFORMATION_MESSAGE, imageCatBackground, botons.toArray(), botons.get(0));
                int optionSelected = botons.indexOf(input);
                switch (optionSelected) {
                    case 0:
                        showFavouriteCats(catFavourite.getApiKey());
                        break;

                    case 1:
                        deleteFavoriteCat(catFavourite);
                        break;

                    default:
                        break;
                }
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public static void deleteFavoriteCat(CatFavourite catFavourite){
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create("", mediaType);
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + catFavourite.getId())
                    .method("DELETE", body)
                    .addHeader("x-api-key", catFavourite.getApiKey())
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(null, "Favorite cat deleted successful");
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
