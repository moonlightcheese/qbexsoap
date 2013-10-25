package com.moonlightcheese.examples;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.rmi.RemoteException;

import com.intuit.developer.ArrayOfString;
import com.intuit.developer.AuthResponse;
import com.intuit.developer.QBWebConnectorSvcSoap;

/**
 * Created with IntelliJ IDEA.
 * User: jarrod
 * Date: 10/24/13
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class ControlServlet extends HttpServlet implements QBWebConnectorSvcSoap {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        printOutput(request, response);
    }

    //public void doGet(HttpServletRequest request, HttpServletResponse response) {
    //    printOutput(request, response);
    //}

    public void printOutput(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter out = null;
        try {
            out =  response.getWriter();
        } catch(Exception e) {
            //wut...
        }
        if(out!=null) {

            File logFile = new File("C:\\tomcat\\webapplog.txt");
            try {
                if(!logFile.exists())
                    logFile.createNewFile();
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
        }
    }

    public void authentication(){

    }

    @Override
    public AuthResponse authenticate2(String strUserName, String strPassword) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayOfString authenticate(String strUserName, String strPassword) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String sendRequestXML(String ticket, String strHCPResponse, String strCompanyFileName, String qbXMLCountry, int qbXMLMajorVers, int qbXMLMinorVers) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int receiveResponseXML(String ticket, String response, String hresult, String message) throws RemoteException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String connectionError(String ticket, String hresult, String message) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLastError(String ticket) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String closeConnection(String ticket) throws RemoteException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
