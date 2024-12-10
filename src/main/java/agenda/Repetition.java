package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Repetition {
    private final ChronoUnit frequency;
    private Set<LocalDate> exceptions;  // Pour stocker les dates d'exception où l'événement ne doit pas se produire

    public Repetition(ChronoUnit frequency) {
        this.frequency = frequency;
        this.exceptions = new HashSet<>();
    }

    public ChronoUnit getFrequency() {
        return frequency;
    }

    public void addException(LocalDate date) {
        exceptions.add(date);  // Ajoute une exception pour que l'événement ne se répète pas ce jour-là
    }

    public boolean isException(LocalDate date) {
        return exceptions.contains(date);  // Vérifie si la date est une exception
    }

    // Calcule la prochaine occurrence de l'événement à partir d'une date donnée
    public LocalDate getNextOccurrence(LocalDate startDate) {
        return startDate.plus(1, frequency);  // Retourne la date de la prochaine répétition
    }
}
