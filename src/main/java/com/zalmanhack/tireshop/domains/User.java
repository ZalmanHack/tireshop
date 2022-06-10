package com.zalmanhack.tireshop.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.zalmanhack.tireshop.domains.enums.Role;
import com.zalmanhack.tireshop.views.CarView;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "usr")
@ToString(includeFieldNames=true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(value = CarView.Internal.class)
    private long id;

    @NotBlank
    @Size(min = 1, max = 64)
    @JsonView(value = CarView.Internal.class)
    private String username;

    @NotBlank
    @Column(length = 26)
    @Size(min = 1, max = 26)
    @JsonView(value = CarView.Internal.class)
    private String firstName;

    @NotBlank
    @Column(length = 26)
    @Size(min = 1, max = 26)
    @JsonView(value = CarView.Internal.class)
    private String lastName;

    @NotNull
    @Min(100)
    @JsonView(value = CarView.Internal.class)
    private long phone;

    @NotBlank
    @Column(length = 320)
    @Size(min = 5, max = 320)
    @JsonView(value = CarView.Internal.class)
    private String email;

    @Size(max = 320)
    @Column(length = 320)
    private String newEmail;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Car> cars;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "user")
    private List<Booking> bookings;

    @NotNull
    @JsonView(value = CarView.Internal.class)
    private short rating;

    private String password = "";

    private String activationCode;

    @NotNull
    private boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }
}
