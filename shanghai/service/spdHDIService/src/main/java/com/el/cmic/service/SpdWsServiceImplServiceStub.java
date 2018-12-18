/**
 * SpdWsServiceImplServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.7  Built on : Nov 20, 2017 (11:41:20 GMT)
 */
package com.el.cmic.service;


/*
 *  SpdWsServiceImplServiceStub java implementation
 */
public class SpdWsServiceImplServiceStub extends org.apache.axis2.client.Stub {
    private static int counter = 0;
    protected org.apache.axis2.description.AxisOperation[] _operations;

    //hashmaps to keep the fault mapping
    private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
    private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
    private java.util.HashMap faultMessageMap = new java.util.HashMap();
    private javax.xml.namespace.QName[] opNameArray = null;

    /**
     *Constructor that takes in a configContext
     */
    public SpdWsServiceImplServiceStub(
        org.apache.axis2.context.ConfigurationContext configurationContext,
        String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(configurationContext, targetEndpoint, false);
    }

    /**
     * Constructor that takes in a configContext  and useseperate listner
     */
    public SpdWsServiceImplServiceStub(
        org.apache.axis2.context.ConfigurationContext configurationContext,
        String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
        //To populate AxisService
        populateAxisService();
        populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,
                _service);

        _serviceClient.getOptions()
                      .setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
    }

    /**
     * Default Constructor
     */
    public SpdWsServiceImplServiceStub(
        org.apache.axis2.context.ConfigurationContext configurationContext)
        throws org.apache.axis2.AxisFault {
        this(configurationContext,
            "http://10.17.9.226:8080/cmicspd/ws/spd/uploaddata");
    }

    /**
     * Default Constructor
     */
    public SpdWsServiceImplServiceStub() throws org.apache.axis2.AxisFault {
        this("http://10.17.9.226:8080/cmicspd/ws/spd/uploaddata");
    }

    /**
     * Constructor taking the target endpoint
     */
    public SpdWsServiceImplServiceStub(String targetEndpoint)
        throws org.apache.axis2.AxisFault {
        this(null, targetEndpoint);
    }

    private static synchronized String getUniqueSuffix() {
        // reset the counter if it is greater than 99999
        if (counter > 99999) {
            counter = 0;
        }

        counter = counter + 1;

        return Long.toString(System.currentTimeMillis()) +
        "_" + counter;
    }

    private void populateAxisService() throws org.apache.axis2.AxisFault {
        //creating the Service with a unique name
        _service = new org.apache.axis2.description.AxisService(
                "SpdWsServiceImplService" + getUniqueSuffix());
        addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];

        __operation = new org.apache.axis2.description.OutInAxisOperation();

        __operation.setName(new javax.xml.namespace.QName(
                "http://service.cmic.el.com/", "upLoadData"));
        _service.addOperation(__operation);

        _operations[0] = __operation;
    }

    //populates the faults
    private void populateFaults() {
    }

    /**
     * Auto generated method signature
     *
     * @see com.el.cmic.service.SpdWsServiceImplService#upLoadData
     * @param upLoadData0
     */
    public UpLoadDataResponseE upLoadData(
        UpLoadDataE upLoadData0)
        throws java.rmi.RemoteException {
        org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        try {
            org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
            _operationClient.getOptions()
                            .setAction("http://service.cmic.el.com/SpdWsService/UpLoadData");
            _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

            addPropertyToOperationClient(_operationClient,
                org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
                "&");

            // create SOAP envelope with that payload
            org.apache.axiom.soap.SOAPEnvelope env = null;

            env = toEnvelope(getFactory(_operationClient.getOptions()
                                                        .getSoapVersionURI()),
                    upLoadData0,
                    optimizeContent(
                        new javax.xml.namespace.QName(
                            "http://service.cmic.el.com/", "upLoadData")),
                    new javax.xml.namespace.QName(
                        "http://service.cmic.el.com/", "UpLoadData"));

            //adding SOAP soap_headers
            _serviceClient.addHeadersToEnvelope(env);
            // set the message context with that soap envelope
            _messageContext.setEnvelope(env);

            // add the message contxt to the operation client
            _operationClient.addMessageContext(_messageContext);

            //execute the operation client
            _operationClient.execute(true);

            org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
            org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();

            Object object = fromOM(_returnEnv.getBody()
                                                       .getFirstElement(),
                    UpLoadDataResponseE.class);

            return (UpLoadDataResponseE) object;
        } catch (org.apache.axis2.AxisFault f) {
            org.apache.axiom.om.OMElement faultElt = f.getDetail();

            if (faultElt != null) {
                if (faultExceptionNameMap.containsKey(
                            new org.apache.axis2.client.FaultMapKey(
                                faultElt.getQName(), "UpLoadData"))) {
                    //make the fault by reflection
                    try {
                        String exceptionClassName = (String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(), "UpLoadData"));
                        Class exceptionClass = Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        Exception ex = (Exception) constructor.newInstance(f.getMessage());

                        //message class
                        String messageClassName = (String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                    faultElt.getQName(), "UpLoadData"));
                        Class messageClass = Class.forName(messageClassName);
                        Object messageObject = fromOM(faultElt,
                                messageClass);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                new Class[] { messageClass });
                        m.invoke(ex, new Object[] { messageObject });

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    } catch (ClassCastException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                } else {
                    throw f;
                }
            } else {
                throw f;
            }
        } finally {
            if (_messageContext.getTransportOut() != null) {
                _messageContext.getTransportOut().getSender()
                               .cleanup(_messageContext);
            }
        }
    }

    /**
     * Auto generated method signature for Asynchronous Invocations
     *
     * @see com.el.cmic.service.SpdWsServiceImplService#startupLoadData
     * @param upLoadData0
     */
    public void startupLoadData(
        UpLoadDataE upLoadData0,
        final com.el.cmic.service.SpdWsServiceImplServiceCallbackHandler callback)
        throws java.rmi.RemoteException {
        org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
        _operationClient.getOptions()
                        .setAction("http://service.cmic.el.com/SpdWsService/UpLoadData");
        _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

        addPropertyToOperationClient(_operationClient,
            org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,
            "&");

        // create SOAP envelope with that payload
        org.apache.axiom.soap.SOAPEnvelope env = null;
        final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

        //Style is Doc.
        env = toEnvelope(getFactory(_operationClient.getOptions()
                                                    .getSoapVersionURI()),
                upLoadData0,
                optimizeContent(
                    new javax.xml.namespace.QName(
                        "http://service.cmic.el.com/", "upLoadData")),
                new javax.xml.namespace.QName("http://service.cmic.el.com/",
                    "UpLoadData"));

        // adding SOAP soap_headers
        _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);

        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                public void onMessage(
                    org.apache.axis2.context.MessageContext resultContext) {
                    try {
                        org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();

                        Object object = fromOM(resultEnv.getBody()
                                                                  .getFirstElement(),
                                UpLoadDataResponseE.class);
                        callback.receiveResultupLoadData((UpLoadDataResponseE) object);
                    } catch (org.apache.axis2.AxisFault e) {
                        callback.receiveErrorupLoadData(e);
                    }
                }

                public void onError(Exception error) {
                    if (error instanceof org.apache.axis2.AxisFault) {
                        org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
                        org.apache.axiom.om.OMElement faultElt = f.getDetail();

                        if (faultElt != null) {
                            if (faultExceptionNameMap.containsKey(
                                        new org.apache.axis2.client.FaultMapKey(
                                            faultElt.getQName(), "UpLoadData"))) {
                                //make the fault by reflection
                                try {
                                    String exceptionClassName = (String) faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "UpLoadData"));
                                    Class exceptionClass = Class.forName(exceptionClassName);
                                    java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                    Exception ex = (Exception) constructor.newInstance(f.getMessage());

                                    //message class
                                    String messageClassName = (String) faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(
                                                faultElt.getQName(),
                                                "UpLoadData"));
                                    Class messageClass = Class.forName(messageClassName);
                                    Object messageObject = fromOM(faultElt,
                                            messageClass);
                                    java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                            new Class[] { messageClass });
                                    m.invoke(ex,
                                        new Object[] { messageObject });

                                    callback.receiveErrorupLoadData(new java.rmi.RemoteException(
                                            ex.getMessage(), ex));
                                } catch (ClassCastException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (ClassNotFoundException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (NoSuchMethodException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (java.lang.reflect.InvocationTargetException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (IllegalAccessException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (InstantiationException e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                } catch (org.apache.axis2.AxisFault e) {
                                    // we cannot intantiate the class - throw the original Axis fault
                                    callback.receiveErrorupLoadData(f);
                                }
                            } else {
                                callback.receiveErrorupLoadData(f);
                            }
                        } else {
                            callback.receiveErrorupLoadData(f);
                        }
                    } else {
                        callback.receiveErrorupLoadData(error);
                    }
                }

                public void onFault(
                    org.apache.axis2.context.MessageContext faultContext) {
                    org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                    onError(fault);
                }

                public void onComplete() {
                    try {
                        _messageContext.getTransportOut().getSender()
                                       .cleanup(_messageContext);
                    } catch (org.apache.axis2.AxisFault axisFault) {
                        callback.receiveErrorupLoadData(axisFault);
                    }
                }
            });

        org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;

        if ((_operations[0].getMessageReceiver() == null) &&
                _operationClient.getOptions().isUseSeparateListener()) {
            _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
            _operations[0].setMessageReceiver(_callbackReceiver);
        }

        //execute the operation client
        _operationClient.execute(false);
    }

    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        if (opNameArray == null) {
            return false;
        }

        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;
            }
        }

        return false;
    }

    private org.apache.axiom.om.OMElement toOM(
        UpLoadDataE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(UpLoadDataE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.om.OMElement toOM(
        UpLoadDataResponseE param,
        boolean optimizeContent) throws org.apache.axis2.AxisFault {
        try {
            return param.getOMElement(UpLoadDataResponseE.MY_QNAME,
                org.apache.axiom.om.OMAbstractFactory.getOMFactory());
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory,
        UpLoadDataE param,
        boolean optimizeContent, javax.xml.namespace.QName elementQName)
        throws org.apache.axis2.AxisFault {
        try {
            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
            emptyEnvelope.getBody()
                         .addChild(param.getOMElement(
                    UpLoadDataE.MY_QNAME,
                    factory));

            return emptyEnvelope;
        } catch (org.apache.axis2.databinding.ADBException e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }
    }

    /* methods to provide back word compatibility */

    /**
     *  get the default envelope
     */
    private org.apache.axiom.soap.SOAPEnvelope toEnvelope(
        org.apache.axiom.soap.SOAPFactory factory) {
        return factory.getDefaultEnvelope();
    }

    private Object fromOM(org.apache.axiom.om.OMElement param,
        Class type) throws org.apache.axis2.AxisFault {
        try {
            if (UpLoadDataE.class.equals(
                        type)) {
                return UpLoadDataE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }

            if (UpLoadDataResponseE.class.equals(
                        type)) {
                return UpLoadDataResponseE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
            }
        } catch (Exception e) {
            throw org.apache.axis2.AxisFault.makeFault(e);
        }

        return null;
    }

    //http://10.17.9.226:8080/cmicspd/ws/spd/uploaddata
    public static class UpLoadData implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
           name = UpLoadData
           Namespace URI = http://service.cmic.el.com/
           Namespace Prefix = ns1
         */

        /**
         * field for Arg0
         */
        protected String localArg0;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localArg0Tracker = false;

        /**
         * field for Arg1
         */
        protected String localArg1;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localArg1Tracker = false;

        /**
         * field for Arg2
         */
        protected String localArg2;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localArg2Tracker = false;

        /**
         * field for Arg3
         */
        protected String localArg3;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localArg3Tracker = false;

        /**
         * field for Arg4
         */
        protected String localArg4;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean localArg4Tracker = false;

        public boolean isArg0Specified() {
            return localArg0Tracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getArg0() {
            return localArg0;
        }

        /**
         * Auto generated setter method
         * @param param Arg0
         */
        public void setArg0(String param) {
            localArg0Tracker = param != null;

            this.localArg0 = param;
        }

        public boolean isArg1Specified() {
            return localArg1Tracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getArg1() {
            return localArg1;
        }

        /**
         * Auto generated setter method
         * @param param Arg1
         */
        public void setArg1(String param) {
            localArg1Tracker = param != null;

            this.localArg1 = param;
        }

        public boolean isArg2Specified() {
            return localArg2Tracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getArg2() {
            return localArg2;
        }

        /**
         * Auto generated setter method
         * @param param Arg2
         */
        public void setArg2(String param) {
            localArg2Tracker = param != null;

            this.localArg2 = param;
        }

        public boolean isArg3Specified() {
            return localArg3Tracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getArg3() {
            return localArg3;
        }

        /**
         * Auto generated setter method
         * @param param Arg3
         */
        public void setArg3(String param) {
            localArg3Tracker = param != null;

            this.localArg3 = param;
        }

        public boolean isArg4Specified() {
            return localArg4Tracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String getArg4() {
            return localArg4;
        }

        /**
         * Auto generated setter method
         * @param param Arg4
         */
        public void setArg4(String param) {
            localArg4Tracker = param != null;

            this.localArg4 = param;
        }

        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            String prefix = null;
            String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                String namespacePrefix = registerPrefix(xmlWriter,
                        "http://service.cmic.el.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":UpLoadData", xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "UpLoadData", xmlWriter);
                }
            }

            if (localArg0Tracker) {
                namespace = "";
                writeStartElement(null, namespace, "arg0", xmlWriter);

                if (localArg0 == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg0 cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArg0);
                }

                xmlWriter.writeEndElement();
            }

            if (localArg1Tracker) {
                namespace = "";
                writeStartElement(null, namespace, "arg1", xmlWriter);

                if (localArg1 == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg1 cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArg1);
                }

                xmlWriter.writeEndElement();
            }

            if (localArg2Tracker) {
                namespace = "";
                writeStartElement(null, namespace, "arg2", xmlWriter);

                if (localArg2 == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg2 cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArg2);
                }

                xmlWriter.writeEndElement();
            }

            if (localArg3Tracker) {
                namespace = "";
                writeStartElement(null, namespace, "arg3", xmlWriter);

                if (localArg3 == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg3 cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArg3);
                }

                xmlWriter.writeEndElement();
            }

            if (localArg4Tracker) {
                namespace = "";
                writeStartElement(null, namespace, "arg4", xmlWriter);

                if (localArg4 == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "arg4 cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(localArg4);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static String generatePrefix(
            String namespace) {
            if (namespace.equals("http://service.cmic.el.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(String prefix,
            String namespace, String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix,
            String namespace, String attName,
            String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace,
            String attName, String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace,
            String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

        /**
         * Register a namespace prefix
         */
        private String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            String namespace)
            throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static UpLoadData parse(
                javax.xml.stream.XMLStreamReader reader)
                throws Exception {
                UpLoadData object = new UpLoadData();

                int event;
                javax.xml.namespace.QName currentQName = null;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"UpLoadData".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (UpLoadData) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg0").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "arg0" + "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.setArg0(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg1").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "arg1" + "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.setArg1(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg2").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "arg2" + "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.setArg2(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg3").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "arg3" + "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.setArg3(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "arg4").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "arg4" + "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.setArg4(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class ExtensionMapper {
        public static Object getTypeObject(
            String namespaceURI, String typeName,
            javax.xml.stream.XMLStreamReader reader) throws Exception {
            if ("http://service.cmic.el.com/".equals(namespaceURI) &&
                    "UpLoadData".equals(typeName)) {
                return UpLoadData.Factory.parse(reader);
            }

            if ("http://service.cmic.el.com/".equals(namespaceURI) &&
                    "UpLoadDataResponse".equals(typeName)) {
                return UpLoadDataResponse.Factory.parse(reader);
            }

            throw new org.apache.axis2.databinding.ADBException(
                "Unsupported type " + namespaceURI + " " + typeName);
        }
    }

    public static class UpLoadDataE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://service.cmic.el.com/",
                "UpLoadData", "ns1");

        /**
         * field for UpLoadData
         */
        protected UpLoadData localUpLoadData;

        /**
         * Auto generated getter method
         * @return UpLoadData
         */
        public UpLoadData getUpLoadData() {
            return localUpLoadData;
        }

        /**
         * Auto generated setter method
         * @param param UpLoadData
         */
        public void setUpLoadData(UpLoadData param) {
            this.localUpLoadData = param;
        }

        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localUpLoadData == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "UpLoadData cannot be null!");
            }

            localUpLoadData.serialize(MY_QNAME, xmlWriter);
        }

        private static String generatePrefix(
            String namespace) {
            if (namespace.equals("http://service.cmic.el.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(String prefix,
            String namespace, String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix,
            String namespace, String attName,
            String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace,
            String attName, String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace,
            String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

        /**
         * Register a namespace prefix
         */
        private String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            String namespace)
            throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static UpLoadDataE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws Exception {
                UpLoadDataE object = new UpLoadDataE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://service.cmic.el.com/",
                                        "UpLoadData").equals(reader.getName())) {
                                object.setUpLoadData(UpLoadData.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class UpLoadDataResponse implements org.apache.axis2.databinding.ADBBean {
        /* This type was generated from the piece of schema that had
           name = UpLoadDataResponse
           Namespace URI = http://service.cmic.el.com/
           Namespace Prefix = ns1
         */

        /**
         * field for _return
         */
        protected String local_return;

        /*  This tracker boolean wil be used to detect whether the user called the set method
         *   for this attribute. It will be used to determine whether to include this field
         *   in the serialized XML
         */
        protected boolean local_returnTracker = false;

        public boolean is_returnSpecified() {
            return local_returnTracker;
        }

        /**
         * Auto generated getter method
         * @return java.lang.String
         */
        public String get_return() {
            return local_return;
        }

        /**
         * Auto generated setter method
         * @param param _return
         */
        public void set_return(String param) {
            local_returnTracker = param != null;

            this.local_return = param;
        }

        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, parentQName));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            String prefix = null;
            String namespace = null;

            prefix = parentQName.getPrefix();
            namespace = parentQName.getNamespaceURI();
            writeStartElement(prefix, namespace, parentQName.getLocalPart(),
                xmlWriter);

            if (serializeType) {
                String namespacePrefix = registerPrefix(xmlWriter,
                        "http://service.cmic.el.com/");

                if ((namespacePrefix != null) &&
                        (namespacePrefix.trim().length() > 0)) {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        namespacePrefix + ":UpLoadDataResponse", xmlWriter);
                } else {
                    writeAttribute("xsi",
                        "http://www.w3.org/2001/XMLSchema-instance", "type",
                        "UpLoadDataResponse", xmlWriter);
                }
            }

            if (local_returnTracker) {
                namespace = "";
                writeStartElement(null, namespace, "return", xmlWriter);

                if (local_return == null) {
                    // write the nil attribute
                    throw new org.apache.axis2.databinding.ADBException(
                        "return cannot be null!!");
                } else {
                    xmlWriter.writeCharacters(local_return);
                }

                xmlWriter.writeEndElement();
            }

            xmlWriter.writeEndElement();
        }

        private static String generatePrefix(
            String namespace) {
            if (namespace.equals("http://service.cmic.el.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(String prefix,
            String namespace, String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix,
            String namespace, String attName,
            String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace,
            String attName, String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace,
            String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

        /**
         * Register a namespace prefix
         */
        private String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            String namespace)
            throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static UpLoadDataResponse parse(
                javax.xml.stream.XMLStreamReader reader)
                throws Exception {
                UpLoadDataResponse object = new UpLoadDataResponse();

                int event;
                javax.xml.namespace.QName currentQName = null;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    if (reader.getAttributeValue(
                                "http://www.w3.org/2001/XMLSchema-instance",
                                "type") != null) {
                        String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "type");

                        if (fullTypeName != null) {
                            String nsPrefix = null;

                            if (fullTypeName.indexOf(":") > -1) {
                                nsPrefix = fullTypeName.substring(0,
                                        fullTypeName.indexOf(":"));
                            }

                            nsPrefix = (nsPrefix == null) ? "" : nsPrefix;

                            String type = fullTypeName.substring(fullTypeName.indexOf(
                                        ":") + 1);

                            if (!"UpLoadDataResponse".equals(type)) {
                                //find namespace for the prefix
                                String nsUri = reader.getNamespaceContext()
                                                               .getNamespaceURI(nsPrefix);

                                return (UpLoadDataResponse) ExtensionMapper.getTypeObject(nsUri,
                                    type, reader);
                            }
                        }
                    }

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    reader.next();

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement() &&
                            new javax.xml.namespace.QName("", "return").equals(
                                reader.getName())) {
                        nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                                "nil");

                        if ("true".equals(nillableValue) ||
                                "1".equals(nillableValue)) {
                            throw new org.apache.axis2.databinding.ADBException(
                                "The element: " + "return" +
                                "  cannot be null");
                        }

                        String content = reader.getElementText();

                        object.set_return(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                content));

                        reader.next();
                    } // End of if for expected property start element

                    else {
                    }

                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    if (reader.isStartElement()) {
                        // 2 - A start element we are not expecting indicates a trailing invalid property
                        throw new org.apache.axis2.databinding.ADBException(
                            "Unexpected subelement " + reader.getName());
                    }
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }
        } //end of factory class
    }

    public static class UpLoadDataResponseE implements org.apache.axis2.databinding.ADBBean {
        public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName("http://service.cmic.el.com/",
                "UpLoadDataResponse", "ns1");

        /**
         * field for UpLoadDataResponse
         */
        protected UpLoadDataResponse localUpLoadDataResponse;

        /**
         * Auto generated getter method
         * @return UpLoadDataResponse
         */
        public UpLoadDataResponse getUpLoadDataResponse() {
            return localUpLoadDataResponse;
        }

        /**
         * Auto generated setter method
         * @param param UpLoadDataResponse
         */
        public void setUpLoadDataResponse(UpLoadDataResponse param) {
            this.localUpLoadDataResponse = param;
        }

        /**
         *
         * @param parentQName
         * @param factory
         * @return org.apache.axiom.om.OMElement
         */
        public org.apache.axiom.om.OMElement getOMElement(
            final javax.xml.namespace.QName parentQName,
            final org.apache.axiom.om.OMFactory factory)
            throws org.apache.axis2.databinding.ADBException {
            return factory.createOMElement(new org.apache.axis2.databinding.ADBDataSource(
                    this, MY_QNAME));
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            serialize(parentQName, xmlWriter, false);
        }

        public void serialize(final javax.xml.namespace.QName parentQName,
            javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
            throws javax.xml.stream.XMLStreamException,
                org.apache.axis2.databinding.ADBException {
            //We can safely assume an element has only one type associated with it
            if (localUpLoadDataResponse == null) {
                throw new org.apache.axis2.databinding.ADBException(
                    "UpLoadDataResponse cannot be null!");
            }

            localUpLoadDataResponse.serialize(MY_QNAME, xmlWriter);
        }

        private static String generatePrefix(
            String namespace) {
            if (namespace.equals("http://service.cmic.el.com/")) {
                return "ns1";
            }

            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(String prefix,
            String namespace, String localPart,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeStartElement(writerPrefix, localPart, namespace);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }

        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(String prefix,
            String namespace, String attName,
            String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String writerPrefix = xmlWriter.getPrefix(namespace);

            if (writerPrefix != null) {
                xmlWriter.writeAttribute(writerPrefix, namespace, attName,
                    attValue);
            } else {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
                xmlWriter.writeAttribute(prefix, namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(String namespace,
            String attName, String attValue,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attValue);
            } else {
                xmlWriter.writeAttribute(registerPrefix(xmlWriter, namespace),
                    namespace, attName, attValue);
            }
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeQNameAttribute(String namespace,
            String attName, javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String attributeNamespace = qname.getNamespaceURI();
            String attributePrefix = xmlWriter.getPrefix(attributeNamespace);

            if (attributePrefix == null) {
                attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
            }

            String attributeValue;

            if (attributePrefix.trim().length() > 0) {
                attributeValue = attributePrefix + ":" + qname.getLocalPart();
            } else {
                attributeValue = qname.getLocalPart();
            }

            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName, attributeValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(attributePrefix, namespace, attName,
                    attributeValue);
            }
        }

        /**
         *  method to handle Qnames
         */
        private void writeQName(javax.xml.namespace.QName qname,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            String namespaceURI = qname.getNamespaceURI();

            if (namespaceURI != null) {
                String prefix = xmlWriter.getPrefix(namespaceURI);

                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix, namespaceURI);
                }

                if (prefix.trim().length() > 0) {
                    xmlWriter.writeCharacters(prefix + ":" +
                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                            qname));
                }
            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                        qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
            javax.xml.stream.XMLStreamWriter xmlWriter)
            throws javax.xml.stream.XMLStreamException {
            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                StringBuffer stringToWrite = new StringBuffer();
                String namespaceURI = null;
                String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }

                    namespaceURI = qnames[i].getNamespaceURI();

                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);

                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix, namespaceURI);
                        }

                        if (prefix.trim().length() > 0) {
                            stringToWrite.append(prefix).append(":")
                                         .append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                    qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(
                                qnames[i]));
                    }
                }

                xmlWriter.writeCharacters(stringToWrite.toString());
            }
        }

        /**
         * Register a namespace prefix
         */
        private String registerPrefix(
            javax.xml.stream.XMLStreamWriter xmlWriter,
            String namespace)
            throws javax.xml.stream.XMLStreamException {
            String prefix = xmlWriter.getPrefix(namespace);

            if (prefix == null) {
                prefix = generatePrefix(namespace);

                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();

                while (true) {
                    String uri = nsContext.getNamespaceURI(prefix);

                    if ((uri == null) || (uri.length() == 0)) {
                        break;
                    }

                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }

                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }

            return prefix;
        }

        /**
         *  Factory class that keeps the parse method
         */
        public static class Factory {
            private static org.apache.commons.logging.Log log = org.apache.commons.logging.LogFactory.getLog(Factory.class);

            /**
             * static method to create the object
             * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
             *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
             * Postcondition: If this object is an element, the reader is positioned at its end element
             *                If this object is a complex type, the reader is positioned at the end element of its outer element
             */
            public static UpLoadDataResponseE parse(
                javax.xml.stream.XMLStreamReader reader)
                throws Exception {
                UpLoadDataResponseE object = new UpLoadDataResponseE();

                int event;
                javax.xml.namespace.QName currentQName = null;
                String nillableValue = null;
                String prefix = "";
                String namespaceuri = "";

                try {
                    while (!reader.isStartElement() && !reader.isEndElement())
                        reader.next();

                    currentQName = reader.getName();

                    // Note all attributes that were handled. Used to differ normal attributes
                    // from anyAttributes.
                    java.util.Vector handledAttributes = new java.util.Vector();

                    while (!reader.isEndElement()) {
                        if (reader.isStartElement()) {
                            if (reader.isStartElement() &&
                                    new javax.xml.namespace.QName(
                                        "http://service.cmic.el.com/",
                                        "UpLoadDataResponse").equals(
                                        reader.getName())) {
                                object.setUpLoadDataResponse(UpLoadDataResponse.Factory.parse(
                                        reader));
                            } // End of if for expected property start element

                            else {
                                // 3 - A start element we are not expecting indicates an invalid parameter was passed
                                throw new org.apache.axis2.databinding.ADBException(
                                    "Unexpected subelement " +
                                    reader.getName());
                            }
                        } else {
                            reader.next();
                        }
                    } // end of while loop
                } catch (javax.xml.stream.XMLStreamException e) {
                    throw new Exception(e);
                }

                return object;
            }
        } //end of factory class
    }
}
