package net.fhirfactory.pegacorn.util;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

public class FhirUtil {
    private static FhirUtil INSTANCE = new FhirUtil();
    public static FhirUtil getInstance() {
        return INSTANCE;
    }
    
    private FhirContext fhirContext;

    private FhirUtil() {
        fhirContext = FhirContext.forR4();
    }
    
    /**
     * NOTE: the result is thread safe.
     * 
     * @see {FhirContext}
     */
    public FhirContext getFhirContext() {
        return fhirContext;
    }

    /**
     * NOTE: the result is NOT thread safe.
     * 
     * @see {IParser.newJsonParser()}
     */
    public IParser getJsonParser() {
        return getFhirContext().newJsonParser();
    }
}
