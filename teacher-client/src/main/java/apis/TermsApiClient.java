package apis;

import entities.Term;
import org.springframework.http.ResponseEntity;

public class TermsApiClient extends ApiClient {

    private final String TERMS_URL = "/terms";

    public void addTerm(Term newTerm) {
        String url = BASE_URL + TERMS_URL;
        ResponseEntity<Term> response = restTemplate.postForEntity(url, newTerm, Term.class);
    }

    public void deleteTerm(Long termId) {
        String url = BASE_URL + TERMS_URL + "/" + termId;
        restTemplate.delete(url);
    }

    public Term[] getTerms() {
        String url = BASE_URL + TERMS_URL;
        ResponseEntity<Term[]> response = restTemplate.getForEntity(url, Term[].class);
        return response.getBody();
    }

}
