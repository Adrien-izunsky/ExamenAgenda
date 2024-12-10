package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Termination {

    private final LocalDate endDate;  // Date de terminaison de l'événement
    private final int occurrencesLimit;  // Nombre d'occurrences avant la terminaison

    // Constructeur pour une terminaison par date
    public Termination(LocalDate endDate) {
        this.endDate = endDate;
        this.occurrencesLimit = -1;  // Pas de limite sur le nombre d'occurrences
    }

    // Constructeur pour une terminaison par nombre d'occurrences
    public Termination(int occurrencesLimit) {
        this.occurrencesLimit = occurrencesLimit;
        this.endDate = null;  // Pas de date de terminaison fixe
    }

    // Vérifie si l'événement est terminé à la date donnée
    public boolean isTerminated(LocalDate startDate) {
        if (endDate != null) {
            return !startDate.isBefore(endDate);  // L'événement est terminé si la date de départ dépasse la date de terminaison
        }
        if (occurrencesLimit != -1) {
            // Vérifie si l'événement a atteint le nombre d'occurrences
            return ChronoUnit.DAYS.between(startDate, LocalDate.now()) >= occurrencesLimit;
        }
        return false;  // Aucun critère de terminaison n'est défini
    }
}
