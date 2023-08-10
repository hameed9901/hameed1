package com.example.signatureapplication;

public class RequestBodyDTO {
    private String documentType;
    private String payerId;
    private String accountType;
    private String organizationUid;
    private String subscriberUniqueId;
    private PlaceHolderCoordinates placeHolderCoordinates;
    private  EsealPlaceHolderCoordinates esealPlaceHolderCoordinates;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getOrganizationUid() {
        return organizationUid;
    }

    public void setOrganizationUid(String organizationUid) {
        this.organizationUid = organizationUid;
    }

    public String getSubscriberUniqueId() {
        return subscriberUniqueId;
    }

    public void setSubscriberUniqueId(String subscriberUniqueId) {
        this.subscriberUniqueId = subscriberUniqueId;
    }

    public PlaceHolderCoordinates getPlaceHolderCoordinates() {
        return placeHolderCoordinates;
    }

    public void setPlaceHolderCoordinates(PlaceHolderCoordinates placeHolderCoordinates) {
        this.placeHolderCoordinates = placeHolderCoordinates;
    }

    public EsealPlaceHolderCoordinates getEsealPlaceHolderCoordinates() {
        return esealPlaceHolderCoordinates;
    }

    public void setEsealPlaceHolderCoordinates(EsealPlaceHolderCoordinates esealPlaceHolderCoordinates) {
        this.esealPlaceHolderCoordinates = esealPlaceHolderCoordinates;
    }


    @Override
    public String toString() {
        return "{\n"
                + "\"documentType\":\"" + documentType + "\",\n" + "\"payerId\":\"" + payerId + "\",\n"
                + "\"accountType\":\"" + accountType + "\",\n"
                + "\"organizationUid\":\"" + organizationUid + "\",\n"  + "\"subscriberUniqueId\":\""
                + subscriberUniqueId + "\",\n" + "\"placeHolderCoordinates\":" + placeHolderCoordinates + ",\n" + "\"esealPlaceHolderCoordinates\":" + esealPlaceHolderCoordinates +
                ""
                +
                "}";
    }

}
