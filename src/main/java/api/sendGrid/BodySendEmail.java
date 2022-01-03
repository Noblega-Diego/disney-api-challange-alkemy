package api.sendGrid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class BodySendEmail {
    private Personalization[] personalizations;
    private Content[] content;
    private Email from;
    private Email replyTo;
}
