package com.bertzzie.dota2.model;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * [Documentation Here]
 *
 * @author Alex Xandra Albert Sim
 */
@Data
@Builder
public class EntityCoordinate implements Serializable {
    public static final EntityCoordinate DUMMY_COORDINATE = EntityCoordinate.builder()
            .X(-999999f)
            .Y(-999999f)
            .build();

    private Float X;
    private Float Y;

    @Override
    public int hashCode() {
        return new HashCodeBuilder(71, 31)
                .append(X)
                .append(Y)
                .toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EntityCoordinate)) {
            return false;
        }

        if (object == this) {
            return true;
        }

        EntityCoordinate rightHandSide = (EntityCoordinate) object;

        return new EqualsBuilder()
                .append(X, rightHandSide.X)
                .append(Y, rightHandSide.Y)
                .isEquals();
    }

    @Override
    public String toString() {
        return getX().toString() +  "," + getY().toString();
    }
}
