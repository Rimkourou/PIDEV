package iServices;

import entites.Film;
import javafx.collections.ObservableList;

import java.util.List;

public interface IServiceFilm{
    void addFilm (Film f);
    void editFilm (Film f);
    List<Film> filmList();
    void deleteFilm(int id);
    ObservableList<Film> searchFilmByType(String genre);
    ObservableList<Film> searchFilmByCategory(String categorie);
    ObservableList<Film> searchFilmByTitle(String titre);
}
