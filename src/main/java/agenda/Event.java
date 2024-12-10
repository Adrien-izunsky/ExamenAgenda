package agenda;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class Event {
    private String title;
    private LocalDateTime start;
    private Duration duration;
    private ChronoUnit repetitionUnit;
    private LocalDateTime terminationDateTime;
    private Integer numberOfRepetitions;
    private Set<LocalDate> exceptions;
    private Repetition repetition;

    public Event(String title, LocalDateTime start, Duration duration) {
        this.title = title;
        this.start = start;
        this.duration = duration;
        this.exceptions = new HashSet<>();
    }

    public void setRepetition(ChronoUnit repetitionUnit) {
        this.repetitionUnit = repetitionUnit;
        this.repetition = new Repetition(repetitionUnit);  // Crée une nouvelle répétition
    }

    public void setTermination(LocalDateTime terminationDateTime) {
        this.terminationDateTime = terminationDateTime;
    }

    public void setTermination(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public void addException(LocalDate exceptionDate) {
        this.exceptions.add(exceptionDate);
    }

    public boolean isInDay(LocalDate date) {
        // Vérifie si la date est une exception
        if (exceptions.contains(date)) {
            return false;
        }

        // Si la date correspond à la date de terminaison, l'événement a lieu ce jour-là
        if (terminationDateTime != null && terminationDateTime.toLocalDate().equals(date)) {
            return true;
        }

        // Vérifie si l'événement a lieu le jour de départ
        if (start.toLocalDate().equals(date)) {
            return true;
        }

        // Calcul des répétitions
        LocalDate eventDate = start.toLocalDate();
        // Vérifie les répétitions tant que la date est avant ou égale à la date de terminaison
        while (eventDate.isBefore(date) || eventDate.equals(date)) {
            eventDate = eventDate.plus(1, repetitionUnit);  // Ajoute la durée de répétition

            // Si la date correspond à la date de terminaison, on permet l'occurrence
            if (terminationDateTime != null && eventDate.equals(terminationDateTime.toLocalDate())) {
                return true;
            }

            // Si on dépasse la date de terminaison, on arrête
            if (terminationDateTime != null && eventDate.isAfter(terminationDateTime.toLocalDate())) {
                break;
            }

            // Vérifie si l'événement est dans la période de terminaison
            if (terminationDateTime != null && eventDate.isAfter(terminationDateTime.toLocalDate())) {
                break;
            }

            // Vérification du nombre de répétitions
            if (numberOfRepetitions != null && ChronoUnit.DAYS.between(start.toLocalDate(), eventDate) / repetitionUnit.getDuration().toDays() >= numberOfRepetitions) {
                break;  // Si le nombre de répétitions est atteint, on arrête
            }

            // Si l'événement se produit à la date recherchée, renvoie vrai
            if (eventDate.equals(date)) {
                return true;
            }
        }

        return false;
    }

    public LocalDate getTerminationDate() {
        if (terminationDateTime != null) {
            return terminationDateTime.toLocalDate();
        }
        return start.toLocalDate().plusWeeks(numberOfRepetitions - 1);
    }

    public int getNumberOfOccurrences() {
        if (terminationDateTime != null) {
            return (int) ChronoUnit.WEEKS.between(start.toLocalDate(), terminationDateTime.toLocalDate()) + 1;
        }
        return numberOfRepetitions;
    }

    @Override
    public String toString() {
        return title;
    }
}
