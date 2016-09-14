package app.dto;

/**
 * Created by milen_000 on 13/09/2016.
 */
public class UnirefNematoda {
    String seqName;
    String orfsPtns;
    String hitsAgaintUniruniref100;
    Integer length;
    Integer numberHits;
    String eValue;
    Double simMean;

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

    public String getHitsAgaintUniruniref100() {
        return hitsAgaintUniruniref100;
    }

    public void setHitsAgaintUniruniref100(String hitsAgaintUniruniref100) {
        this.hitsAgaintUniruniref100 = hitsAgaintUniruniref100;
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

    public String setEValue() {
        return eValue;
    }

    public void setEValue(String eValue) {
        this.eValue = eValue;
    }

    public Double getSimMean() {
        return simMean;
    }

    public void setSimMean(Double simMean) {
        this.simMean = simMean;
    }
}
