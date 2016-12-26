package app.dto;

import java.io.Serializable;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class Trembl  implements Serializable {

    private static final long serialVersionUID = 1L;

    String seqName;
    String orfsPtns;
    String description;
    Integer length;
    Integer numberHits;
    String eValue;
    Double simMean;
    String interproIds;
    String fastaDescription;
    String fastaContent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getOrfsPtns() {
        return orfsPtns;
    }

    public void setOrfsPtns(String orfsPtns) {
        this.orfsPtns = orfsPtns;
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
