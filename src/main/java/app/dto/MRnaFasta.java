package app.dto;

import java.io.Serializable;

/**
 * Created by milene.guimaraes on 09/09/16.
 */
public class MRnaFasta implements Serializable {

    private static final long serialVersionUID = 1L;
    String seqName;
    String fastaDescription;
    String fastaContent;

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
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
