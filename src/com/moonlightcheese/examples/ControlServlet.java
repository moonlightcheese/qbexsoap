package com.moonlightcheese.examples;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.swing.text.Document;
import javax.xml.soap.*;
//import javax.xml.messaging.*;
import java.io.*;
import java.rmi.RemoteException;
import java.util.Iterator;

import com.intuit.developer.ArrayOfString;
import com.intuit.developer.AuthResponse;
import com.intuit.developer.QBWebConnectorSvcSoap;

import org.w3c.dom.Document;

/**
 * Created with IntelliJ IDEA.
 * User: jarrod & larry at the Coding Dungeon
 * Date: 10/24/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ControlServlet extends HttpServlet implements QBWebConnectorSvcSoap {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        //SOAPElement soapElementUsername =
        //printOutput(request, response);
        readSoapMessage(request);

        //System.out.print(response.toString());
        //authenticate(soapAuthStrUserName, soapAuthStrPassword);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        printOutput(request, response);



    }

    private void readSoapMessage(HttpServletRequest request) {

        System.out.println("Entering readSoapMessage");
        if (System.getProperty("com.sun.management.jmxremote") == null)
            System.out.println("JMX remote is disabled");
        //read SOAP message from BufferedReader
        /*
        String message = new String();
        while(soapReader != null){
            try {
                if(message!=null){
                    //read string to find callback tags. Store as appropriate. maybe use jaxb?
                    message = soapReader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.print("Error Reading soap message");
            }
        }
        */

        //analyze SOAP Message body
        MessageFactory factory = null;
        try {
            factory = MessageFactory.newInstance();
        } catch(SOAPException se) {
            se.printStackTrace();
        }
        if(factory!=null) {
            try {
                SOAPMessage soapMessage = factory.createMessage(new MimeHeaders(), request.getInputStream());
                Document document = soapMessage.getSOAPBody().extractContentAsDocument();
                document.getElementsByTagName("authenticate");
                //System.out.println(soapMessage.getSOAPPart().getEnvelope().getBody().getElementName().getLocalName());
                //System.out.println(document.getDocumentElement().getElementsByTagName("authenticate").getLength());
                System.out.println(document.getDocumentElement().getLocalName());
                String nameXML = document.getDocumentElement().getLocalName();
                String userNameXML;
                String userPassXML;
                if (nameXML.equals("authenticate")){

                    if(document.getFirstChild().getFirstChild().getLocalName().equals("strUserName")) {
                        System.out.println("Username: " );
                        userNameXML = document.getFirstChild().getFirstChild().getTextContent();
                        userPassXML = document.getFirstChild().getFirstChild().getNextSibling().getTextContent();
                        System.out.println("userPassXML: "+userPassXML + "\n"+ "userNameXML: " + userNameXML);
                        authenticate2(userNameXML, userPassXML);

                    }

                    System.out.println(document.getFirstChild().getFirstChild().getTextContent());
                }
            } catch (SOAPException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void printOutput(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out =  response.getWriter();

        } catch(Exception e) {
            //wut...
        }
        if(out!=null) {

            File logFile = new File("webapplog.txt");
            try {
                if(!logFile.exists()){
                    logFile.createNewFile();
                    out.print("Log File Created");
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
                BufferedReader reader = request.getReader();
                try {
                    String curLine = null;
                    while((curLine = reader.readLine())!=null) {
                        writer.append(request.getRemoteHost() + ": " + curLine + "\n");
                    }
                } catch(Exception e2) {
                    //reached end of file
                    out.println("e2: "+e2.toString());
                }
                writer.close();
                //reader.close();
            } catch (Exception e) {
                out.println("e: "+e.toString());
            }
            out.println(request.toString());
            //out.println("something else :P");
            System.out.println("printOutput() method complete...");
        }
    }

    //TODO add clientVersion method here.

    @Override
    public AuthResponse authenticate2(String strUserName, String strPassword) throws RemoteException {
        AuthResponse authResponse = new AuthResponse();
        String sessionToken = authResponse.getSessionTicket();
        System.out.println(sessionToken);
        return authResponse;
    }

    @Override
    public ArrayOfString authenticate(String strUserName, String strPassword) throws RemoteException {

        String evLogTxt="WebMethod: authenticate() has been called by QBWebconnector" + " " ;
       /* evLogTxt=evLogTxt+"Parameters received:
        evLogTxt=evLogTxt+"string strUserName = " + strUserName + "
        evLogTxt=evLogTxt+"string strPassword = " + strPassword + "
        evLogTxt=evLogTxt+"*/

        String[] asRtn = new String[4];

        asRtn[0] = "CDCGrill";
        asRtn[1] = "60"; //"{57F3B9B1-86F1-4fcc-B1EE-566DE1813D20}"; //myGUID.toString(); comes from tails qwcfile
        //asRtn[2] = strPassword;//"none"; //probably password from qwc
        System.out.println("In authenticate new two");
        ArrayOfString asRtn2 = new ArrayOfString(asRtn);
        System.out.println("In authenticate step2");
        System.out.println("In authenticate as[0] = " + asRtn2.getString(0));
        System.out.println("In authenticate as[1] = " + asRtn2.getString(1));



        return asRtn2;

    }

    //Stops being called after recieveResponseXML returns 100
    @Override
    public String sendRequestXML(String ticket, String strHCPResponse, String strCompanyFileName, String qbXMLCountry, int qbXMLMajorVers, int qbXMLMinorVers) throws RemoteException {
        return null;
    }

    //return a 100 means 100% completed and QBWC will stop calling sendRequestXML()
    @Override
    public int receiveResponseXML(String ticket, String response, String hresult, String message) throws RemoteException {
        return -3;
    }

    @Override
    public String connectionError(String ticket, String hresult, String message) throws RemoteException {
        return null;
    }

    @Override
    public String getLastError(String ticket) throws RemoteException {
        return null;
    }

    @Override
    public String closeConnection(String ticket) throws RemoteException {
        System.out.println("In closeConnection");
        System.out.println(ticket);
        return("close with this message");
    }
}
