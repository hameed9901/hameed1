package com.example.signatureapplication;

public class EsealPlaceHolderCoordinates {
    private int pageNumber;
    private String signatureXaxis;
    private String signatureYaxis;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getSignatureXaxis() {
        return signatureXaxis;
    }

    public void setSignatureXaxis(String signatureXaxis) {
        this.signatureXaxis = signatureXaxis;
    }

    public String getSignatureYaxis() {
        return signatureYaxis;
    }

    public void setSignatureYaxis(String signatureYaxis) {
        this.signatureYaxis = signatureYaxis;
    }

//    @Override
//    public String toString() {
//        return "EsealPlaceHolderCoordinates{" +
//                "pageNumber=" + pageNumber +
//                ", signatureXaxis='" + signatureXaxis + '\'' +
//                ", signatureYaxis='" + signatureYaxis + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "{\n"
                + "\"pageNumber\":" + pageNumber + ",\n" + "\"signatureXaxis\":\"" + signatureXaxis + "\",\n"
                + "\"signatureYaxis\":\"" + signatureYaxis +"\""
                +
                "}";
    }
//    @Override
//    public String toString() {
//        return "{" +
//                "pageNumber=" + pageNumber +
//                ", signatureXaxis='" + signatureXaxis + '\'' +
//                ", signatureYaxis='" + signatureYaxis + '\'' +
//                '}';
//    }
}
