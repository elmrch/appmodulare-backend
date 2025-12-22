// Attendance.java
package com.appmodulare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @Column(name = "total_hours")
    private Double totalHours;
    @Column(name = "total_hours_formatted", length = 20)
    private String totalHoursFormatted;
    @Column(length = 20)
    private String status; // PUNTUAL / TARDE
/*desde a   qui*/
// Coordenadas GPS del check-in
@Column(name = "check_in_latitude")
private Double checkInLatitude;

    @Column(name = "check_in_longitude")
    private Double checkInLongitude;

    @Column(name = "check_in_location", length = 500)
    private String checkInLocation;

    // Coordenadas GPS del check-out
    @Column(name = "check_out_latitude")
    private Double checkOutLatitude;

    @Column(name = "check_out_longitude")
    private Double checkOutLongitude;

    @Column(name = "check_out_location", length = 500)
    private String checkOutLocation;

    // Validación de ubicación
    @Column(name = "location_valid")
    private Boolean locationValid;

    @Column(name = "distance_from_office")
    private Double distanceFromOffice;

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS = 6371000; // metros

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }


    public void validateLocation(double officeLat, double officeLon, double maxDistance) {
        if (checkInLatitude != null && checkInLongitude != null) {
            this.distanceFromOffice = calculateDistance(
                    officeLat, officeLon,
                    checkInLatitude, checkInLongitude
            );
            this.locationValid = this.distanceFromOffice <= maxDistance;


            // if (!this.locationValid && !"TARDE".equals(this.status)) {
            //     this.status = "FUERA_DE_UBICACION";
            // }
        }
    }
    @Column(nullable = false)
    private LocalDate date;

    @PrePersist
    public void prePersist() {
        if (date == null) {
            date = LocalDate.now();
        }
    }

}
