package com.bonnesbaby.challenge.menu;


import com.bonnesbaby.challenge.models.Book;
import com.bonnesbaby.challenge.models.BookResponse;
import com.bonnesbaby.challenge.services.ConsumoAPI;
import com.bonnesbaby.challenge.services.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarbookWeb();
                    break;
                case 2:

                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private BookResponse getDatosSerie() {
        System.out.println("Escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") );
        System.out.println(json);
        BookResponse datos = conversor.obtenerDatos(json, BookResponse.class);
        return datos;
    }
    private void buscarbookWeb() {
        BookResponse datos = getDatosSerie();
        if (datos != null && datos.results() != null && !datos.results().isEmpty()) {
            Book primerLibro = datos.results().get(0); // Obtener el primer libro

            System.out.println("\n--- Primer Libro Encontrado ---");
            System.out.println("Título: " + primerLibro.title());
            if (primerLibro.authors() != null && !primerLibro.authors().isEmpty()) {
                System.out.println("Autores:");
                primerLibro.authors().forEach(author -> System.out.println(" - " + author.name()));
            } else {
                System.out.println("Autores: No disponibles");
            }
            System.out.println("Idiomas: " + (primerLibro.languages() != null ? primerLibro.languages() : "No disponibles"));
            System.out.println("Número de descargas: " + primerLibro.downloadCount());
        } else {
            System.out.println("No se encontraron libros para esa búsqueda.");
        }
    }


}

