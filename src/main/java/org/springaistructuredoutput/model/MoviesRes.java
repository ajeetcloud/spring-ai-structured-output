package org.springaistructuredoutput.model;

import java.util.List;

public record MoviesRes(String title, String director, int releaseYear, List<String> mainActors) {
}
