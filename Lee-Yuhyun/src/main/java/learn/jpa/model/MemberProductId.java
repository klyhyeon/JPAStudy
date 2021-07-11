package learn.jpa.model;

import lombok.*;

import java.io.Serializable;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MemberProductId implements Serializable {

    private String member;
    private String product;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
