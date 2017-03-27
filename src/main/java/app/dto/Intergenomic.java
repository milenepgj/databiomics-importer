package app.dto;

import java.io.Serializable;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class Intergenomic implements Serializable {

    String seqName;
    String orfsPtns;
    String ec_number;
    String enzymeDescription;
    String organism;
    String uniprotId;
    String pdbBestHit;
    String pdbIdentity;
    String foldScopId;
    String superFamily;
    String literature_function;

    public String getEc_number() {
        return ec_number;
    }

    public void setEc_number(String ec_number) {
        this.ec_number = ec_number;
    }

    public String getEnzymeDescription() {
        return enzymeDescription;
    }

    public void setEnzymeDescription(String enzymeDescription) {
        this.enzymeDescription = enzymeDescription;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
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

    public String getUniprotId() {
        return uniprotId;
    }

    public void setUniprotId(String uniprotId) {
        this.uniprotId = uniprotId;
    }

    public String getPdbBestHit() {
        return pdbBestHit;
    }

    public void setPdbBestHit(String pdbBestHit) {
        this.pdbBestHit = pdbBestHit;
    }

    public String getPdbIdentity() {
        return pdbIdentity;
    }

    public void setPdbIdentity(String pdbIdentity) {
        this.pdbIdentity = pdbIdentity;
    }

    public String getFoldScopId() {
        return foldScopId;
    }

    public void setFoldScopId(String foldScopId) {
        this.foldScopId = foldScopId;
    }

    public String getSuperFamily() {
        return superFamily;
    }

    public void setSuperFamily(String superFamily) {
        this.superFamily = superFamily;
    }

    public String getLiterature_function() {
        return literature_function;
    }

    public void setLiterature_function(String literature_function) {
        this.literature_function = literature_function;
    }
}
