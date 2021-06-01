package com.example.leasing.entity.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Data
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PACKAGE)
@Entity
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Builder.Default
    private String reference = UUID.randomUUID().toString();

    @NonNull
    private BigDecimal requestedAmount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.NEW;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "application", cascade = {CascadeType.ALL})
    private Set<Applicant> applicants;

    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "application", cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn
    private VehicleData vehicleData;

    public static ApplicationBuilder builder() {
        return new BiDirectionalApplicationBuilder();
    }

    private static class BiDirectionalApplicationBuilder extends ApplicationBuilder {

        @Override
        public Application build() {
            Application application = super.build();
            application.getApplicants().forEach(applicant -> applicant.setApplication(application));
            application.getVehicleData().setApplication(application);
            return application;
        }

    }

}
