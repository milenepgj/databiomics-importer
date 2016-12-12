package app.dto;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class Fasta {

    String seqName;
    String description;
    Integer length;
    Integer numberHits;
    String eValue;
    Double simMean;
    String interproIds;
    String go;
    String goNames;
    String enzimeCodes;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getNumberHits() {
        return numberHits;
    }

    public void setNumberHits(Integer numberHits) {
        this.numberHits = numberHits;
    }

    public String geteValue() {
        return eValue;
    }

    public void seteValue(String eValue) {
        this.eValue = eValue;
    }

    public Double getSimMean() {
        return simMean;
    }

    public void setSimMean(Double simMean) {
        this.simMean = simMean;
    }

    public String getInterproIds() {
        return interproIds;
    }

    public void setInterproIds(String interproIds) {
        this.interproIds = interproIds;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }

    public String getGoNames() {
        return goNames;
    }

    public void setGoNames(String goNames) {
        this.goNames = goNames;
    }

    public String getEnzimeCodes() {
        return enzimeCodes;
    }

    public void setEnzimeCodes(String enzimeCodes) {
        this.enzimeCodes = enzimeCodes;
    }
}
