import javax.xml.soap.*;


public class SIFtester {

	public static void main(String[] args) {
		String soapEndpointUrl = "http://yourserver:4000/SI.WS.Core/SIF/ContactService.svc";
//        String soapAction = "http://software-innovation.com/SI.Data/IContactService/Ping";
        String soapAction = "http://software-innovation.com/SI.Data/IContactService/GetContactPersons";

        callSoapWebService(soapEndpointUrl, soapAction);

	}
	
    private static void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();
            

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
    }
	
	private static void createSoapEnvelope_Ping(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "si";
        String myNamespaceURI = "http://software-innovation.com/SI.Data";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("Ping", myNamespace);

    }
	
	private static void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "si";
        String myNamespaceURI = "http://software-innovation.com/SI.Data";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
//        SOAPElement soapBodyElem = soapBody.addChildElement("Ping", myNamespace);
        
        SOAPElement soapBodyElem = soapBody.addChildElement("GetContactPersons", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("parameter", myNamespace);
        SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("Active", myNamespace);
        soapBodyElem2.addTextNode("true");
    }



    private static SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        
        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        headers.addHeader("Authorization", "Basic " + yourstring);

        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

}
