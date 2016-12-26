package app.dto;

import java.io.Serializable;

/**
 * Created by milen_000 on 13/09/2016.
 */
public class Uniref100 implements Serializable {

    private static final long serialVersionUID = 2L;
    String seqName;
    String orfsPtns;
    String description;
    Integer length;
    Integer numberHits;
    String eValue;
    Double simMean;
    String fastaDescription;
    String fastaContent;

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

    public String getOrfsPtns() {
        return orfsPtns;
    }

    public void setOrfsPtns(String orfsPtns) {
        this.orfsPtns = orfsPtns;
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

    public String getFastaDescription() {
        return fastaDescription;
    }

    public void setFastaDescription(String fastaDescription) {
        this.fastaDescription = fastaDescription;
    }

    public String getFastaContent() {
        return fastaContent;
    }

    public void setFastaContent(String fastaContent) {
        this.fastaContent = fastaContent;
    }
}
