package ui;

import model.Cat;
import service.ViewCatsService;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class UIMenu {

    public static void showMenu() throws IOException {
        int optionSelected;
        ArrayList<String> options = new ArrayList<>();
        options.add(" 1. Ver Gatos");
        options.add(" 2. Ver Gatos Favoritos");
        options.add(" 3. Salir");

        do{
            Object input =
                    JOptionPane.showInputDialog(
                            null, "Gatos Java", "Menu principal", JOptionPane.INFORMATION_MESSAGE, null, options.toArray(), options.get(0)
                    );
            optionSelected = options.indexOf(input);
            System.out.println("optionSelected: " + optionSelected);

            switch (optionSelected){
                case 0:
                    ViewCatsService.showCats();
                    break;

                case 1:
                    Cat cat = new Cat();
                    ViewCatsService.showFavouriteCats(cat.getApiKey());
                    break;

                case 2:
                    optionSelected = -1;

            }
        }while (optionSelected != -1);
    }
}
