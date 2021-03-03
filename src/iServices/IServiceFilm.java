package iServices;

import entites.Film;

import java.util.List;

public interface IServiceFilm{
    void addFilm (Film f);
    void editFilm (Film f);
    List<Film> filmList();
    void deleteFilm(int id);
    List<Film> filmSalleList();
    List<Film> SearchFilmByTitle(String titre);
}
