package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.views.CarView;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Locale;

@Data
@Entity
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = CarView.Public.class)
    private long id;

    @NotBlank
    @Column(length = 6)
    @Length(min = 6, max = 6)
    @JsonView(value = CarView.Public.class)
    private String hexColor = "FFFFFF";

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor.toUpperCase();
    }
}
