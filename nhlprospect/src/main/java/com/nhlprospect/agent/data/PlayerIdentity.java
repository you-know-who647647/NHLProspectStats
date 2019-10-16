package com.nhlprospect.agent.data;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@EqualsAndHashCode
public class PlayerIdentity {
    @NonNull
    private Integer id;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String firstName;

    @NonNull
    @EqualsAndHashCode.Exclude
    private String lastName;

    public String fullName() {
        return firstName + " " + lastName;
    }
}
