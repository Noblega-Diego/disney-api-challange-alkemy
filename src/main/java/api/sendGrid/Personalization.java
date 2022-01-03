package api.sendGrid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Personalization {
    private Email[] to;
    private String subject;
}
