package me.blackwater.tools.model;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "tool")
@Table(name = "tools")

@NoArgsConstructor
@Getter
@Schema(description = "Model narzędzia")
public class Tool  implements Serializable {

    @Serial
    @Schema(description = "Numer seryjny modelu")
    private static final long serialVersionUID = 1L;

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Id narzędzia", example = "1")
    private long id;

    @Schema(description = "Nazwa narzędzia" , example = "Siekiera")
    private String name;

    @Schema(description = "Cena narzędzia", example = "200")
    private int price;

    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    @Schema(description = "Sklep w którym sprzedawane jest narzędzie")
    private Shop shop;

    public Tool(String name, int price, Shop shop) {
        this.name = name;
        this.price = price;
        this.shop = shop;
    }

}
