package app.dto;

import java.io.Serializable;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class Intragenomic implements Serializable {

    String seqName;
    String orfsPtns;
    String ec_number;
    String tpm;
    String fpkm;
    String enzymeDescription;
    String uniref100;
    Double length;
    Double numberHits;
    String eValue;
    Double simMean;
    String fold;
    String superfamily;
    String literature_function;

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

    public String getEc_number() {
        return ec_number;
    }

    public void setEc_number(String ec_number) {
        this.ec_number = ec_number;
    }

    public String getTpm() {
        return tpm;
    }

    public void setTpm(String tpm) {
        this.tpm = tpm;
    }

    public String getFpkm() {
        return fpkm;
    }

    public void setFpkm(String fpkm) {
        this.fpkm = fpkm;
    }

    public String getEnzymeDescription() {
        return enzymeDescription;
    }

    public void setEnzymeDescription(String enzymeDescription) {
        this.enzymeDescription = enzymeDescription;
    }

    public String getUniref100() {
        return uniref100;
    }

    public void setUniref100(String uniref100) {
        this.uniref100 = uniref100;
    }

    public double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getNumberHits() {
        return numberHits;
    }

    public void setNumberHits(Double numberHits) {
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

    public String getFold() {
        return fold;
    }

    public void setFold(String fold) {
        this.fold = fold;
    }

    public String getSuperfamily() {
        return superfamily;
    }

    public void setSuperfamily(String superfamily) {
        this.superfamily = superfamily;
    }

    public String getLiterature_function() {
        return literature_function;
    }

    public void setLiterature_function(String literature_function) {
        this.literature_function = literature_function;
    }
}
