package net.fhirfactory.pegacorn.fhir.operations;

import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.util.OperationOutcomeUtil;
import org.hl7.fhir.r4.model.codesystems.OperationOutcome;
import org.hl7.fhir.r4.model.codesystems.OperationOutcomeEnumFactory;
import org.hl7.fhir.r4.utils.OperationOutcomeUtilities;

public class PegacornMethodOutcome extends MethodOutcome {

    public void insertOperationOutcome(OperationOutcome outcomeEnum){
        OperationOutcomeEnumFactory outcomeEnumFactory = new OperationOutcomeEnumFactory();
        MethodOutcome newOutcome = new MethodOutcome();
    }


}
