package me.blackwater.tools.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "shop")
@Table(name = "shops")

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Model sklepu, sprzedającego narzędzia")
public class Shop implements Serializable {

    @Serial
    @Schema(description = "Wersja seryjna modelu")
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Id sklepu")
    private long id;

    @Schema(description = "Nazwa sklepu")
    private String name;

    @Schema(description = "Email sklepu")
    private String email;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tool> tools;


    public Shop(String name, String email) {
        this.name = name;
        this.email = email;
        this.tools = new ArrayList<>();
    }
}
