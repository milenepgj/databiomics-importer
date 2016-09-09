package app.dto;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class Sequence {

    String seqname;
    String description;
    Long length;
    Long numberhits;
    String e_value;
    Double sim_mean;
    String interpro_ids;
    String go;
    String go_names;


    public String getSeqname() {
        return seqname;
    }

    public void setSeqname(String seqname) {
        this.seqname = seqname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getNumberhits() {
        return numberhits;
    }

    public void setNumberhits(Long numberhits) {
        this.numberhits = numberhits;
    }

    public String getE_value() {
        return e_value;
    }

    public void setE_value(String e_value) {
        this.e_value = e_value;
    }

    public Double getSim_mean() {
        return sim_mean;
    }

    public void setSim_mean(Double sim_mean) {
        this.sim_mean = sim_mean;
    }

    public String getInterpro_ids() {
        return interpro_ids;
    }

    public void setInterpro_ids(String interpro_ids) {
        this.interpro_ids = interpro_ids;
    }

    public String getGo_names() {
        return go_names;
    }

    public void setGo_names(String go_names) {
        this.go_names = go_names;
    }

    public String getGo() {
        return go;
    }

    public void setGo(String go) {
        this.go = go;
    }
}
