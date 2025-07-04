package org.example.demo2.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/*Indica che quando generi la JavaDoc del tuo progetto
questa annotation personalizzata (@Maggiorenne, @IniziaConMaiuscola) 
verrà inclusa nei commenti della documentazione del campo.
 Utile per chiarezza*/
@Documented
/*
 * Dice a Jakarta Bean Validation (Hibernate Validator) quale classe esegue la
 * logica di validazione per questa annotation. Anello di collegamento tra
 * l’annotation e la logica di validazione vera e
 * propria.
 */
@Constraint(validatedBy = MaggiorenneValidator.class)
/*
 * Indica dove puoi mettere questa annotation.
 * 
 * FIELD ➜ puoi metterla sui campi (private String nome;).
 * 
 * METHOD ➜ puoi usarla su metodi.
 * 
 * TYPE ➜ puoi usarla su classi/interfacce.
 * 
 * PARAMETER ➜ puoi usarla sui parametri di metodo.
 */
@Target({ FIELD })
/*
 * Indica quando l’annotation è disponibile.
 * 
 * RUNTIME ➜ l’annotation deve essere disponibile a runtime, perché Jakarta Bean Validation la deve leggere mentre esegue la validazione.
 * 
 * SOURCE ➜ esiste solo nel codice sorgente (sparisce in compilazione).
 * 
 * CLASS ➜ esiste nel .class ma non a runtime (inutile per validator).
 * 
 * Quindi: per le custom annotation di validazione, serve sempre RUNTIME.
 */
@Retention(RUNTIME)
public @interface Maggiorenne {
  String message() default "Devi avere almeno 18 anni!";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
/*Questi tre sono obbligatori per seguire lo standard Bean Validation.

message ➜ Il messaggio di errore di default, se la validazione fallisce. Puoi personalizzarlo dove usi l’annotation:

groups ➜ Serve se vuoi validare in gruppi diversi (avanzato). Di solito lo lasci vuoto.

payload ➜ Serve se vuoi allegare metadati extra (pochissimo usato). Si lascia vuoto al 99%.


 */