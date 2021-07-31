package learn.jpa.model;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

    @NonNull
    private String startDate;

    @NonNull
    private String endDate;

    @Builder
    public static Period of( @NonNull String startDate, @NonNull String endDate){
        return new Period(startDate, endDate);
    }
}
